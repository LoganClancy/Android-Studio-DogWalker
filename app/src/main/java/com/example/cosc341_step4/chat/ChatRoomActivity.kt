package com.example.cosc341_step4.chat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cosc341_step4.R
import com.example.cosc341_step4.chat.Message
import com.example.cosc341_step4.chat.MessageType
import java.io.File
import java.util.*

class ChatRoomActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CONVERSATION_ID = "conversation_id"
        const val EXTRA_CONVERSATION_NAME = "conversation_name"
        const val EXTRA_IS_GROUP = "is_group"

        private const val REQUEST_CAMERA = 101
        private const val REQUEST_GALLERY = 102
        private const val REQUEST_PERMISSIONS = 103
    }

    // Current logged-in user ID — replace with actual auth user ID
    private val currentUserId = "user_dominic"
    private val currentUserName = "Dominic J"

    private lateinit var rvMessages: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var btnVoiceNote: ImageButton
    private lateinit var btnCamera: ImageButton
    private lateinit var btnGallery: ImageButton
    private lateinit var voiceRecordingOverlay: LinearLayout
    private lateinit var tvRecordingTimer: TextView
    private lateinit var tvChatTitle: TextView
    private lateinit var messageAdapter: MessageAdapter

    private var mediaRecorder: MediaRecorder? = null
    private var voiceFilePath: String? = null
    private var isRecording = false
    private var recordingSeconds = 0
    private val timerHandler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null

    private var photoUri: Uri? = null
    private var conversationId: String = ""
    private var conversationName: String = ""
    private var isGroupChat: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        conversationId = intent.getStringExtra(EXTRA_CONVERSATION_ID) ?: ""
        conversationName = intent.getStringExtra(EXTRA_CONVERSATION_NAME) ?: "Chat"
        isGroupChat = intent.getBooleanExtra(EXTRA_IS_GROUP, false)

        initViews()
        setupToolbar()
        setupRecyclerView()
        setupSendButton()
        setupVoiceButton()
        setupCameraButton()
        setupGalleryButton()
        requestRequiredPermissions()
        loadMessages()
    }

    private fun initViews() {
        rvMessages = findViewById(R.id.rvMessages)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        btnVoiceNote = findViewById(R.id.btnVoiceNote)
        btnCamera = findViewById(R.id.btnCamera)
        btnGallery = findViewById(R.id.btnGallery)
        voiceRecordingOverlay = findViewById(R.id.voiceRecordingOverlay)
        tvRecordingTimer = findViewById(R.id.tvRecordingTimer)
        tvChatTitle = findViewById(R.id.tvChatTitle)
        findViewById<Button>(R.id.btnReleaseToSend).setOnClickListener { stopRecordingAndSend() }
    }

    private fun setupToolbar() {
        tvChatTitle.text = conversationName
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(
            mutableListOf(),
            currentUserId,
            isGroupChat
        )
        rvMessages.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        rvMessages.adapter = messageAdapter
    }

    private fun loadMessages() {
        // TODO: Replace with Firebase/backend fetch
        // Sample messages to demonstrate the UI
        val sampleMessages = mutableListOf(
            Message(
                senderId = "user_harrison",
                senderName = "Harrison",
                content = "Hello",
                type = MessageType.TEXT,
                timestamp = System.currentTimeMillis() - 60000
            ),
            Message(
                senderId = currentUserId,
                senderName = currentUserName,
                content = "Hola",
                type = MessageType.TEXT,
                timestamp = System.currentTimeMillis() - 30000
            )
        )
        messageAdapter.setMessages(sampleMessages)
    }

    // ─── Text Sending ───────────────────────────────────────────────────────
    private fun setupSendButton() {
        btnSend.setOnClickListener {
            val text = etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                sendMessage(Message(
                    senderId = currentUserId,
                    senderName = currentUserName,
                    content = text,
                    type = MessageType.TEXT
                ))
                etMessage.text.clear()
            }
        }
    }

    // ─── Voice Note ─────────────────────────────────────────────────────────
    private fun setupVoiceButton() {
        btnVoiceNote.setOnClickListener {
            if (!isRecording) startRecording() else stopRecordingAndSend()
        }
    }

    private fun startRecording() {
        if (!hasAudioPermission()) {
            requestRequiredPermissions()
            return
        }
        voiceFilePath = "${externalCacheDir?.absolutePath}/voice_${System.currentTimeMillis()}.m4a"
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(voiceFilePath)
            prepare()
            start()
        }
        isRecording = true
        recordingSeconds = 0
        voiceRecordingOverlay.visibility = View.VISIBLE

        timerRunnable = object : Runnable {
            override fun run() {
                recordingSeconds++
                val min = recordingSeconds / 60
                val sec = recordingSeconds % 60
                tvRecordingTimer.text = String.format("%02d:%02d", min, sec)
                timerHandler.postDelayed(this, 1000)
            }
        }
        timerHandler.postDelayed(timerRunnable!!, 1000)
    }

    private fun stopRecordingAndSend() {
        timerRunnable?.let { timerHandler.removeCallbacks(it) }
        try {
            mediaRecorder?.apply { stop(); release() }
        } catch (e: Exception) { e.printStackTrace() }
        mediaRecorder = null
        isRecording = false
        voiceRecordingOverlay.visibility = View.GONE

        voiceFilePath?.let { path ->
            sendMessage(Message(
                senderId = currentUserId,
                senderName = currentUserName,
                type = MessageType.VOICE,
                mediaUri = path,
                content = "Voice message"
            ))
        }
    }

    // ─── Camera (Take Photo) ────────────────────────────────────────────────
    private fun setupCameraButton() {
        btnCamera.setOnClickListener {
            if (!hasCameraPermission()) { requestRequiredPermissions(); return@setOnClickListener }
            val photoFile = File.createTempFile(
                "photo_${System.currentTimeMillis()}", ".jpg",
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )
            photoUri = FileProvider.getUriForFile(
                this, "${packageName}.fileprovider", photoFile
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            }
            startActivityForResult(intent, REQUEST_CAMERA)
        }
    }

    // ─── Gallery (Send Existing Image) ──────────────────────────────────────
    private fun setupGalleryButton() {
        btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_GALLERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return

        when (requestCode) {
            REQUEST_CAMERA -> {
                photoUri?.let { uri ->
                    sendMessage(Message(
                        senderId = currentUserId,
                        senderName = currentUserName,
                        type = MessageType.IMAGE,
                        mediaUri = uri.toString(),
                        content = "Photo"
                    ))
                }
            }
            REQUEST_GALLERY -> {
                data?.data?.let { uri ->
                    sendMessage(Message(
                        senderId = currentUserId,
                        senderName = currentUserName,
                        type = MessageType.IMAGE,
                        mediaUri = uri.toString(),
                        content = "Image"
                    ))
                }
            }
        }
    }

    // ─── Send Message ────────────────────────────────────────────────────────
    private fun sendMessage(message: Message) {
        messageAdapter.addMessage(message)
        rvMessages.scrollToPosition(messageAdapter.itemCount - 1)
        // TODO: Save to Firebase / backend here
        // FirebaseRepository.sendMessage(conversationId, message)
    }

    // ─── Permissions ─────────────────────────────────────────────────────────
    private fun requestRequiredPermissions() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val missing = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missing.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, missing.toTypedArray(), REQUEST_PERMISSIONS)
        }
    }

    private fun hasAudioPermission() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

    private fun hasCameraPermission() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun onDestroy() {
        super.onDestroy()
        timerRunnable?.let { timerHandler.removeCallbacks(it) }
        mediaRecorder?.release()
    }
}

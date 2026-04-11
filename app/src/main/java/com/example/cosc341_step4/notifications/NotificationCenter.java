package com.example.cosc341_step4.notifications;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosc341_step4.R;

import java.util.List;

public class NotificationCenter extends AppCompatActivity {
    NotificationAdapter adapter;
    List<NotificationEntry> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification_center);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView rv = findViewById(R.id.Recycler);
        list = NotificationDB.getAll();
        adapter = new NotificationAdapter(list);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    public void startDeletion(){

    }

    public void test(View view){
        adapter.addNotif("bruh", "moment");
    }

    public void backToMain(View view){
        finish();
    }
}


package com.example.cosc341_step4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cosc341_step4.notifications.NotificationDB;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class MakeNewEventFragment extends Fragment {

    private TextInputEditText etEventName, etEventDetails, etLocation, etOccupancy, etTags;
    private SwitchCompat switchRsvp;
    private Button btnCreateEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_new_event, container, false);

        etEventName = view.findViewById(R.id.et_event_name);
        etEventDetails = view.findViewById(R.id.et_event_details);
        etLocation = view.findViewById(R.id.et_location);
        etOccupancy = view.findViewById(R.id.et_occupancy);
        etTags = view.findViewById(R.id.et_tags);
        switchRsvp = view.findViewById(R.id.switch_rsvp);
        btnCreateEvent = view.findViewById(R.id.btn_create_event);

        btnCreateEvent.setOnClickListener(v -> {
            String name = etEventName.getText().toString();
            String location = etLocation.getText().toString();

            if (name.isEmpty() || location.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in event name and location", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Event created successfully!", Toast.LENGTH_SHORT).show();
                // Clear fields
                etEventName.setText("");
                etEventDetails.setText("");
                etLocation.setText("");
                etOccupancy.setText("");
                etTags.setText("");
                switchRsvp.setChecked(false);
            }
            NotificationDB.addEventCreatedNotif(name, location);
        });
        return view;
    }
}

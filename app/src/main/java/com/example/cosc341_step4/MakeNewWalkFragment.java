package com.example.cosc341_step4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class MakeNewWalkFragment extends Fragment {

    private TextInputEditText etWalkName, etWalkDetails, etStartLocation, etEndLocation, etOccupancy, etTags;
    private SwitchCompat switchRsvp;
    private Button btnCreateWalk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_new_walk, container, false);

        etWalkName = view.findViewById(R.id.et_walk_name);
        etWalkDetails = view.findViewById(R.id.et_walk_details);
        etStartLocation = view.findViewById(R.id.et_start_location);
        etEndLocation = view.findViewById(R.id.et_end_location);
        etOccupancy = view.findViewById(R.id.et_occupancy);
        etTags = view.findViewById(R.id.et_tags);
        switchRsvp = view.findViewById(R.id.switch_rsvp);
        btnCreateWalk = view.findViewById(R.id.btn_create_walk);

        btnCreateWalk.setOnClickListener(v -> {
            String name = etWalkName.getText().toString();
            String startLocation = etStartLocation.getText().toString();

            if (name.isEmpty() || startLocation.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in walk name and start location", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Walk created successfully!", Toast.LENGTH_SHORT).show();
                // Clear fields
                etWalkName.setText("");
                etWalkDetails.setText("");
                etStartLocation.setText("");
                etEndLocation.setText("");
                etOccupancy.setText("");
                etTags.setText("");
                switchRsvp.setChecked(false);
            }
        });

        return view;
    }
}

// EventWalkProfileFragment.java
package com.example.cosc341_step4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import com.example.cosc341_step4.R;

public class EventWalkProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_walk_profile, container, false);

        Button btnCreateEvent = view.findViewById(R.id.btn_create_event);
        Button btnJoinEvent = view.findViewById(R.id.btn_join_event);
        Button btnCreateWalk = view.findViewById(R.id.btn_create_walk);
        Button btnJoinWalk = view.findViewById(R.id.btn_join_walk);
        Button btnMyActivities = view.findViewById(R.id.btn_my_activities);

        btnCreateEvent.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).loadFragment(new MakeNewEventFragment());
        });

        btnJoinEvent.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).loadFragment(new JoinEventsFragment());
        });

        btnCreateWalk.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).loadFragment(new MakeNewWalkFragment());
        });

        btnJoinWalk.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).loadFragment(new JoinWalksFragment());
        });

        btnMyActivities.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).loadFragment(new PeopleJoiningFragment());
        });

        return view;
    }
}
// PeopleJoiningFragment.java
package com.example.cosc341_step4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PeopleJoiningFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyActivitiesAdapter adapter;
    private List<MyActivity> myActivities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_joining, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_people);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadMyActivities();

        adapter = new MyActivitiesAdapter(myActivities);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadMyActivities() {
        myActivities = new ArrayList<>();
        // Sample joined activities - in a real app, these would come from user's joined list
        myActivities.add(new MyActivity("Llams Hangout", "Llamas Park", "1.5 miles", "8+ attending", "Weekly pet meetup"));
        myActivities.add(new MyActivity("Cat Meetup at Central Park", "Central Park", "2.5 miles", "8+ attending", "Meet fellow cat lovers"));
        myActivities.add(new MyActivity("Beach Walk", "Beach Parking", "1.0 miles", "5 walking right now", "Enjoy a relaxing walk on the beach"));
    }

    // Model class for My Activities
    class MyActivity {
        private String title;
        private String location;
        private String distance;
        private String attendees;
        private String description;

        public MyActivity(String title, String location, String distance, String attendees, String description) {
            this.title = title;
            this.location = location;
            this.distance = distance;
            this.attendees = attendees;
            this.description = description;
        }

        public String getTitle() { return title; }
        public String getLocation() { return location; }
        public String getDistance() { return distance; }
        public String getAttendees() { return attendees; }
        public String getDescription() { return description; }
    }

    class MyActivitiesAdapter extends RecyclerView.Adapter<MyActivitiesAdapter.ViewHolder> {
        private List<MyActivity> activities;

        MyActivitiesAdapter(List<MyActivity> activities) {
            this.activities = activities;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_myactivity, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MyActivity activity = activities.get(position);
            holder.bind(activity);
        }

        @Override
        public int getItemCount() {
            return activities.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titleText, locationText, distanceText, attendeesText, descriptionText;
            private Button joinButton;
            private LinearLayout detailsLayout;
            private boolean expanded = false;

            ViewHolder(View itemView) {
                super(itemView);
                titleText = itemView.findViewById(R.id.activity_title);
                locationText = itemView.findViewById(R.id.activity_location);
                distanceText = itemView.findViewById(R.id.activity_distance);
                attendeesText = itemView.findViewById(R.id.activity_attendees);
                descriptionText = itemView.findViewById(R.id.activity_description);
                joinButton = itemView.findViewById(R.id.btn_leave);
                detailsLayout = itemView.findViewById(R.id.details_layout);

                itemView.setOnClickListener(v -> {
                    expanded = !expanded;
                    detailsLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
                });
            }

            void bind(MyActivity activity) {
                titleText.setText(activity.getTitle());
                locationText.setText("📍 " + activity.getLocation());
                distanceText.setText("📏 " + activity.getDistance());
                attendeesText.setText("👥 " + activity.getAttendees());
                descriptionText.setText(activity.getDescription());

                joinButton.setText("Leave");
                joinButton.setOnClickListener(v -> {
                    //Remove from joined activities
                    activities.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }
        }
    }
}
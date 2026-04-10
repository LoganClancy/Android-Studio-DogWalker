// JoinEventsFragment.java
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

import com.example.cosc341_step4.R;

import java.util.ArrayList;
import java.util.List;

public class JoinEventsFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<Event> events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_events, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadEvents();

        adapter = new EventAdapter(events);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadEvents() {
        events = new ArrayList<>();
        events.add(new Event(1, "Llams Hangout", "Casual pet gathering",
                "1:00 PM - 3:00 PM", "Llams Park", "5+ attending", false));
        events.add(new Event(2, "Cat Meetup at Central Park", "Meet fellow cat lovers",
                "2:00 PM - 4:00 PM", "Central Park", "8+ attending", false));
        events.add(new Event(3, "Tuesday Pet Group", "Weekly pet meetup",
                "6:00 PM - 7:30 PM", "Community Center", "12+ attending", false));
        events.add(new Event(4, "Rutland Pet Picnic",
                "Hello! We are gonna be hosting our usual pet meetups. All owners and volunteers are welcome. " +
                        "We also have free cheese crackers (first come, first serve). Do make sure to bring your own " +
                        "disposable bags for any waste. Kids are welcome too!\n\n" +
                        "🔴 1:00 PM - 3:00 PM (PST)\n" +
                        "🟢 Rutland Centennial Park\n" +
                        "🟡 20+ estimated (13 sign ups as of 9:31 AM today)",
                "1:00 PM - 3:00 PM", "Rutland Centennial Park", "20+ estimated", false));
        events.add(new Event(5, "Cat Cafe Kelowna", "Enjoy coffee with cats!\n\nEnding Soon!",
                "10:00 AM - 8:00 PM", "Cat Cafe", "15+ attending", false));
        events.add(new Event(6, "Kelowna Shelter Meet",
                "At the Kelowna Animal Shelter (KAS), we try to ensure that all kinds of people who want " +
                        "to acquire experience with handling animals and pets can have a general intro of understanding them.\n\n" +
                        "🔴 3:00 PM - 6:00 PM (PST)\n" +
                        "🟢 KAS Shelter Downtown\n" +
                        "🟢 5+ estimated (0 sign ups as of 9:31 AM today)\n" +
                        "📍 cats-dogs-friendly-hospital-veterinarian-newcomers-all ages",
                "3:00 PM - 6:00 PM", "KAS Shelter Downtown", "5+ estimated", false));
    }

    class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
        private List<Event> events;

        EventAdapter(List<Event> events) {
            this.events = events;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_event, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Event event = events.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titleText, timeText, locationText, attendeesText, descriptionText;
            private Button joinButton;
            private CardView cardView;
            private LinearLayout detailsLayout;
            private boolean expanded = false;

            ViewHolder(View itemView) {
                super(itemView);
                titleText = itemView.findViewById(R.id.event_title);
                timeText = itemView.findViewById(R.id.event_time);
                locationText = itemView.findViewById(R.id.event_location);
                attendeesText = itemView.findViewById(R.id.event_attendees);
                descriptionText = itemView.findViewById(R.id.event_description);
                joinButton = itemView.findViewById(R.id.btn_join);
                cardView = (CardView) itemView;
                detailsLayout = itemView.findViewById(R.id.details_layout);

                itemView.setOnClickListener(v -> {
                    expanded = !expanded;
                    detailsLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
                });
            }

            void bind(Event event) {
                titleText.setText(event.getTitle());
                timeText.setText(event.getTime());
                locationText.setText(event.getLocation());
                attendeesText.setText(event.getAttendees());
                descriptionText.setText(event.getDescription());

                if (event.isJoined()) {
                    joinButton.setText("Joined ✓");
                    joinButton.setEnabled(false);
                } else {
                    joinButton.setText("Join");
                    joinButton.setEnabled(true);
                    joinButton.setOnClickListener(v -> {
                        event.setJoined(true);
                        joinButton.setText("Joined ✓");
                        joinButton.setEnabled(false);
                    });
                }
            }
        }
    }
}
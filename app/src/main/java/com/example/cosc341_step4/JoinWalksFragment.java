package com.example.cosc341_step4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class JoinWalksFragment extends Fragment {

    private RecyclerView recyclerView;
    private WalkAdapter adapter;
    private List<Walk> walks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_walks, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_walks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadWalks();

        adapter = new WalkAdapter(walks);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadWalks() {
        walks = new ArrayList<>();
        walks.add(new Walk(1, "Beach Walk", "Enjoy a relaxing walk on the beach",
                "3:00 PM - 4:00 PM", "Beach Parking", "Beach End", "8 people", false));
        walks.add(new Walk(2, "Usual Water Street walk", "Our regular downtown walk",
                "5:00 PM - 6:00 PM", "Water Street Cafe", "City Park", "6 people", false));
        walks.add(new Walk(3, "BNA to Queensway Pet Meet", "Walk from brewery to park",
                "2:00 PM - 3:30 PM", "BNA Brewery", "Queensway Park", "10 people", false));
        walks.add(new Walk(4, "Kitty Walk across the beach",
                "Hello! We are gonna be hosting our usual pet meetups. All owners and volunteers are welcome. " +
                        "We also have free cheese crackers (first come, first serve). Do make sure to bring your own " +
                        "disposable bags for any waste. Kids are welcome too!\n\n" +
                        "- 4:00 PM - 5:00 PM (PST)\n" +
                        "- Queenway Beach\n" +
                        "- South Downtown Beach\n" +
                        "- 5 people (3 walking right now)",
                "4:00 PM - 5:00 PM", "Queenway Beach", "South Downtown Beach", "5 people", false));
        walks.add(new Walk(5, "St Paul Street walk", "Urban walk through downtown",
                "1:00 PM - 2:00 PM", "St Paul Church", "Library", "4 people", false));
        walks.add(new Walk(6, "Lake Country Leisure Walk with Leyla",
                "Hey it's Leyla! I just organized this impromptu lake night walk with my adorable new labrador. " +
                        "I'd love some company and we can also get food at the pub later if anyone's down? Cheers!\n\n" +
                        "- 6:30 - 8:30 PM (PST)\n" +
                        "- Lake Hill Drive\n" +
                        "- Turtle Boy Pub\n" +
                        "- 1 people (0 walking right now)",
                "6:30 PM - 8:30 PM", "Lake Hill Drive", "Turtle Boy Pub", "1 people", false));
    }

    class WalkAdapter extends RecyclerView.Adapter<WalkAdapter.ViewHolder> {
        private List<Walk> walks;

        WalkAdapter(List<Walk> walks) {
            this.walks = walks;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_walk, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Walk walk = walks.get(position);
            holder.bind(walk);
        }

        @Override
        public int getItemCount() {
            return walks.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titleText, timeText, participantsText, descriptionText;
            private TextView startLocationText, endLocationText;
            private Button joinButton;
            private LinearLayout detailsLayout;
            private boolean expanded = false;

            ViewHolder(View itemView) {
                super(itemView);
                titleText = itemView.findViewById(R.id.walk_title);
                timeText = itemView.findViewById(R.id.walk_time);
                participantsText = itemView.findViewById(R.id.walk_participants);
                descriptionText = itemView.findViewById(R.id.walk_description);
                startLocationText = itemView.findViewById(R.id.walk_start_location);
                endLocationText = itemView.findViewById(R.id.walk_end_location);
                joinButton = itemView.findViewById(R.id.btn_join);
                detailsLayout = itemView.findViewById(R.id.details_layout);

                itemView.setOnClickListener(v -> {
                    expanded = !expanded;
                    detailsLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
                });
            }

            void bind(Walk walk) {
                titleText.setText(walk.getTitle());
                timeText.setText(walk.getTime());
                participantsText.setText("👥 " + walk.getParticipants());
                descriptionText.setText(walk.getDescription());
                startLocationText.setText("📍 Start: " + walk.getStartLocation());
                if (walk.getEndLocation() != null && !walk.getEndLocation().isEmpty()) {
                    endLocationText.setText("🏁 End: " + walk.getEndLocation());
                } else {
                    endLocationText.setVisibility(View.GONE);
                }

                if (walk.isJoined()) {
                    joinButton.setText("Joined ✓");
                    joinButton.setEnabled(false);
                } else {
                    joinButton.setText("Join");
                    joinButton.setEnabled(true);
                    joinButton.setOnClickListener(v -> {
                        walk.setJoined(true);
                        joinButton.setText("Joined ✓");
                        joinButton.setEnabled(false);
                    });
                }
            }
        }
    }
}
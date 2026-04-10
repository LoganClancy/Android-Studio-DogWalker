package com.example.cosc341_step4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PeopleJoiningFragment extends Fragment {

    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private List<Person> people;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_joining, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_people);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadPeople();

        adapter = new PersonAdapter(people);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadPeople() {
        people = new ArrayList<>();
        people.add(new Person("John Lennon", "#2196F3"));
        people.add(new Person("Trent Reznor", "#F44336"));
        people.add(new Person("Lex Luther", "#4CAF50"));
    }

    class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
        private List<Person> people;

        PersonAdapter(List<Person> people) {
            this.people = people;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_person, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Person person = people.get(position);
            holder.bind(person);
        }

        @Override
        public int getItemCount() {
            return people.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView nameText, initialText;
            private CardView circleCard;

            ViewHolder(View itemView) {
                super(itemView);
                nameText = itemView.findViewById(R.id.person_name);
                initialText = itemView.findViewById(R.id.person_initial);
                circleCard = itemView.findViewById(R.id.circle_card);
            }

            void bind(Person person) {
                nameText.setText(person.getName());
                initialText.setText(person.getName().substring(0, 1));
                circleCard.setCardBackgroundColor(android.graphics.Color.parseColor(person.getColor()));
            }
        }
    }
}
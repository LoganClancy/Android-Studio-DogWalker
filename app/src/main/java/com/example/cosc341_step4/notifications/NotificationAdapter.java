package com.example.cosc341_step4.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosc341_step4.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationEntry> notifs;

    public NotificationAdapter(List<NotificationEntry> notifs) {
        this.notifs = notifs;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, message, time;
        ImageButton delete;

        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.notif_icon);
            title = view.findViewById(R.id.notif_title);
            message = view.findViewById(R.id.notif_message);
            time = view.findViewById(R.id.time);
            delete = view.findViewById(R.id.delete_button);
        }
    }

    public void addNotif(String title, String msg, int iconID){
        notifs.add(new NotificationEntry(title,msg, iconID));
        notifyItemInserted(notifs.size() - 1);
    }

    public void addNotif(String title, String msg){
        addNotif(title, msg, R.drawable.mayling_sob);
    }

    public void removeNotif(int i){
        notifs.remove(i);
        notifyItemRemoved(i);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationEntry notification = notifs.get(position);
        holder.icon.setImageResource(notification.iconID);
        holder.title.setText(notification.title);
        holder.message.setText(notification.message);
        holder.time.setText(notification.time);
        holder.delete.setOnClickListener(v -> {
            removeNotif(holder.getAbsoluteAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return this.notifs.size();
    }
}

package com.example.solofly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solofly.Model.ReminderModel;
import com.example.solofly.R;

import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    Context context;
    ArrayList<ReminderModel> reminderModelArrayList;

    public ReminderAdapter(Context context, ArrayList<ReminderModel> reminderModelArrayList) {
        this.context = context;
        this.reminderModelArrayList = reminderModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reminder_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.reminderTitle.setText(reminderModelArrayList.get(position).reminderTitle);
        holder.reminderShortNote.setText(reminderModelArrayList.get(position).reminderShortNote);
        holder.reminderDate.setText(reminderModelArrayList.get(position).reminderDate);
        holder.reminderTime.setText(reminderModelArrayList.get(position).reminderTime);
        holder.remindBefore.setText(reminderModelArrayList.get(position).remindBefore);
        holder.reminderRepeat.setText(reminderModelArrayList.get(position).reminderRepeat);
    }

    @Override
    public int getItemCount() {
        return reminderModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reminderTitle, reminderShortNote, remindBefore, reminderRepeat, reminderDate, reminderTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reminderTitle = itemView.findViewById(R.id.reminder_layout_title);
            reminderShortNote = itemView.findViewById(R.id.reminder_layout_short_note);
            reminderDate = itemView.findViewById(R.id.reminder_layout_date);
            reminderTime = itemView.findViewById(R.id.reminder_layout_time);
            remindBefore = itemView.findViewById(R.id.reminder_layout_remind_before);
            reminderRepeat = itemView.findViewById(R.id.reminder_layout_repeat);
        }
    }
}

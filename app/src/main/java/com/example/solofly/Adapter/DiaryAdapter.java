package com.example.solofly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solofly.Model.DiaryModel;
import com.example.solofly.R;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    ArrayList<DiaryModel> diaryModelArrayList;
    Context context;

    public DiaryAdapter(ArrayList<DiaryModel> diaryModelArrayList, Context context) {
        this.diaryModelArrayList = diaryModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diary_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.diaryDate.setText(diaryModelArrayList.get(position).getDiaryDate());
        holder.diaryTitle.setText(diaryModelArrayList.get(position).getDiaryTitle());
        holder.diaryNote.setText(diaryModelArrayList.get(position).getDiaryNote());
    }

    @Override
    public int getItemCount() {
        return diaryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView diaryDate, diaryTitle, diaryNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diaryDate = itemView.findViewById(R.id.diary_item_date);
            diaryTitle = itemView.findViewById(R.id.diary_item_title);
            diaryNote = itemView.findViewById(R.id.diary_item_note);
        }
    }
}

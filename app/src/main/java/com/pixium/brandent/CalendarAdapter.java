package com.pixium.brandent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private ArrayList<TasksCalendarItem> mTasksCalendarItems;

    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        public TextView initial_tv;
        public TextView no_tv;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            initial_tv = itemView.findViewById(R.id.tv_day_initial);
            no_tv = itemView.findViewById(R.id.tv_day_no);
        }
    }

    public CalendarAdapter(ArrayList<TasksCalendarItem> tasksCalendarItems) {
        mTasksCalendarItems = tasksCalendarItems;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_calendar_tasks, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        TasksCalendarItem currentItem = mTasksCalendarItems.get(position);

        holder.initial_tv.setText(currentItem.getInitial());
        holder.no_tv.setText(currentItem.getNo());
    }

    @Override
    public int getItemCount() {
        return mTasksCalendarItems.size();
    }
}

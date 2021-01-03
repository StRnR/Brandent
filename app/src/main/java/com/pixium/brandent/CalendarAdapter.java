package com.pixium.brandent;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private ArrayList<TasksCalendarItem> mTasksCalendarItems;
    private int mSelectedPos;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        public TextView initial_tv;
        public TextView no_tv;
        public CardView calendar_cv;

        public CalendarViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            initial_tv = itemView.findViewById(R.id.tv_day_initial);
            no_tv = itemView.findViewById(R.id.tv_day_no);
            calendar_cv = itemView.findViewById(R.id.cv_calendar_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public CalendarAdapter(ArrayList<TasksCalendarItem> tasksCalendarItems, int selectedPos) {
        mTasksCalendarItems = tasksCalendarItems;
        mSelectedPos = selectedPos;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_calendar_tasks, parent, false);
        return new CalendarViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        TasksCalendarItem currentItem = mTasksCalendarItems.get(position);

        holder.initial_tv.setText(currentItem.getInitial());
        holder.no_tv.setText(currentItem.getNo());

        if (position == mSelectedPos) {
            holder.initial_tv.setTextColor(Color.WHITE);
            holder.no_tv.setTextColor(Color.WHITE);
            holder.calendar_cv.setCardBackgroundColor(Color.parseColor("#FF7603"));
        } else {
            holder.initial_tv.setTextColor(Color.parseColor("#a4a5a8"));
            holder.no_tv.setTextColor(Color.BLACK);
            holder.calendar_cv.setCardBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return mTasksCalendarItems.size();
    }

    public void setSelectedPos(int position) {
        mSelectedPos = position;
        notifyDataSetChanged();
    }
}

package com.pixium.clinitick.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.clinitick.R;
import com.pixium.clinitick.models.TasksCardModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TasksAdapter extends
        RecyclerView.Adapter<TasksAdapter.TasksHolder> {
    private List<TasksCardModel> tasksCardModels = new ArrayList<>();
    private OnItemClickListener mClickListener;
    private OnItemCheckClickListener mCheckListener;
    private OnItemCancelClickListener mCancelListener;
    private OnItemUnknownClickListener mUnknownListener;

    public void setOnItemCheckClickListener(OnItemCheckClickListener listener) {
        mCheckListener = listener;
    }

    public void setOnItemCancelClickListener(OnItemCancelClickListener listener) {
        mCancelListener = listener;
    }

    public void setOnItemUnknownClickListener(OnItemUnknownClickListener listener) {
        mUnknownListener = listener;
    }

    @NonNull
    @Override
    public TasksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tasks_appointment_item, parent, false);
        return new TasksHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksHolder holder, int position) {
        TasksCardModel current = tasksCardModels.get(position);
        Timestamp timestamp = new Timestamp(current.getTime());
        String timeStr = Character.toString(timestamp.toString().charAt(11))
                + timestamp.toString().charAt(12)
                + timestamp.toString().charAt(13)
                + timestamp.toString().charAt(14)
                + timestamp.toString().charAt(15);

        holder.timeTv.setText(timeStr);
        holder.titleTv.setText(current.getTitle());
        holder.descriptionTv.setText(current.getDescription());
        switch (current.getState()) {
            case "unknown":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                break;
            case "done":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_enabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                break;
            case "canceled":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_enabled);
                break;
        }

        holder.checkBtn.setOnClickListener(v -> {
            if (current.getState().equals("unknown") ||
                    current.getState().equals("canceled")) {
                if (mCheckListener != null) {
                    try {
                        mCheckListener.onItemCheckClick(current);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (mUnknownListener != null) {
                    try {
                        mUnknownListener.onItemUnknownClick(current);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        holder.cancelBtn.setOnClickListener(v -> {
            if (current.getState().equals("unknown") ||
                    current.getState().equals("done")) {
                if (mCancelListener != null) {
                    try {
                        mCancelListener.onItemCancelClick(current);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (mUnknownListener != null) {
                    try {
                        mUnknownListener.onItemUnknownClick(current);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasksCardModels.size();
    }

    public void setTasksCardModels(List<TasksCardModel> tasksCardModels) {
        this.tasksCardModels = tasksCardModels;
        notifyDataSetChanged();
    }

    public interface OnItemCheckClickListener {
        void onItemCheckClick(TasksCardModel tasksCardModel) throws ExecutionException, InterruptedException;
    }

    public interface OnItemCancelClickListener {
        void onItemCancelClick(TasksCardModel tasksCardModel) throws ExecutionException, InterruptedException;
    }

    public interface OnItemUnknownClickListener {
        void onItemUnknownClick(TasksCardModel tasksCardModel) throws ExecutionException, InterruptedException;
    }

    class TasksHolder extends RecyclerView.ViewHolder {
        private final TextView timeTv;
        private final TextView titleTv;
        private final TextView descriptionTv;
        private final Button checkBtn;
        private final Button cancelBtn;

        public TasksHolder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.tv_time_tasks_appointment_cv);
            titleTv = itemView.findViewById(R.id.tv_patient_name_tasks_appointment_cv);
            descriptionTv = itemView.findViewById(R.id.tv_title_tasks_appointment_cv);
            checkBtn = itemView.findViewById(R.id.btn_check_tasks_appointment_cv);
            cancelBtn = itemView.findViewById(R.id.btn_cancel_tasks_appointment_cv);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (mClickListener != null && position != RecyclerView.NO_POSITION) {
                    mClickListener.onItemClick(tasksCardModels.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TasksCardModel tasksCardModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }
}

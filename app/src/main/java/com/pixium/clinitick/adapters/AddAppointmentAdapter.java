package com.pixium.clinitick.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.clinitick.R;
import com.pixium.clinitick.UiTools;
import com.pixium.clinitick.models.AddAppointmentModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class AddAppointmentAdapter extends RecyclerView.Adapter<AddAppointmentAdapter.AddAppointmentHolder> {
    private List<AddAppointmentModel> addAppointmentModels = new ArrayList<>();
    private OnDateClickListener listener;

    @NonNull
    @Override
    public AddAppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_appointment_item, parent, false);
        return new AddAppointmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddAppointmentHolder holder, int position) {
        AddAppointmentModel currentAdd = addAppointmentModels.get(position);
        if (!holder.titleEt.getText().toString().equals(currentAdd.getTitle())) {
            holder.titleEt.setText(currentAdd.getTitle());
        }
        try {
            if (currentAdd.getPrice() != null) {
                if (!UiTools.stringToPrice(holder.priceEt.getText().toString()).equals(currentAdd.getPrice())) {
                    holder.priceEt.setText(UiTools.priceToString(currentAdd.getPrice(), true));
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (currentAdd.getTimeStamp() != null) {
                PersianDate pDate = new PersianDate(currentAdd.getTimeStamp());
                PersianCalendar initDate = new PersianCalendar();
                initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());
                Calendar visitCalendar = GregorianCalendar.getInstance();
                visitCalendar.setTimeInMillis(initDate.getTimeInMillis());
                PersianDateFormat formatter = new PersianDateFormat("H:i");
                String timeStr = initDate.getPersianLongDate() + " - " + formatter.format(pDate);
                holder.timeBtn.setText(timeStr);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        holder.titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AddAppointmentModel currentAdd = addAppointmentModels.get(position);
                currentAdd.setTitle(holder.titleEt.getText().toString());
                addAppointmentModels.set(position, currentAdd);
            }
        });

        holder.priceEt.addTextChangedListener(new TextWatcher() {
            String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    holder.priceEt.removeTextChangedListener(this);

                    String formatted = UiTools.priceToString(UiTools.stringToPrice(s.toString())
                            , true);
                    current = formatted;
                    holder.priceEt.setText(formatted);
                    holder.priceEt.setSelection(formatted.length() - 6);

                    holder.priceEt.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                AddAppointmentModel currentAdd = addAppointmentModels.get(position);
                currentAdd.setPrice(UiTools.stringToPrice(holder.priceEt.getText().toString()));
                addAppointmentModels.set(position, currentAdd);
            }
        });

        if (listener != null) {
            holder.timeBtn.setOnClickListener(v -> {
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onDateClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return addAppointmentModels.size();
    }

    public void setAddAppointments(List<AddAppointmentModel> addAppointments) {
        this.addAppointmentModels = addAppointments;
        notifyDataSetChanged();
    }

    public List<AddAppointmentModel> getAddAppointments() {
        return addAppointmentModels;
    }

    class AddAppointmentHolder extends RecyclerView.ViewHolder {
        private final EditText titleEt;
        private final EditText priceEt;
        private final Button timeBtn;

        public AddAppointmentHolder(@NonNull View itemView) {
            super(itemView);
            titleEt = itemView.findViewById(R.id.et_title_appointment_add);
            priceEt = itemView.findViewById(R.id.et_price_appointment_add);
            timeBtn = itemView.findViewById(R.id.btn_time_appointment_add);
        }
    }

    public interface OnDateClickListener {
        void onDateClick(int position);
    }

    public void setOnDateClickListener(OnDateClickListener listener) {
        this.listener = listener;
    }
}

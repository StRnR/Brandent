package com.pixium.clinitick.views;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.clinitick.ActiveUser;
import com.pixium.clinitick.R;
import com.pixium.clinitick.UiTools;
import com.pixium.clinitick.db.entities.Finance;
import com.pixium.clinitick.viewmodels.AddFinanceViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;

public class AddEditFinanceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_FINANCE_ID =
            "com.pixium.brandent.EXTRA_FINANCE_ID";
    private int prevFinanceId = -1;
    private String financeType;
    private AddFinanceViewModel addFinanceViewModel;
    private Calendar dateCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_finance);

        TextView headerTv = findViewById(R.id.tv_header_finance_add);

        EditText titleEt = findViewById(R.id.et_title_finance_add);
        EditText amountEt = findViewById(R.id.et_amount_finance_add);

        Button backBtn = findViewById(R.id.btn_back_finance_add);
        Button dateBtn = findViewById(R.id.btn_date_finance_add);
        Button submitBtn = findViewById(R.id.btn_submit_finance_add);

        addFinanceViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(AddFinanceViewModel.class);

        Spinner financeTypeSpinner = findViewById(R.id.spinner_type_finance_add);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this
                , R.array.finance_type, R.layout.spinner_finance_type_item);
        adapter.setDropDownViewResource(R.layout.appointment_add_spinner_dropdown_item);
        financeTypeSpinner.setAdapter(adapter);
        financeTypeSpinner.setOnItemSelectedListener(this);


        amountEt.addTextChangedListener(new TextWatcher() {
            String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    amountEt.removeTextChangedListener(this);

                    String formatted = UiTools.priceToString(UiTools.stringToPrice(s.toString()), true);

                    current = formatted;
                    amountEt.setText(formatted);
                    amountEt.setSelection(formatted.length() - 6);

                    amountEt.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // Date & Time Picker
        PersianDate pDate = new PersianDate();
        PersianCalendar initDate = new PersianCalendar();
        initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_FINANCE_ID)) {
            try {
                Finance finance = addFinanceViewModel.getFinanceById(intent
                        .getIntExtra(EXTRA_FINANCE_ID, -1));
                prevFinanceId = finance.getFinanceId();
                headerTv.setText("ویرایش تراکنش");
                titleEt.setText(finance.getTitle());
                amountEt.setText(UiTools.priceToString(finance.getPrice(), true));
                if (finance.getType().equals("EXPENSE")) {
                    financeType = "EXPENSE";
                    financeTypeSpinner.setSelection(1);
                } else {
                    financeType = "INCOME";
                    financeTypeSpinner.setSelection(2);
                }
                pDate = new PersianDate(finance.getDate());
                initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());
                dateBtn.setText(initDate.getPersianLongDate());
                dateCalendar = GregorianCalendar.getInstance();
                dateCalendar.setTimeInMillis(initDate.getTimeInMillis());
                dateCalendar.set(Calendar.HOUR_OF_DAY, 0);
                dateCalendar.set(Calendar.MINUTE, 0);
                dateCalendar.set(Calendar.SECOND, 0);
                dateCalendar.set(Calendar.MILLISECOND, 0);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        Typeface typeface = Typeface.DEFAULT;

        dateBtn.setOnClickListener(v -> {
            PersianDatePickerDialog picker = new PersianDatePickerDialog(this);
            picker.setPositiveButtonString("باشه");
            picker.setNegativeButton("بیخیال");
            picker.setTodayButton("امروز");
            picker.setTodayButtonVisible(true);
            picker.setMinYear(1300);
            picker.setMaxYear(PersianDatePickerDialog.THIS_YEAR);
            picker.setInitDate(initDate);
            picker.setActionTextColor(ContextCompat.getColor(this, R.color.clinitickPurple));
            picker.setTypeFace(typeface);
            picker.setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR);
            picker.setShowInBottomSheet(true);
            picker.setListener(new Listener() {
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
                    String dateStr = persianCalendar.getPersianLongDate();
                    dateBtn.setText(dateStr);
                    dateCalendar = GregorianCalendar.getInstance();
                    dateCalendar.setTimeInMillis(persianCalendar.getTimeInMillis());
                    dateCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    dateCalendar.set(Calendar.MINUTE, 0);
                    dateCalendar.set(Calendar.SECOND, 0);
                    dateCalendar.set(Calendar.MILLISECOND, 0);
                }

                @Override
                public void onDismissed() {
                }
            });

            picker.show();
        });

        submitBtn.setOnClickListener(v -> {
            if (prevFinanceId != -1) {
                if (titleEt.getText().toString().equals("") || amountEt.getText().toString().equals("")) {
                    Toast.makeText(this, "Please fill the title and/or the amount field"
                            , Toast.LENGTH_SHORT).show();
                } else if (financeType.equals("")) {
                    Toast.makeText(this, "Please select either expense or income"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Finance prevFinance = addFinanceViewModel.getFinanceById(prevFinanceId);
                        Finance updateFinance = new Finance(ActiveUser.getInstance().getId()
                                , prevFinance.getUuid(), null, dateCalendar.getTimeInMillis()
                                , UiTools.stringToPrice(amountEt.getText().toString())
                                , titleEt.getText().toString(), financeType, 0);
                        updateFinance.setFinanceId(prevFinanceId);
                        addFinanceViewModel.updateFinance(updateFinance);
                        startActivity(new Intent(this, FinanceActivity.class));
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (titleEt.getText().toString().equals("") || amountEt.getText().toString().equals("")) {
                    Toast.makeText(this, "Please fill the title and/or the amount field"
                            , Toast.LENGTH_SHORT).show();
                } else if (financeType.equals("")) {
                    Toast.makeText(this, "Please select either expense or income"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Finance addFinance = new Finance(ActiveUser.getInstance().getId(), null
                            , null, dateCalendar.getTimeInMillis()
                            , UiTools.stringToPrice(amountEt.getText().toString())
                            , titleEt.getText().toString(), financeType, 0);
                    addFinanceViewModel.insertFinance(addFinance);
                    startActivity(new Intent(this, FinanceActivity.class));
                }
            }
        });


        backBtn.setOnClickListener(v -> onBackPressed());

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 1) {
            financeType = "EXPENSE";
        } else if (position == 2) {
            financeType = "INCOME";
        } else {
            financeType = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
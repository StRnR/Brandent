package com.pixium.clinitick.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.pixium.clinitick.R;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText titleEt = findViewById(R.id.et_title_task_add);

        Button timeBtn = findViewById(R.id.btn_time_task_add);
        Button submitBtn = findViewById(R.id.btn_submit_task_add);

        Spinner spinner = findViewById(R.id.spinner_clinic_task_add);


    }
}
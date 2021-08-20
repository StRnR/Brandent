package com.brandent.clinitick.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.brandent.clinitick.R;
import com.brandent.clinitick.viewmodels.ForgotPassViewModel;

public class ForgotPassActivity extends AppCompatActivity {
    private ForgotPassViewModel forgotPassViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        forgotPassViewModel = new ViewModelProvider(this
                , ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(ForgotPassViewModel.class);

        EditText phoneEt = findViewById(R.id.et_phone_forgot_pass);

        Button backBtn = findViewById(R.id.btn_back_forgot_pass);
        Button submitBtn = findViewById(R.id.btn_submit_forgot_pass);

        submitBtn.setOnClickListener(v -> {
            if (!phoneEt.getText().toString().trim().equals("")
                    && phoneEt.getText().toString().length() == 11) {
                String phoneNo = phoneEt.getText().toString();
                forgotPassViewModel.init(phoneNo);

                forgotPassViewModel.postForgotPassPhone().observe(this, integer -> {
                    try {
                        if (integer == 204) {
                            Intent intent = new Intent(ForgotPassActivity.this
                                    , ConfirmCodeActivity.class);
                            intent.putExtra("phone", phoneNo);
                            intent.putExtra("scenario", "forgot_pass");
                            startActivity(intent);
                        } else if (integer == 404) {
                            Toast.makeText(ForgotPassActivity.this
                                    , "Phone number doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NullPointerException e) {
                        Toast.makeText(ForgotPassActivity.this
                                , "Something went wrong. Please try again later."
                                , Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please enter a valid number!"
                        , Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(v -> onBackPressed());
    }
}
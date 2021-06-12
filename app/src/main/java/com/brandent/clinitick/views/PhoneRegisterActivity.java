package com.brandent.clinitick.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.brandent.clinitick.R;
import com.brandent.clinitick.viewmodels.PhoneRegisterViewModel;

public class PhoneRegisterActivity extends AppCompatActivity {
    private PhoneRegisterViewModel phoneRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);
        phoneRegisterViewModel = new ViewModelProvider(this
                , ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(PhoneRegisterViewModel.class);

        Button submitBtn = findViewById(R.id.btn_submit_phone_register);
        Button backBtn = findViewById(R.id.btn_back_phone_register);

        EditText phoneEt = findViewById(R.id.et_phone_register);

        submitBtn.setOnClickListener(v -> {
            if (!phoneEt.getText().toString().equals("")
                    && phoneEt.getText().toString().length() == 11) {
                String phoneNo = phoneEt.getText().toString();
                phoneRegisterViewModel.init(phoneNo);
                phoneRegisterViewModel.postPhone().observe(this, messageResponse -> {
                    try {
                        String message = messageResponse.getMessage();
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        if (message.equals("code sent")) {
                            Intent intent = new Intent(this, ConfirmCodeActivity.class);
                            intent.putExtra("phone", phoneNo);
                            startActivity(intent);
                        }
                    } catch (NullPointerException e) {
                        Toast.makeText(this, "null object reference"
                                , Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please enter a valid number!"
                        , Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(v -> super.onBackPressed());
    }
}
package com.pixium.brandent.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.brandent.ActiveUser;
import com.pixium.brandent.R;
import com.pixium.brandent.api.models.auth.RegisterRequest;
import com.pixium.brandent.db.entities.Dentist;
import com.pixium.brandent.viewmodels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel registerViewModel;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new ViewModelProvider(this
                , ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(RegisterViewModel.class);

        phone = getIntent().getStringExtra("phone");

        Button submitBtn = findViewById(R.id.btn_register_submit);
        Button backBtn = findViewById(R.id.btn_back_register);

        EditText firstNameEt = findViewById(R.id.et_register_name);
        EditText lastNameEt = findViewById(R.id.et_register_last_name);
        EditText specialityEt = findViewById(R.id.et_register_speciality);
        EditText passwordEt = findViewById(R.id.et_register_password);

        submitBtn.setOnClickListener(v -> {
            if (!firstNameEt.getText().toString().equals("")
                    && !lastNameEt.getText().toString().equals("")
                    && !specialityEt.getText().toString().equals("")
                    && !passwordEt.getText().toString().equals("")) {
                RegisterRequest registerRequest = new RegisterRequest(phone
                        , passwordEt.getText().toString(), firstNameEt.getText().toString()
                        , lastNameEt.getText().toString(), specialityEt.getText().toString());
                registerViewModel.registerDentist(registerRequest)
                        .observe(this, authResponse -> {
                            try {
                                String message = authResponse.getMessage();
                                Toast.makeText(this, message, Toast.LENGTH_SHORT)
                                        .show();
                                if (message.equals("success")) {
                                    Dentist dentist = new Dentist(authResponse.getUser().getId()
                                            , authResponse.getUser().getFirst_name()
                                            , authResponse.getUser().getLast_name()
                                            , authResponse.getUser().getPhone()
                                            , authResponse.getUser().getSpeciality()
                                            , authResponse.getUser().getImage()
                                            , 1, authResponse.getToken()
                                            , System.currentTimeMillis());
                                    registerViewModel.insertDentist(dentist);
                                    ActiveUser.setActiveUser(dentist.getDentistId());
                                    startActivity(new Intent(this, HomeActivity.class));
                                    finish();
                                }
                            } catch (NullPointerException e) {
                                Toast.makeText(this, "Null reference", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT)
                        .show();
            }
        });


        backBtn.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(this, PhoneRegisterActivity.class));
        });
    }
}
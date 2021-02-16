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
import com.pixium.brandent.api.models.auth.LoginRequest;
import com.pixium.brandent.db.entities.Dentist;
import com.pixium.brandent.viewmodels.IntroViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class IntroActivity extends AppCompatActivity {
    private IntroViewModel introViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Button registerBtn = findViewById(R.id.btn_register_intro);
        Button loginBtn = findViewById(R.id.btn_login_intro);

        EditText phoneEt = findViewById(R.id.et_phone_intro);
        EditText passwordEt = findViewById(R.id.et_password_intro);

        introViewModel = new ViewModelProvider(this
                , ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(IntroViewModel.class);

        introViewModel.getAllDentistsLive().observe(this, dentists -> {
            List<Dentist> currentDentists = new ArrayList<>();
            for (Dentist dentist : dentists) {
                if (dentist.getCurrent() == 1) {
                    currentDentists.add(dentist);
                }
            }

            if (currentDentists.size() == 1) {
                ActiveUser.setActiveUser(currentDentists.get(0).getDentistId());
                startActivity(new Intent(this, HomeActivity.class));
            } else if (currentDentists.size() >= 1) {
                for (Dentist dentist : currentDentists) {
                    Dentist updateDentist = new Dentist(dentist.getDentistId()
                            , dentist.getFirstName(), dentist.getLastName(), dentist.getPhone()
                            , dentist.getSpeciality(), dentist.getImageName()
                            , 0, dentist.getToken());
                    introViewModel.updateDentist(updateDentist);
                }
            }
        });

        loginBtn.setOnClickListener(v -> {
            if (phoneEt.getText().toString().length() != 11) {
                Toast.makeText(this, "Please enter a valid phone number!"
                        , Toast.LENGTH_SHORT).show();
            } else {
                if (passwordEt.getText().toString().equals("")) {
                    Toast.makeText(this, "Please enter your password!"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    LoginRequest loginRequest = new LoginRequest(phoneEt.getText().toString()
                            , passwordEt.getText().toString());
                    introViewModel.loginDentist(loginRequest).observe(this, authResponse -> {
                        try {
                            String message = authResponse.getMessage();
                            Toast.makeText(this, message, Toast.LENGTH_SHORT)
                                    .show();
                            if (message.equals("success")) {
                                Dentist dentist = introViewModel
                                        .getDentistById(authResponse.getUser().getId());
                                Dentist updateDentist = new Dentist(authResponse.getUser().getId()
                                        , authResponse.getUser().getFirst_name()
                                        , authResponse.getUser().getLast_name()
                                        , authResponse.getUser().getPhone()
                                        , authResponse.getUser().getSpeciality()
                                        , authResponse.getUser().getImage()
                                        , 1, authResponse.getToken());
                                if (dentist == null) {
                                    introViewModel.insertDentist(updateDentist);
                                } else {
                                    introViewModel.updateDentist(updateDentist);
                                }
                                ActiveUser.setActiveUser(updateDentist.getDentistId());
                                startActivity(new Intent(this, HomeActivity.class));
                                finish();
                            }
                        } catch (NullPointerException e) {
                            Toast.makeText(this, "Null reference", Toast.LENGTH_SHORT)
                                    .show();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

        registerBtn.setOnClickListener(v ->
                startActivity(new Intent(this, PhoneRegisterActivity.class)));
    }
}
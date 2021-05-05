package com.pixium.clinitick.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.clinitick.ActiveUser;
import com.pixium.clinitick.DateTools;
import com.pixium.clinitick.ModelConverter;
import com.pixium.clinitick.R;
import com.pixium.clinitick.api.models.Appointment;
import com.pixium.clinitick.api.models.Task;
import com.pixium.clinitick.api.models.auth.LoginRequest;
import com.pixium.clinitick.api.models.sync.SyncRequest;
import com.pixium.clinitick.db.entities.Dentist;
import com.pixium.clinitick.viewmodels.IntroViewModel;

import java.text.ParseException;
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

        List<Dentist> dentists = new ArrayList<>();
        try {
            dentists = introViewModel.getAllDentists();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        List<Dentist> currentDentists = new ArrayList<>();
        for (Dentist dentist : dentists) {
            if (dentist.getCurrent() == 1) {
                currentDentists.add(dentist);
            }
        }

        if (currentDentists.size() == 1) {
            ActiveUser.setActiveUser(currentDentists.get(0).getDentistId());
            loginBtn.setEnabled(false);
            sync(currentDentists.get(0));
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (currentDentists.size() > 1) {
            for (Dentist dentist : currentDentists) {
                Dentist updateDentist = new Dentist(dentist.getDentistId()
                        , dentist.getFirstName(), dentist.getLastName(), dentist.getPhone()
                        , dentist.getSpeciality(), dentist.getImageName()
                        , 0, dentist.getToken(), dentist.getLastUpdated());
                introViewModel.updateDentist(updateDentist);
            }
        }

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
                                if (dentist == null) {
                                    Dentist insertDentist = new Dentist(authResponse.getUser().getId()
                                            , authResponse.getUser().getFirst_name()
                                            , authResponse.getUser().getLast_name()
                                            , authResponse.getUser().getPhone()
                                            , authResponse.getUser().getSpeciality()
                                            , authResponse.getUser().getImage()
                                            , 1, authResponse.getToken()
                                            , DateTools.timestampFromString(DateTools.oldLastUpdated
                                            , DateTools.apiTimeFormat));
                                    introViewModel.insertDentist(insertDentist);
                                    ActiveUser.setActiveUser(insertDentist.getDentistId());
                                    sync(insertDentist);
                                    Intent intent = new Intent(this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Dentist updateDentist = new Dentist(authResponse.getUser().getId()
                                            , authResponse.getUser().getFirst_name()
                                            , authResponse.getUser().getLast_name()
                                            , authResponse.getUser().getPhone()
                                            , authResponse.getUser().getSpeciality()
                                            , authResponse.getUser().getImage()
                                            , 1, authResponse.getToken()
                                            , dentist.getLastUpdated());
                                    introViewModel.updateDentist(updateDentist);
                                    ActiveUser.setActiveUser(updateDentist.getDentistId());
                                    sync(updateDentist);
                                    Intent intent = new Intent(this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(this, message, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        } catch (NullPointerException e) {
                            Toast.makeText(this, "Null reference", Toast.LENGTH_SHORT)
                                    .show();
                        } catch (InterruptedException | ExecutionException | ParseException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

        registerBtn.setOnClickListener(v ->
                startActivity(new Intent(this, PhoneRegisterActivity.class)));
    }


    public void sync(Dentist dentist) {
        com.pixium.clinitick.db.entities.Appointment[] toSyncAppointments;
        com.pixium.clinitick.db.entities.Task[] toSyncTasks;
        try {
            toSyncAppointments = introViewModel.getUnsyncedAppointments(dentist.getLastUpdated());
        } catch (NullPointerException | ExecutionException | InterruptedException e) {
            toSyncAppointments = new com.pixium.clinitick.db.entities.Appointment[0];
            e.printStackTrace();
        }

        try {
            toSyncTasks = introViewModel.getUnsyncedTasks(dentist.getLastUpdated());
        } catch (NullPointerException | ExecutionException | InterruptedException e) {
            toSyncTasks = new com.pixium.clinitick.db.entities.Task[0];
            e.printStackTrace();
        }

        Long mLastUpdated = dentist.getLastUpdated();
        if (dentist.getLastUpdated() == null) {
            try {
                mLastUpdated =
                        DateTools.timestampFromString(DateTools.oldLastUpdated, DateTools.apiTimeFormat);
                Dentist updateDentist = new Dentist(dentist.getDentistId()
                        , dentist.getFirstName(), dentist.getLastName()
                        , dentist.getPhone(), dentist.getSpeciality()
                        , dentist.getImageName(), dentist.getCurrent()
                        , dentist.getToken()
                        , mLastUpdated);

                introViewModel.updateDentist(updateDentist);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        SyncRequest syncRequest = null;
        try {
            syncRequest = new
                    SyncRequest(DateTools.stringFromTimestamp(mLastUpdated, DateTools.apiTimeFormat)
                    , ModelConverter.clinicsToSync(introViewModel.getUnsyncedClinics(mLastUpdated))
                    , ModelConverter.patientsToSync(introViewModel.getUnsyncedPatients(mLastUpdated))
                    , ModelConverter.financesToSync(introViewModel.getUnsyncedFinances(mLastUpdated))
                    , ModelConverter.appointmentsToSync(introViewModel.getAppointmentClinicForUuidStrings(toSyncAppointments)
                    , introViewModel.getPatientForUuidStrings(toSyncAppointments), toSyncAppointments)
                    , ModelConverter.tasksToSync(introViewModel.getTaskClinicForUuidStrings(toSyncTasks), toSyncTasks));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        introViewModel.sync(syncRequest, dentist.getToken()).observe(this
                , syncResponse -> {
                    try {
                        String syncMessage = syncResponse.getMessage();
                        Toast.makeText(this, syncMessage, Toast.LENGTH_SHORT)
                                .show();
                        if (syncMessage.equals("successful")) {
                            try {
                                introViewModel.insertClinics(ModelConverter
                                        .clinicsFromSync(syncResponse.getClinics()));
                            } catch (NullPointerException e) {
                                Toast.makeText(this, "clinics null"
                                        , Toast.LENGTH_SHORT).show();
                            }

                            try {
                                introViewModel.insertPatients(ModelConverter
                                        .patientsFromSync(syncResponse.getPatients()));
                            } catch (NullPointerException e) {
                                Toast.makeText(this, "patients null"
                                        , Toast.LENGTH_SHORT).show();
                            }

                            try {
                                Appointment[] appointments = syncResponse.getAppointments();
                                introViewModel.insertAppointments(ModelConverter
                                        .appointmentsFromSync(introViewModel
                                                        .getAppointmentClinicForIds(appointments)
                                                , introViewModel
                                                        .getPatientForIds(appointments)
                                                , appointments));
                            } catch (NullPointerException e) {
                                Toast.makeText(this, "appointments null"
                                        , Toast.LENGTH_SHORT).show();
                            }

                            try {
                                Task[] tasks = syncResponse.getTasks();
                                introViewModel.insertTasks(ModelConverter
                                        .tasksFromSync(introViewModel.getTaskClinicForIds(tasks), tasks));
                            } catch (NullPointerException e) {
                                Toast.makeText(this, "tasks null"
                                        , Toast.LENGTH_SHORT).show();
                            }

                            try {
                                introViewModel.insertFinances(ModelConverter
                                        .financesFromSync(syncResponse.getFinances()));
                            } catch (NullPointerException e) {
                                Toast.makeText(this, "finances null"
                                        , Toast.LENGTH_SHORT).show();
                            }
                            Dentist syncedDentist = new Dentist(dentist.getDentistId()
                                    , dentist.getFirstName(), dentist.getLastName()
                                    , dentist.getPhone(), dentist.getSpeciality()
                                    , dentist.getImageName(), 1, dentist.getToken()
                                    , System.currentTimeMillis());
                            introViewModel.updateDentist(syncedDentist);
                        }
                    } catch (NullPointerException e) {
                        Toast.makeText(this, "Null reference", Toast.LENGTH_SHORT)
                                .show();
                    } catch (InterruptedException | ParseException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
    }

}
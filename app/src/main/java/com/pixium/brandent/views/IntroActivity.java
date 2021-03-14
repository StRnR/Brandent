package com.pixium.brandent.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.brandent.ActiveUser;
import com.pixium.brandent.DateTools;
import com.pixium.brandent.ModelConverter;
import com.pixium.brandent.R;
import com.pixium.brandent.api.models.Appointment;
import com.pixium.brandent.api.models.Clinic;
import com.pixium.brandent.api.models.Finance;
import com.pixium.brandent.api.models.Patient;
import com.pixium.brandent.api.models.Task;
import com.pixium.brandent.api.models.auth.LoginRequest;
import com.pixium.brandent.api.models.sync.SyncRequest;
import com.pixium.brandent.db.entities.Dentist;
import com.pixium.brandent.viewmodels.IntroViewModel;

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

            // Sync
            com.pixium.brandent.db.entities.Appointment[] toSyncAppointments;
            try {
                toSyncAppointments = introViewModel.getUnsyncedAppointments(currentDentists.get(0).getLastUpdated());
            } catch (NullPointerException | ExecutionException | InterruptedException e) {
                toSyncAppointments = new com.pixium.brandent.db.entities.Appointment[0];
                e.printStackTrace();
            }
            Long mLastUpdated;
            if (currentDentists.get(0).getLastUpdated() == null) {
                mLastUpdated = Long.MIN_VALUE;
            } else {
                mLastUpdated = currentDentists.get(0).getLastUpdated();
            }
            SyncRequest syncRequest = null;
            try {
                syncRequest = new
                        SyncRequest(DateTools.stringFromTimestamp(mLastUpdated, DateTools.apiTimeFormat)
                        , ModelConverter.clinicsToSync(introViewModel.getUnsyncedClinics(mLastUpdated))
                        , ModelConverter.patientsToSync(introViewModel.getUnsyncedPatients(mLastUpdated))
                        , ModelConverter.financesToSync(introViewModel.getUnsyncedFinances(mLastUpdated))
                        , ModelConverter.appointmentsToSync(introViewModel.getClinicForUuidStrings(toSyncAppointments)
                        , introViewModel.getPatientForUuidStrings(toSyncAppointments), toSyncAppointments)
                        , new Task[0]);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            introViewModel.sync(syncRequest, currentDentists.get(0).getToken()).observe(this, syncResponse -> {
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
                        Thread.sleep(1000);

                        try {
                            Appointment[] appointments = syncResponse.getAppointments();
                            introViewModel.insertAppointments(ModelConverter
                                    .appointmentsFromSync(introViewModel
                                                    .getClinicForIds(appointments)
                                            , introViewModel
                                                    .getPatientForIds(appointments)
                                            , appointments));
                        } catch (NullPointerException e) {
                            Toast.makeText(this, "appointments null"
                                    , Toast.LENGTH_SHORT).show();
                        }

                        try {
                            introViewModel.insertFinances(ModelConverter
                                    .financesFromSync(syncResponse.getFinances()));
                        } catch (NullPointerException e) {
                            Toast.makeText(this, "finances null"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        Dentist syncedDentist = new Dentist(currentDentists.get(0).getDentistId()
                                , currentDentists.get(0).getFirstName()
                                , currentDentists.get(0).getLastName()
                                , currentDentists.get(0).getPhone()
                                , currentDentists.get(0).getSpeciality()
                                , currentDentists.get(0).getImageName()
                                , 1, currentDentists.get(0).getToken()
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
            startActivity(new Intent(this, HomeActivity.class));
        } else if (currentDentists.size() >= 1) {
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

                                    // Sync
                                    SyncRequest syncRequest = new
                                            SyncRequest(DateTools.oldLastUpdated, new Clinic[0]
                                            , new Patient[0], new Finance[0], new Appointment[0]
                                            , new Task[0]);
                                    introViewModel.sync(syncRequest, authResponse.getToken())
                                            .observe(this, syncResponse -> {
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
                                                                                    .getClinicForIds(appointments)
                                                                            , introViewModel
                                                                                    .getPatientForIds(appointments)
                                                                            , appointments));
                                                        } catch (NullPointerException e) {
                                                            Toast.makeText(this, "appointments null"
                                                                    , Toast.LENGTH_SHORT).show();
                                                        }

                                                        try {
                                                            introViewModel.insertFinances(ModelConverter
                                                                    .financesFromSync(syncResponse.getFinances()));
                                                        } catch (NullPointerException e) {
                                                            Toast.makeText(this, "finances null"
                                                                    , Toast.LENGTH_SHORT).show();
                                                        }
                                                        Dentist syncedDentist = new Dentist(authResponse.getUser().getId()
                                                                , authResponse.getUser().getFirst_name()
                                                                , authResponse.getUser().getLast_name()
                                                                , authResponse.getUser().getPhone()
                                                                , authResponse.getUser().getSpeciality()
                                                                , authResponse.getUser().getImage()
                                                                , 1, authResponse.getToken()
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
                                    startActivity(new Intent(this, HomeActivity.class));
                                    finish();
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

                                    // Sync
                                    com.pixium.brandent.db.entities.Appointment[] toSyncAppointments
                                            = introViewModel.getUnsyncedAppointments(dentist.getLastUpdated());
                                    SyncRequest syncRequest = new
                                            SyncRequest(DateTools.stringFromTimestamp(dentist.getLastUpdated(), DateTools.apiTimeFormat)
                                            , ModelConverter.clinicsToSync(introViewModel.getUnsyncedClinics(dentist.getLastUpdated()))
                                            , ModelConverter.patientsToSync(introViewModel.getUnsyncedPatients(dentist.getLastUpdated()))
                                            , ModelConverter.financesToSync(introViewModel.getUnsyncedFinances(dentist.getLastUpdated()))
                                            , ModelConverter.appointmentsToSync(introViewModel.getClinicForUuidStrings(toSyncAppointments), introViewModel.getPatientForUuidStrings(toSyncAppointments), toSyncAppointments)
                                            , new Task[0]);
                                    introViewModel.sync(syncRequest, authResponse.getToken())
                                            .observe(this, syncResponse -> {
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
                                                        Thread.sleep(1000);

                                                        try {
                                                            Appointment[] appointments = syncResponse.getAppointments();
                                                            introViewModel.insertAppointments(ModelConverter
                                                                    .appointmentsFromSync(introViewModel
                                                                                    .getClinicForIds(appointments)
                                                                            , introViewModel
                                                                                    .getPatientForIds(appointments)
                                                                            , appointments));
                                                        } catch (NullPointerException e) {
                                                            Toast.makeText(this, "appointments null"
                                                                    , Toast.LENGTH_SHORT).show();
                                                        }

                                                        try {
                                                            introViewModel.insertFinances(ModelConverter
                                                                    .financesFromSync(syncResponse.getFinances()));
                                                        } catch (NullPointerException e) {
                                                            Toast.makeText(this, "finances null"
                                                                    , Toast.LENGTH_SHORT).show();
                                                        }
                                                        Dentist syncedDentist = new Dentist(authResponse.getUser().getId()
                                                                , authResponse.getUser().getFirst_name()
                                                                , authResponse.getUser().getLast_name()
                                                                , authResponse.getUser().getPhone()
                                                                , authResponse.getUser().getSpeciality()
                                                                , authResponse.getUser().getImage()
                                                                , 1, authResponse.getToken()
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
                                    startActivity(new Intent(this, HomeActivity.class));
                                    finish();
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


}
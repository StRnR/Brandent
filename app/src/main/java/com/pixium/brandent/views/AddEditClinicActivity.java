package com.pixium.brandent.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.brandent.R;
import com.pixium.brandent.db.entities.Clinic;
import com.pixium.brandent.viewmodels.ClinicViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddEditClinicActivity extends AppCompatActivity {
    private ClinicViewModel clinicViewModel;

    public static final String EXTRA_ID =
            "com.pixium.brandent.EXTRA_ID";
    public static final String EXTRA_UUID =
            "com.pixium.brandent.EXTRA_UUID";
    public static final String EXTRA_TITLE =
            "com.pixium.brandent.EXTRA_TITLE";
    public static final String EXTRA_COLOR =
            "com.pixium.brandent.EXTRA_COLOR";
    public static final String EXTRA_ADDRESS =
            "com.pixium.brandent.EXTRA_ADDRESS";

    private String clinicColor = null;
    private String prevName;

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);

        Button backBtn = findViewById(R.id.btn_back_clinic_add);
        Button submitBtn = findViewById(R.id.btn_submit_clinic_add);
        Button redBtn = findViewById(R.id.btn_color_red_clinic_add);
        Button pinkBtn = findViewById(R.id.btn_color_pink_clinic_add);
        Button purpleBtn = findViewById(R.id.btn_color_purple_clinic_add);
        Button blueBtn = findViewById(R.id.btn_color_blue_clinic_add);
        Button cyanBtn = findViewById(R.id.btn_color_cyan_clinic_add);
        Button tealBtn = findViewById(R.id.btn_color_teal_clinic_add);
        Button greenBtn = findViewById(R.id.btn_color_green_clinic_add);
        Button lightGreenBtn = findViewById(R.id.btn_color_light_green_clinic_add);

        EditText nameEt = findViewById(R.id.et_name_clinic_add);
        EditText addressEt = findViewById(R.id.et_address_clinic_add);

        TextView headerTv = findViewById(R.id.tv_header_clinic_add);

        clinicViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(ClinicViewModel.class);

        redBtn.setOnClickListener(v -> {
            clinicColor = getResources().getString(R.color.clinicRed);
            redBtn.setBackground(getDrawable(R.drawable.bg_selected_red_clinic));
            pinkBtn.setBackground(getDrawable(R.drawable.bg_pink_clinic));
            purpleBtn.setBackground(getDrawable(R.drawable.bg_purple_clinic));
            blueBtn.setBackground(getDrawable(R.drawable.bg_blue_clinic));
            cyanBtn.setBackground(getDrawable(R.drawable.bg_cyan_clinic));
            tealBtn.setBackground(getDrawable(R.drawable.bg_teal_clinic));
            greenBtn.setBackground(getDrawable(R.drawable.bg_green_clinic));
            lightGreenBtn.setBackground(getDrawable(R.drawable.bg_light_green_clinic));
        });

        pinkBtn.setOnClickListener(v -> {
            clinicColor = getResources().getString(R.color.clinicPink);
            pinkBtn.setBackground(getDrawable(R.drawable.bg_selected_pink_clinic));
            redBtn.setBackground(getDrawable(R.drawable.bg_red_clinic));
            purpleBtn.setBackground(getDrawable(R.drawable.bg_purple_clinic));
            blueBtn.setBackground(getDrawable(R.drawable.bg_blue_clinic));
            cyanBtn.setBackground(getDrawable(R.drawable.bg_cyan_clinic));
            tealBtn.setBackground(getDrawable(R.drawable.bg_teal_clinic));
            greenBtn.setBackground(getDrawable(R.drawable.bg_green_clinic));
            lightGreenBtn.setBackground(getDrawable(R.drawable.bg_light_green_clinic));
        });

        purpleBtn.setOnClickListener(v -> {
            clinicColor = getResources().getString(R.color.clinicPurple);
            purpleBtn.setBackground(getDrawable(R.drawable.bg_selected_purple_clinic));
            redBtn.setBackground(getDrawable(R.drawable.bg_red_clinic));
            pinkBtn.setBackground(getDrawable(R.drawable.bg_pink_clinic));
            blueBtn.setBackground(getDrawable(R.drawable.bg_blue_clinic));
            cyanBtn.setBackground(getDrawable(R.drawable.bg_cyan_clinic));
            tealBtn.setBackground(getDrawable(R.drawable.bg_teal_clinic));
            greenBtn.setBackground(getDrawable(R.drawable.bg_green_clinic));
            lightGreenBtn.setBackground(getDrawable(R.drawable.bg_light_green_clinic));
        });

        blueBtn.setOnClickListener(v -> {
            clinicColor = getResources().getString(R.color.clinicBlue);
            blueBtn.setBackground(getDrawable(R.drawable.bg_selected_blue_clinic));
            redBtn.setBackground(getDrawable(R.drawable.bg_red_clinic));
            pinkBtn.setBackground(getDrawable(R.drawable.bg_pink_clinic));
            purpleBtn.setBackground(getDrawable(R.drawable.bg_purple_clinic));
            cyanBtn.setBackground(getDrawable(R.drawable.bg_cyan_clinic));
            tealBtn.setBackground(getDrawable(R.drawable.bg_teal_clinic));
            greenBtn.setBackground(getDrawable(R.drawable.bg_green_clinic));
            lightGreenBtn.setBackground(getDrawable(R.drawable.bg_light_green_clinic));
        });

        cyanBtn.setOnClickListener(v -> {
            clinicColor = getResources().getString(R.color.clinicCyan);
            cyanBtn.setBackground(getDrawable(R.drawable.bg_selected_cyan_clinic));
            redBtn.setBackground(getDrawable(R.drawable.bg_red_clinic));
            pinkBtn.setBackground(getDrawable(R.drawable.bg_pink_clinic));
            purpleBtn.setBackground(getDrawable(R.drawable.bg_purple_clinic));
            blueBtn.setBackground(getDrawable(R.drawable.bg_blue_clinic));
            tealBtn.setBackground(getDrawable(R.drawable.bg_teal_clinic));
            greenBtn.setBackground(getDrawable(R.drawable.bg_green_clinic));
            lightGreenBtn.setBackground(getDrawable(R.drawable.bg_light_green_clinic));
        });

        tealBtn.setOnClickListener(v -> {
            clinicColor = getResources().getString(R.color.clinicTeal);
            tealBtn.setBackground(getDrawable(R.drawable.bg_selected_teal_clinic));
            redBtn.setBackground(getDrawable(R.drawable.bg_red_clinic));
            pinkBtn.setBackground(getDrawable(R.drawable.bg_pink_clinic));
            purpleBtn.setBackground(getDrawable(R.drawable.bg_purple_clinic));
            blueBtn.setBackground(getDrawable(R.drawable.bg_blue_clinic));
            cyanBtn.setBackground(getDrawable(R.drawable.bg_cyan_clinic));
            greenBtn.setBackground(getDrawable(R.drawable.bg_green_clinic));
            lightGreenBtn.setBackground(getDrawable(R.drawable.bg_light_green_clinic));
        });

        greenBtn.setOnClickListener(v -> {
            clinicColor = getResources().getString(R.color.clinicGreen);
            greenBtn.setBackground(getDrawable(R.drawable.bg_selected_green_clinic));
            redBtn.setBackground(getDrawable(R.drawable.bg_red_clinic));
            pinkBtn.setBackground(getDrawable(R.drawable.bg_pink_clinic));
            purpleBtn.setBackground(getDrawable(R.drawable.bg_purple_clinic));
            blueBtn.setBackground(getDrawable(R.drawable.bg_blue_clinic));
            cyanBtn.setBackground(getDrawable(R.drawable.bg_cyan_clinic));
            tealBtn.setBackground(getDrawable(R.drawable.bg_teal_clinic));
            lightGreenBtn.setBackground(getDrawable(R.drawable.bg_light_green_clinic));
        });

        lightGreenBtn.setOnClickListener(v -> {
            clinicColor = getResources().getString(R.color.clinicLightGreen);
            lightGreenBtn.setBackground(getDrawable(R.drawable.bg_selected_light_green_clinic));
            redBtn.setBackground(getDrawable(R.drawable.bg_red_clinic));
            pinkBtn.setBackground(getDrawable(R.drawable.bg_pink_clinic));
            purpleBtn.setBackground(getDrawable(R.drawable.bg_purple_clinic));
            blueBtn.setBackground(getDrawable(R.drawable.bg_blue_clinic));
            cyanBtn.setBackground(getDrawable(R.drawable.bg_cyan_clinic));
            tealBtn.setBackground(getDrawable(R.drawable.bg_teal_clinic));
            greenBtn.setBackground(getDrawable(R.drawable.bg_green_clinic));
        });

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            headerTv.setText("ویرایش مطب");
            prevName = intent.getStringExtra(EXTRA_TITLE);
            nameEt.setText(prevName);
            addressEt.setText(intent.getStringExtra(EXTRA_ADDRESS));
            String oldColor = intent.getStringExtra(EXTRA_COLOR);

            switch (oldColor) {
                case "#ffff2927":
                    redBtn.performClick();
                    break;
                case "#fffc2479":
                    pinkBtn.performClick();
                    break;
                case "#ff7033bf":
                    purpleBtn.performClick();
                    break;
                case "#ff0098fd":
                    blueBtn.performClick();
                    break;
                case "#ff00c0da":
                    cyanBtn.performClick();
                    break;
                case "#ff009989":
                    tealBtn.performClick();
                    break;
                case "#ff17b243":
                    greenBtn.performClick();
                    break;
                case "#ff7ec630":
                    lightGreenBtn.performClick();
                    break;
            }
        }

        submitBtn.setOnClickListener(v -> {
            if (clinicColor == null) {
                Toast.makeText(getApplicationContext(), "Please select a color for your clinic", Toast.LENGTH_SHORT).show();
                return;
            } else if (nameEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter a name for your clinic", Toast.LENGTH_SHORT).show();
                return;
            } else {
                try {
                    List<Clinic> duplicates = clinicViewModel.getClinicByTitle(nameEt.getText()
                            .toString());
                    if (duplicates.size() != 0 && !duplicates.get(0).getTitle().equals(prevName)) {
                        Toast.makeText(getApplicationContext()
                                , "Please enter a unique name for your clinic"
                                , Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, nameEt.getText().toString());
            data.putExtra(EXTRA_COLOR, clinicColor);
            data.putExtra(EXTRA_ADDRESS, addressEt.getText().toString());

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
                data.putExtra(EXTRA_UUID, getIntent().getStringExtra(EXTRA_UUID));
            }

            setResult(RESULT_OK, data);
            finish();
        });

        backBtn.setOnClickListener(v -> super.onBackPressed());

    }
}
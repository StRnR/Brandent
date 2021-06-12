package com.brandent.clinitick.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.brandent.clinitick.R;
import com.brandent.clinitick.db.entities.Clinic;
import com.brandent.clinitick.viewmodels.ClinicViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddEditClinicActivity extends AppCompatActivity {
    public static final String EXTRA_CLINIC_ID =
            "com.pixium.brandent.EXTRA_CLINIC_ID";
    public static final String EXTRA_CLINIC_UUID =
            "com.pixium.brandent.EXTRA_CLINIC_UUID";
    public static final String EXTRA_CLINIC_TITLE =
            "com.pixium.brandent.EXTRA_CLINIC_TITLE";
    public static final String EXTRA_CLINIC_COLOR =
            "com.pixium.brandent.EXTRA_CLINIC_COLOR";
    public static final String EXTRA_CLINIC_ADDRESS =
            "com.pixium.brandent.EXTRA_CLINIC_ADDRESS";
    private ClinicViewModel clinicViewModel;
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
            clinicColor = "color_8";
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
            clinicColor = "color_7";
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
            clinicColor = "color_6";
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
            clinicColor = "color_5";
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
            clinicColor = "color_4";
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
            clinicColor = "color_3";
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
            clinicColor = "color_2";
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
            clinicColor = "color_1";
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
        if (intent.hasExtra(EXTRA_CLINIC_ID)) {
            headerTv.setText("ویرایش مطب");
            prevName = intent.getStringExtra(EXTRA_CLINIC_TITLE);
            nameEt.setText(prevName);
            addressEt.setText(intent.getStringExtra(EXTRA_CLINIC_ADDRESS));
            String oldColor = intent.getStringExtra(EXTRA_CLINIC_COLOR);

            switch (oldColor) {
                case "color_8":
                    redBtn.performClick();
                    break;
                case "#color_7":
                    pinkBtn.performClick();
                    break;
                case "#color_6":
                    purpleBtn.performClick();
                    break;
                case "color_5":
                    blueBtn.performClick();
                    break;
                case "color_4":
                    cyanBtn.performClick();
                    break;
                case "color_3":
                    tealBtn.performClick();
                    break;
                case "color_2":
                    greenBtn.performClick();
                    break;
                case "color_1":
                    lightGreenBtn.performClick();
                    break;
            }
        }

        submitBtn.setOnClickListener(v -> {
            if (clinicColor == null) {
                Toast.makeText(getApplicationContext(), "Please select a color for your clinic"
                        , Toast.LENGTH_SHORT).show();
                return;
            } else if (nameEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter a name for your clinic"
                        , Toast.LENGTH_SHORT).show();
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
            data.putExtra(EXTRA_CLINIC_TITLE, nameEt.getText().toString());
            data.putExtra(EXTRA_CLINIC_COLOR, clinicColor);
            data.putExtra(EXTRA_CLINIC_ADDRESS, addressEt.getText().toString());

            int id = getIntent().getIntExtra(EXTRA_CLINIC_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_CLINIC_ID, id);
                data.putExtra(EXTRA_CLINIC_UUID, getIntent().getStringExtra(EXTRA_CLINIC_UUID));
            }

            setResult(RESULT_OK, data);
            finish();
        });

        backBtn.setOnClickListener(v -> super.onBackPressed());

    }
}
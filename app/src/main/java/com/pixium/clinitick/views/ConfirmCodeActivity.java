package com.pixium.clinitick.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.clinitick.R;
import com.pixium.clinitick.UiTools;
import com.pixium.clinitick.viewmodels.ConfirmCodeViewModel;

public class ConfirmCodeActivity extends AppCompatActivity {
    private ConfirmCodeViewModel confirmCodeViewModel;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);

        confirmCodeViewModel = new ViewModelProvider(this
                , ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(ConfirmCodeViewModel.class);
        phone = getIntent().getStringExtra("phone");

        Button resendBtn = findViewById(R.id.btn_submit_confirm_code);
        Button backBtn = findViewById(R.id.btn_confirm_code_back);

        EditText codeFirstEt = findViewById(R.id.et_confirm_code_first);
        EditText codeSecondEt = findViewById(R.id.et_confirm_code_second);
        EditText codeThirdEt = findViewById(R.id.et_confirm_code_third);
        EditText codeFourthEt = findViewById(R.id.et_confirm_code_fourth);

        codeSecondEt.setEnabled(false);
        codeThirdEt.setEnabled(false);
        codeFourthEt.setEnabled(false);

        codeFirstEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    codeSecondEt.setEnabled(true);
                    codeSecondEt.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        codeSecondEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    codeThirdEt.setEnabled(true);
                    codeThirdEt.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        codeThirdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    codeFourthEt.setEnabled(true);
                    codeFourthEt.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        codeFourthEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!codeFirstEt.getText().toString().equals("")
                        && !codeSecondEt.getText().toString().equals("")
                        && !codeThirdEt.getText().toString().equals("")
                        && !codeFourthEt.getText().toString().equals("")) {
                    UiTools.closeKeyboard(ConfirmCodeActivity.this);
                    String code = codeFirstEt.getText().toString() + codeSecondEt.getText().toString()
                            + codeThirdEt.getText().toString() + codeFourthEt.getText().toString();
                    confirmCodeViewModel.init(phone, code);
                    confirmCodeViewModel.postCode().observe(ConfirmCodeActivity.this
                            , messageResponse -> {
                                try {
                                    String message = messageResponse.getMessage();
                                    Toast.makeText(ConfirmCodeActivity.this, message
                                            , Toast.LENGTH_SHORT).show();
                                    if (message.equals("phone verified")) {
                                        Intent intent = new Intent(ConfirmCodeActivity.this
                                                , RegisterActivity.class);
                                        intent.putExtra("phone", phone);
                                        startActivity(intent);
                                    }
                                } catch (NullPointerException e) {
                                    Toast.makeText(ConfirmCodeActivity.this
                                            , "Null reference", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(ConfirmCodeActivity.this
                            , "Please enter a valid code!", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resendBtn.setOnClickListener(v -> confirmCodeViewModel.postPhone(phone));

        backBtn.setOnClickListener(v -> super.onBackPressed());
    }
}
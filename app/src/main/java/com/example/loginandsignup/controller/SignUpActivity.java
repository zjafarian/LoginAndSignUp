package com.example.loginandsignup.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandsignup.R;
import com.example.loginandsignup.model.BankUser;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    public static final String SAVE_USER_NAME_SIGN_UP = "saveUserNameSignUp";
    public static final String SAVE_PASSWORD_SIGN_UP = "savePasswordSignUp";
    public static final String EXTRA_GET_BANK_USER = "get_bank_user";
    public static final String SAVE_BANK_USER = "save_bank_user";
    public static final String CURRENT_INDEX = "current-index";
    private Button mSign;
    private TextInputLayout mUserNameSignUpForm;
    private TextInputEditText mUserNameSignUp;
    private TextInputLayout mPasswordSignUpForm;
    private TextInputEditText mPasswordSignUp;
    private BankUser[] bankUsers = new BankUser[5];
    private int mCurrentIndex = 0;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setId();
        if (savedInstanceState != null) {
            mUserNameSignUp.setText(savedInstanceState.getInt(SAVE_USER_NAME_SIGN_UP));
            mPasswordSignUp.setText(savedInstanceState.getInt(SAVE_PASSWORD_SIGN_UP));
            bankUsers = (BankUser[]) savedInstanceState.getSerializable(SAVE_BANK_USER);
            mCurrentIndex = savedInstanceState.getInt(CURRENT_INDEX);
        }
        username = getIntent().getStringExtra(LoginActivity.EXTERA_USER_NAME);
        mUserNameSignUp.setText(username);
        password = getIntent().getStringExtra(LoginActivity.EXTERA_PASSWORD);
        mPasswordSignUp.setText(password);
        bankUsers = (BankUser[]) getIntent().getSerializableExtra(LoginActivity.EXTRA_PUT_BANK_USERS);

        setListener();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_USER_NAME_SIGN_UP, R.string.userTextSignUp);
        outState.putInt(SAVE_PASSWORD_SIGN_UP, R.string.passwordSignUp);
        outState.putSerializable(SAVE_BANK_USER, bankUsers);
        outState.putInt(CURRENT_INDEX, mCurrentIndex);
    }

    private void setId() {
        mPasswordSignUpForm = findViewById(R.id.password_signUp_form);
        mUserNameSignUpForm = findViewById(R.id.username_signUp_form);
        mUserNameSignUp = findViewById(R.id.username_signUp);
        mPasswordSignUp = findViewById(R.id.password_signUp);
        mSign = findViewById(R.id.btn_signup_sign);
    }

    private void setListener() {
        mSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUserNameSignUp.getText().toString();
                password = mPasswordSignUp.getText().toString();
                if (username.matches("") || password.matches("")) {
                    Toast toast = Toast.makeText(SignUpActivity.this,
                            R.string.message_signup, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                } else {
                    bankUsers[mCurrentIndex] = new BankUser(username, password);
                    username ="";
                    password="";
                    mUserNameSignUp.setText(username);
                    mPasswordSignUp.setText(password);
                    mUserNameSignUp.setSelectAllOnFocus(true);
                    mUserNameSignUp.selectAll();
                    mCurrentIndex++;
                }

                if (mCurrentIndex == bankUsers.length) {
                    sendUserNameAndPassword();
                    finish();
                }
            }
        });
    }

    private void sendUserNameAndPassword() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_GET_BANK_USER, bankUsers);
        setResult(RESULT_OK, intent);


    }


}
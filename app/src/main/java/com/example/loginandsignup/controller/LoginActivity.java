package com.example.loginandsignup.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandsignup.R;
import com.example.loginandsignup.model.BankUser;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTERA_USER_NAME = "com.example.loginandsignup.UserName";
    public static final String EXTERA_PASSWORD = "com.example.loginandsignup.Password";
    public static final int REQUEST_CODE_SIGNUP = 0;
    public static final String SAVE_USERNAME_TEXT = "saveUsernameText";
    public static final String SAVE_TEXT_PASSWORD = "saveTextPassword";
    public static final String SAVE_BANK_USERS = "save_bank_users";
    public static final String EXTRA_PUT_BANK_USERS = "com.example.loginandsignup.put_bank_users";
    public static final String SAVE_CURRENT_INDEX = "save_current_index";
    private Button mButton_logIn;
    private Button mButton_signUp;
    private TextInputLayout mUsernameLoginForm;
    private TextInputLayout mPasswordLoginForm;
    private TextInputEditText mUsernameLogin;
    private TextInputEditText mPasswordLogin;
    private BankUser[] bankUsers = new BankUser[5];
    private int mCurrentIndex = 0;
    private ViewGroup mLayoutMain;
    private String username;
    private String password;
    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setId();
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(SAVE_CURRENT_INDEX);
            bankUsers = (BankUser[]) savedInstanceState.getSerializable(SAVE_BANK_USERS);
            mUsernameLogin.setText(bankUsers[0].getUserName());
            mPasswordLogin.setText(bankUsers[0].getPassword());
        }
        setListener();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_USERNAME_TEXT, username);
        outState.putString(SAVE_TEXT_PASSWORD, password);
        outState.putInt(SAVE_CURRENT_INDEX, mCurrentIndex);
        outState.putSerializable(SAVE_BANK_USERS, bankUsers);
    }

    private void setId() {
        mUsernameLoginForm = findViewById(R.id.username_login_form);
        mPasswordLoginForm = findViewById(R.id.password_login_form);
        mUsernameLogin = findViewById(R.id.username_login);
        mPasswordLogin = findViewById(R.id.password_login);
        mButton_logIn = findViewById(R.id.btn_login);
        mButton_signUp = findViewById(R.id.btn_signup);
        mLayoutMain = findViewById(R.id.layout_main);
    }

    private void setListener() {
        mButton_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mButton_signUp.isClickable()) {
                    Toast toast = Toast.makeText(LoginActivity.this, R.string.message_not_sign_up,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                } else {
                    String user = mUsernameLogin.getText().toString();
                    String pass = mPasswordLogin.getText().toString();
                    if (user.matches("") || pass.matches("")) {
                        Toast toast = Toast.makeText(LoginActivity.this, R.string.message_signup,
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                    } else {
                        for (int i = 0; i < bankUsers.length; i++) {
                            bankUsers[i]=new BankUser();
                            if (user.equals(bankUsers[i].getUserName()) && pass.equals(bankUsers[i].getPassword())) {
                                check = true;
                            }
                        }
                        if (check) {
                            Snackbar snackbar = Snackbar.make(mLayoutMain, R.string.successful_message, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            Snackbar snackbar = Snackbar.make(mLayoutMain, R.string.non_successful_message,
                                    Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                    mCurrentIndex++;
                }
                check = false;
                if (mCurrentIndex == bankUsers.length) {
                    mUsernameLogin.setActivated(false);
                    mPasswordLogin.setActivated(false);
                }
            }
        });

        mButton_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsernameLogin.getText().toString();
                password = mPasswordLogin.getText().toString();
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.putExtra(EXTERA_USER_NAME, username);
                intent.putExtra(EXTERA_PASSWORD, password);
                intent.putExtra(EXTRA_PUT_BANK_USERS, bankUsers);
                startActivityForResult(intent, REQUEST_CODE_SIGNUP);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_SIGNUP) {
            bankUsers = (BankUser[]) data.getSerializableExtra(SignUpActivity.EXTRA_GET_BANK_USER);


        }
    }

}
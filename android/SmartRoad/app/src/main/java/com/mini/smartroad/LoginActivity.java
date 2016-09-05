package com.mini.smartroad;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mini.smartroad.client.user.UserClientAgent;

import java.util.UUID;


public class LoginActivity extends Activity {

    private TextInputEditText firstNameEditText;
    private TextInputEditText lastNameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText repeatedPasswordEditText;
    private TextInputLayout firstNameLayout;
    private TextInputLayout lastNameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout repeatedPasswordLayout;
    private AppCompatButton registerBtn;
    private TextView linkLoginTextView;

    public void register(View view) {
        Toast.makeText(getApplicationContext(), "AAA", Toast.LENGTH_LONG).show();
        Resources resources = getResources();
        TextInputLayout textInputLayout;
        if (emailEditText.getText().toString().isEmpty()) {
            emailLayout.setError(resources.getString(R.string.error_empty_email));
        } else if (passwordEditText.getText().toString().isEmpty()) {
            emailLayout.setError(null);
            repeatedPasswordLayout.setError(null);
            passwordLayout.setError(resources.getString(R.string.error_empty_password));
        } else if (repeatedPasswordEditText.getText().toString().isEmpty()) {
            emailLayout.setError(null);
            passwordLayout.setError(null);
            repeatedPasswordLayout.setError(resources.getString(R.string.error_empty_repeat_password));
        } else if (passwordEditText.getText().toString().length() < 5) {
            emailLayout.setError(null);
            repeatedPasswordLayout.setError(null);
            passwordLayout.setError(resources.getString(R.string.error_password_to_short));
        } else if (passwordEditText.getText().toString().length() > 30) {
            emailLayout.setError(null);
            repeatedPasswordLayout.setError(null);
            passwordLayout.setError(resources.getString(R.string.error_password_to_long));
        } else if (!passwordEditText.getText().toString().equals(repeatedPasswordEditText.getText().toString())) {
            emailLayout.setError(null);
            passwordLayout.setError(null);
            repeatedPasswordLayout.setError(resources.getString(R.string.error_repeat_password_mismatch));
        } else {
            emailLayout.setError(null);
            passwordLayout.setError(null);
            repeatedPasswordLayout.setError(null);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firstNameEditText = (TextInputEditText) findViewById(R.id.first_name_edit_text);
        lastNameEditText = (TextInputEditText) findViewById(R.id.last_name_edit_text);
        emailEditText = (TextInputEditText) findViewById(R.id.email_edit_text);
        passwordEditText = (TextInputEditText) findViewById(R.id.password_edit_text);
        repeatedPasswordEditText = (TextInputEditText) findViewById(R.id.repeated_password_edit_text);
        registerBtn = (AppCompatButton) findViewById(R.id.btn_register);
        linkLoginTextView = (TextView) findViewById(R.id.link_login);
        firstNameLayout = (TextInputLayout) findViewById(R.id.first_name_layout);
        lastNameLayout = (TextInputLayout) findViewById(R.id.last_name_layout);
        emailLayout = (TextInputLayout) findViewById(R.id.email_layout);
        passwordLayout = (TextInputLayout) findViewById(R.id.password_layout);
        repeatedPasswordLayout = (TextInputLayout) findViewById(R.id.repeat_password_layout);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });
//        ConnectionUtils.prepareConnection(getApplicationContext());
    }

    public void startAgents() {
        ConnectionUtils.startAgent(UserClientAgent.class.getName() + UUID.randomUUID(),
                UserClientAgent.class, getApplicationContext(), new Object[]{"test@gmail.com", "password"});
    }
}

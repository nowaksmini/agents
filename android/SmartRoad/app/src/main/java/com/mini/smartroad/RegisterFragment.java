package com.mini.smartroad;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mini.smartroad.client.user.UserClientAgent;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterFragment extends Fragment {

    @BindView(R.id.first_name_edit_text)
    TextInputEditText firstNameEditText;
    @BindView(R.id.last_name_edit_text)
    TextInputEditText lastNameEditText;
    @BindView(R.id.email_edit_text)
    TextInputEditText emailEditText;
    @BindView(R.id.password_edit_text)
    TextInputEditText passwordEditText;
    @BindView(R.id.repeated_password_edit_text)
    TextInputEditText repeatedPasswordEditText;
    @BindView(R.id.first_name_layout)
    TextInputLayout firstNameLayout;
    @BindView(R.id.last_name_layout)
    TextInputLayout lastNameLayout;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.repeat_password_layout)
    TextInputLayout repeatedPasswordLayout;
    @BindView(R.id.btn_register)
    AppCompatButton registerBtn;
    @BindView(R.id.link_login)
    TextView linkLoginTextView;
    @BindView(R.id.remember_credentials)
    CheckBox rememberCredentials;

    public void register(View view) {
        Toast.makeText(getContext(), "REGISTER", Toast.LENGTH_LONG).show();
        Resources resources = getResources();
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
            if (rememberCredentials.isChecked()) {
                ((LoginActivity) getActivity()).saveCredentials(emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString());
            } else {
                ((LoginActivity) getActivity()).clearCredentials();
            }

            startAgentRegister(emailEditText.getText().toString().trim(),
                    firstNameEditText.getText().toString().trim(),
                    lastNameEditText.getText().toString().trim(),
                    passwordEditText.getText().toString());
        }
    }

    private void startAgentRegister(String email, String firstName, String lastName, String password) {
        ConnectionUtils.startAgent(UserClientAgent.class.getName() + UUID.randomUUID(),
                UserClientAgent.class, getContext().getApplicationContext(),
                new Object[]{email, firstName, lastName, password});
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        rememberCredentials.setChecked(true);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });
        return view;
    }

}

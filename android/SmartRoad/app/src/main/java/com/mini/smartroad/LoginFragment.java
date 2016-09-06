package com.mini.smartroad;

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

import com.mini.smartroad.client.user.UserClientAgent;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment {

    @BindView(R.id.email_edit_text)
    TextInputEditText emailEditText;
    @BindView(R.id.password_edit_text)
    TextInputEditText passwordEditText;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.btn_login)
    AppCompatButton loginBtn;
    @BindView(R.id.link_register)
    TextView linkRegisterTextView;
    @BindView(R.id.remember_credentials)
    CheckBox rememberCredentials;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        String[] credentials = ((LoginActivity) getActivity()).getCredentials();
        emailEditText.setText(credentials[0]);
        passwordEditText.setText(credentials[1]);
        rememberCredentials.setChecked(true);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
        return view;
    }

    private void login(View view) {
        Resources resources = getResources();
        if (emailEditText.getText().toString().isEmpty()) {
            emailLayout.setError(resources.getString(R.string.error_empty_email));
        } else if (passwordEditText.getText().toString().isEmpty()) {
            emailLayout.setError(null);
            passwordLayout.setError(resources.getString(R.string.error_empty_password));
        } else {
            emailLayout.setError(null);
            passwordLayout.setError(null);
            if (rememberCredentials.isChecked()) {
                ((LoginActivity) getActivity()).saveCredentials(emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString());
            } else {
                ((LoginActivity) getActivity()).clearCredentials();
            }
            startAgentLogin(emailEditText.getText().toString().trim(), passwordEditText.getText().toString());
        }
    }

    private void startAgentLogin(String email, String password) {
        ConnectionUtils.startAgent(UserClientAgent.class.getName() + UUID.randomUUID(),
                UserClientAgent.class, getContext().getApplicationContext(), new Object[]{email, password});
    }

}

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
import android.widget.TextView;
import android.widget.Toast;

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

    public void login(View view) {
        Toast.makeText(getContext(), "LOGIN", Toast.LENGTH_LONG).show();
        Resources resources = getResources();
        if (emailEditText.getText().toString().isEmpty()) {
            emailLayout.setError(resources.getString(R.string.error_empty_email));
        } else if (passwordEditText.getText().toString().isEmpty()) {
            emailLayout.setError(null);
            passwordLayout.setError(resources.getString(R.string.error_empty_password));
        } else {
            emailLayout.setError(null);
            passwordLayout.setError(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
        return view;
    }

}

package com.mini.smartroad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TripFragment extends Fragment {

    @BindView(R.id.btn_start)
    AppCompatButton appCompatButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_trip, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @OnClick(R.id.btn_start)
    public void click(View view) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }
}

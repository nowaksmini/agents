package com.mini.smartroad;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.out.StationOutDto;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActionDialogFragment extends DialogFragment {

    public static final String STATION = "station";
    private static final String TAG = ActionDialogFragment.class.getName();

    @BindView(R.id.like_btn)
    AppCompatImageButton likeBtn;
    @BindView(R.id.dislike_btn)
    AppCompatImageButton dislikeBtn;
    @BindView(R.id.accept_btn)
    AppCompatImageButton acceptBtn;
    @BindView(R.id.reject_btn)
    AppCompatImageButton rejectBtn;

    @BindView(R.id.station_name)
    TextView stationNameTxt;
    @BindView(R.id.station_address1)
    TextView stationAddress1Txt;
    @BindView(R.id.station_address2)
    TextView stationAddress2Txt;
    @BindView(R.id.station_address3)
    TextView stationAddress3Txt;
    @BindView(R.id.station_contact1)
    TextView stationContact1Txt;
    @BindView(R.id.station_contact2)
    TextView stationContact2Txt;
    @BindView(R.id.station_logo)
    ImageView stationLogo;

    @BindView(R.id.like_description)
    TextView likeDescription;
    @BindView(R.id.relike_description)
    TextView relikeDescription;

    @BindView(R.id.address_label)
    TextView addressLabel;
    @BindView(R.id.contact_label)
    TextView contactLabel;
    @BindView(R.id.likes)
    LinearLayout likesLayout;
    @BindView(R.id.confirms)
    LinearLayout confirmsLayout;
    @BindView(R.id.like_label)
    TextView likedLabel;
    @BindView(R.id.confirm_label)
    TextView confirmedLabel;

    @BindView(R.id.like_panel)
    LinearLayout likePanel;
    @BindView(R.id.accept_panel)
    LinearLayout acceptPanel;
    @BindView(R.id.dislike_panel)
    LinearLayout dislikePanel;
    @BindView(R.id.reject_panel)
    LinearLayout rejectPanel;

    private String stationToken;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_action, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        StationOutDto stationOutDto = (StationOutDto) getArguments().get(STATION);
        if (stationOutDto != null) {
            stationToken = stationOutDto.getToken();
            String title = stationOutDto.getName();
            if (!TextUtils.isEmpty(stationOutDto.getFullName())) {
                title += " - " + stationOutDto.getFullName();
            }
            stationNameTxt.setText(title);
            buildAddress(stationOutDto);
            buildContact(stationOutDto);
            buildLogo(stationOutDto);
            buildStatistics(stationOutDto);
            buildButtons(stationOutDto);
        }
        return builder.create();
    }

    private void buildAddress(StationOutDto stationOutDto) {
        AddressDto addressDto = stationOutDto.getAddressDto();
        if (addressDto == null) {
            stationAddress1Txt.setVisibility(View.GONE);
            stationAddress2Txt.setVisibility(View.GONE);
            stationAddress3Txt.setVisibility(View.GONE);
        } else {
            String address1 = "";
            if (!TextUtils.isEmpty(addressDto.getStreet())) {
                address1 = getString(R.string.street) + " " + addressDto.getStreet();
                if (!TextUtils.isEmpty(addressDto.getNumber())) {
                    address1 += " " + addressDto.getNumber();
                }
                if (!TextUtils.isEmpty(addressDto.getExtraNumber())) {
                    address1 += " " + addressDto.getExtraNumber();
                }
            }
            if (TextUtils.isEmpty(address1)) {
                stationAddress1Txt.setVisibility(View.GONE);
            } else {
                stationAddress1Txt.setText(address1);
            }
            String address2 = "";
            if (!TextUtils.isEmpty(addressDto.getPostalCode())) {
                address2 = addressDto.getPostalCode();
            }
            if (!TextUtils.isEmpty(addressDto.getCity())) {
                address2 += " " + addressDto.getCity();
            }
            if (TextUtils.isEmpty(address2)) {
                stationAddress2Txt.setVisibility(View.GONE);
            } else {
                stationAddress2Txt.setText(address2);
            }
            String address3 = "";
            if (!TextUtils.isEmpty(addressDto.getDistinct())) {
                address3 = getString(R.string.distinct) + " " + addressDto.getDistinct();
            }
            if (!TextUtils.isEmpty(addressDto.getCountry())) {
                address3 += " " + addressDto.getCountry();
            }
            if (TextUtils.isEmpty(address3)) {
                stationAddress3Txt.setVisibility(View.GONE);
            } else {
                stationAddress3Txt.setText(address3);
            }
        }
        if (stationAddress1Txt.getVisibility() == View.GONE
                && stationAddress2Txt.getVisibility() == View.GONE
                && stationAddress3Txt.getVisibility() == View.GONE) {
            addressLabel.setVisibility(View.GONE);
        }
    }

    private void buildContact(StationOutDto stationOutDto) {
        String contact1 = "";
        if (!TextUtils.isEmpty(stationOutDto.getPhone())) {
            contact1 = getString(R.string.telephone) + " " + stationOutDto.getPhone();
        }
        if (TextUtils.isEmpty(contact1)) {
            stationContact1Txt.setVisibility(View.GONE);
        } else {
            stationContact1Txt.setText(contact1);
        }
        String contact2 = "";
        if (!TextUtils.isEmpty(stationOutDto.getEmail())) {
            contact2 = getString(R.string.email) + " " + stationOutDto.getEmail();
        }
        if (TextUtils.isEmpty(contact2)) {
            stationContact2Txt.setVisibility(View.GONE);
        } else {
            stationContact2Txt.setText(contact2);
        }
        if (stationContact1Txt.getVisibility() == View.GONE
                && stationContact2Txt.getVisibility() == View.GONE) {
            contactLabel.setVisibility(View.GONE);
        }
    }

    private void buildStatistics(StationOutDto stationOutDto) {
        if (stationOutDto.getLikes() != null && stationOutDto.getLikes() > 0) {
            for (int i = 0; i < stationOutDto.getLikes(); i++) {
                ImageView imageView = new ImageView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, LinearLayout.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setImageResource(R.mipmap.thumb_up);
                likesLayout.addView(imageView);
            }
        } else {
            likedLabel.setVisibility(View.GONE);
            likesLayout.setVisibility(View.GONE);
        }
        if (stationOutDto.getConfirms() != null && stationOutDto.getConfirms() > 0) {
            for (int i = 0; i < stationOutDto.getConfirms(); i++) {
                ImageView imageView = new ImageView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, LinearLayout.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setImageResource(R.mipmap.accept);
                confirmsLayout.addView(imageView);
            }
        } else {
            confirmedLabel.setVisibility(View.GONE);
            confirmsLayout.setVisibility(View.GONE);
        }
    }

    private void buildLogo(StationOutDto stationOutDto) {
        if (TextUtils.isEmpty(stationOutDto.getLogo())) {
            stationLogo.setVisibility(View.GONE);
        } else {
            Picasso.with(getContext()).load(stationOutDto.getLogo()).fit().centerInside().into(stationLogo);
        }
    }

    private void buildButtons(StationOutDto stationOutDto) {
        ActionType actionType = stationOutDto.getActionType();
        if (actionType == null) {
            rejectPanel.setVisibility(View.GONE);
            dislikePanel.setVisibility(View.GONE);
        } else if (actionType == ActionType.LIKE) {
            rejectPanel.setVisibility(View.GONE);
            likeDescription.setVisibility(View.GONE);
            relikeDescription.setVisibility(View.VISIBLE);
        } else if (actionType == ActionType.CONFIRM) {
            dislikePanel.setVisibility(View.GONE);
            likePanel.setVisibility(View.GONE);
            acceptPanel.setVisibility(View.GONE);
        } else {
            likePanel.setVisibility(View.VISIBLE);
            acceptPanel.setVisibility(View.VISIBLE);
        }
        addOnClickListeners();
    }

    private void addOnClickListeners() {
        final SearchActivity activity = (SearchActivity) getActivity();
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startAgentAction(stationToken, ActionType.LIKE, Boolean.TRUE,
                        relikeDescription.getVisibility() == View.VISIBLE);
                dismiss();
            }
        });
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startAgentAction(stationToken, ActionType.CONFIRM, Boolean.FALSE, Boolean.FALSE);
                dismiss();
            }
        });
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startAgentAction(stationToken, ActionType.CONFIRM, Boolean.TRUE, Boolean.FALSE);
                dismiss();
            }
        });
        dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startAgentAction(stationToken, ActionType.LIKE, Boolean.FALSE, Boolean.FALSE);
                dismiss();
            }
        });
    }
}

package com.mobidal.pharmacynamoune.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobidal.pharmacynamoune.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.tv_welcome) TextView mWelcomeTextView;
    @BindView(R.id.tv_welcome_description) TextView mWelcomeDescriptionTextView;
    @BindView(R.id.b_connect) Button mConnectButton;

    @BindView(R.id.ll_my_commands) View mMyCommandsView;
    @BindView(R.id.ll_saved_products) View mSavedProductsView;
    @BindView(R.id.ll_recently_viewed) View mRecentlyViewedView;
    @BindView(R.id.ll_recently_searched) View mRecentSearchedView;

    @BindView(R.id.ll_details) View mDetailsView;
    @BindView(R.id.ll_address_book) View mAddressBookView;
    @BindView(R.id.ll_change_password) View mChangePasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Bind views
        ButterKnife.bind(this);

        // Setup profile
        setupProfile();
    }

    private void setupProfile() {
        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mMyCommandsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mSavedProductsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mRecentlyViewedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mRecentSearchedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mDetailsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mAddressBookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mChangePasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
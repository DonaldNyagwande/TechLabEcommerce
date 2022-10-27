package com.mobidal.pharmacynamoune;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mobidal.pharmacynamoune.adapter.MainFragmentAdapter;
import com.mobidal.pharmacynamoune.fragment.CategoryFragment;
import com.mobidal.pharmacynamoune.fragment.HomeFragment;
import com.mobidal.pharmacynamoune.profile.ProfileActivity;
import com.mobidal.pharmacynamoune.profile.ShoppingBasketActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tb_main) Toolbar mToolbar;
    @BindView(R.id.dl_main) DrawerLayout mDrawerLayout;
    @BindView(R.id.nv_main) NavigationView mNavigationView;

    @BindView(R.id.vp_fragment) ViewPager2 mFragmentViewPager;
    @BindView(R.id.tl_main) TabLayout mFragmentTabLayout;

    private View mProfileView;
    private ImageView mPictureImageView;
    private TextView mNameTextView;
    private TextView mScoreTextView;
    private TextView mPhoneTextView;
    private TextView mEmailTextView;
    private TextView mLocationTextView;
    private Button mConnectButton;

    MainFragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Views
        ButterKnife.bind(this);

        // setup the {@link Toolbar}
        setSupportActionBar(mToolbar);

        // Setup Navigation Drawer
        setupNavigationDrawer();

        // Setup {@link ViewPager}
        setupFragmentViewPager();
    }

    private void setupNavigationDrawer() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Setup Navigation Drawer Click Listener
        mNavigationView.setNavigationItemSelectedListener(this);

        // Bind views
        View headerView = mNavigationView.getHeaderView(0);

        mProfileView = (View) headerView.findViewById(R.id.g_profile);
        mPictureImageView = (ImageView) headerView.findViewById(R.id.iv_picture);
        mNameTextView = (TextView) headerView.findViewById(R.id.tv_name);
        mScoreTextView = (TextView) headerView.findViewById(R.id.tv_score);
        mEmailTextView = (TextView) headerView.findViewById(R.id.tv_email);
        mPhoneTextView = (TextView) headerView.findViewById(R.id.tv_phone);
        mLocationTextView = (TextView) headerView.findViewById(R.id.tv_location);
        mConnectButton = (Button) headerView.findViewById(R.id.b_connect);
    }

    private void setupFragmentViewPager() {
        // Create {@link MainFragmentAdapter} for {@link Fragment} {@link ViewPager2}
        mFragmentAdapter = new MainFragmentAdapter(this);

        // Add {@link Fragment}s
        mFragmentAdapter.addFragment(new HomeFragment(this), getString(R.string.tab_home), R.drawable.ic_home);
        mFragmentAdapter.addFragment(new CategoryFragment(this), getString(R.string.tab_category), R.drawable.ic_category);

        // Setup {@link Fragment} {@link ViewPager} with {@link Adapter}
        mFragmentViewPager.setAdapter(mFragmentAdapter);

        // Disable swiping
        mFragmentViewPager.setUserInputEnabled(false);

        // Setup {@link TabLayout} with {@link ViewPager}
        new TabLayoutMediator(mFragmentTabLayout, mFragmentViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon(mFragmentAdapter.getIconResourceId(position));
                tab.setText(mFragmentAdapter.getName(position));
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_shopping_basket:
                shoppingBasketAction();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shoppingBasketAction() {
        Intent intent = new Intent(this, ShoppingBasketActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.nav_profile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.nav_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
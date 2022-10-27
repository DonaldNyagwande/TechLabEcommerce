package com.mobidal.pharmacynamoune;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mobidal.pharmacynamoune.adapter.PictureAdapter;
import com.mobidal.pharmacynamoune.model.Picture;
import com.mobidal.pharmacynamoune.model.Product;
import com.mobidal.pharmacynamoune.profile.ShoppingBasketActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements PictureAdapter.OnPictureClickListener {

    public static final String EXTRA_PRODUCT_ID = "extra_product_id";

    @BindView(R.id.tb_main) Toolbar mToolbar;
    @BindView(R.id.l_main) View mMainView;
    @BindView(R.id.sfl) ShimmerFrameLayout mShimmerFrameLayout;

    @BindView(R.id.vp2_picture) ViewPager2 mPictureViewPager2;
    @BindView(R.id.tl_picture_indicator) TabLayout mPictureIndicatorTabLayout;

    @BindView(R.id.tv_mark) TextView mMarkTextView;
    @BindView(R.id.tv_name) TextView mNameTextView;
    @BindView(R.id.tv_price) TextView mPriceTextView;
    @BindView(R.id.tv_view_number) TextView mViewNumberTextView;
    @BindView(R.id.iv_save) ImageView mSaveImageView;
    @BindView(R.id.tv_description) TextView mDescriptionTextView;

    @BindView(R.id.b_purchase) Button mPurchaseButton;

    Product mProduct;
    PictureAdapter mPictureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Bind HomeFragment views
        ButterKnife.bind(this);

        // setup the {@link Toolbar}
        setupToolbar();

        // setup {@link Product}
        setupProduct();
        // Load {@link Article}
        loadProduct(getIntent());
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
            case android.R.id.home:
                finish();
                return true;
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

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Product Details");
    }

    private void setupProduct() {
        // Start Shimmer
        startShimmer();

        // Setup {@link Picture} horizontal {@link RecyclerView}
        setupPictureView();

        // Setup purchase {@link Product}
        setupPurchaseProduct();

        // Setup save {@link Product}
        setupSaveProduct();
    }

    private void startShimmer() {
        mMainView.setVisibility(View.GONE);
        mShimmerFrameLayout.setVisibility(View.VISIBLE);
        mShimmerFrameLayout.startShimmer();
    }

    private void setupPictureView() {
        // Create {@link LinearLayoutManager} for the {@link Picture} {@link RecyclerView}
        LinearLayoutManager topArticleLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);

        // Create {@link PictureAdapter}
        mPictureAdapter =
                new PictureAdapter(this, null, this);

        // Setup {@link Picture} {@link RecyclerView} {@link Adapter}
        mPictureViewPager2.setAdapter(mPictureAdapter);

        // Setup {@link TabLayout} with {@link ViewPager2}
        new TabLayoutMediator(mPictureIndicatorTabLayout, mPictureViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // This method do nothing
            }
        }).attach();

        ViewPager2.PageTransformer transformer = new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setAlpha(0.25f + r);
                page.setScaleY(0.75f + r * 0.25f);
            }
        };

        mPictureViewPager2.setPageTransformer(transformer);
    }

    private void setupPurchaseProduct() {
        // TODO purchase product
        mPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPurchaseButton.setEnabled(false);
                String stateMessage = getString(R.string.your_purchase_has_been_successfully_completed);

                Toast.makeText(ProductActivity.this, stateMessage,
                        Toast.LENGTH_SHORT).show();

                mPurchaseButton.setEnabled(true);
            }
        });
    }

    private void setupSaveProduct() {
        // TODO save/unsave product
        mSaveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveImageView.setEnabled(false);
                String stateMessage = "";

                if (mProduct.getPivot().isSaved()) {
                    // Unsave product
                    mSaveImageView.setImageResource(R.drawable.ic_favorite_border);
                    stateMessage = getString(R.string.product_saved);
                } else {
                    // Save product
                    mSaveImageView.setImageResource(R.drawable.ic_favorite);
                    stateMessage = getString(R.string.product_unsaved);
                }

                Toast.makeText(ProductActivity.this, stateMessage,
                        Toast.LENGTH_SHORT).show();

                mSaveImageView.setEnabled(true);
            }
        });
    }

    private void loadProduct(Intent intent) {
        // TODO load product
        if (intent != null && intent.hasExtra(EXTRA_PRODUCT_ID)) {
            int productId = intent.getIntExtra(EXTRA_PRODUCT_ID, 0);

            List<Picture> pictureList = new ArrayList<>();

            Picture picture = new Picture(1,
                    "https://www.pharma-medicaments.com/wp-content/uploads/2022/01/3304746.jpg");

            pictureList.add(picture);
            pictureList.add(picture);
            pictureList.add(picture);
            pictureList.add(picture);
            pictureList.add(picture);

            Product.Pivot pivot = new Product.Pivot(false, 1);

            mProduct = new Product(1, "Doliprane", "Doliprane 1000mg",
                    "DOLIPRANE est un antalgique (calme la douleur) et un antipyrétique (fait baisser la fièvre).\n" +
                            "\n" +
                            "La substance active de ce médicament est le paracétamol.\n" +
                            "\n" +
                            "Il est utilisé pour traiter la douleur et/ou la fièvre, par exemple en cas de maux de tête, d’état grippal, de douleurs dentaires, de courbatures, de règles douloureuses.", 300,
                    "https://www.pharma-medicaments.com/wp-content/uploads/2022/01/3304746.jpg",
                    pictureList, 120, pivot);

            if (mProduct.getPivot().isSaved()) {
                mSaveImageView.setImageResource(R.drawable.ic_favorite);
            } else {
                mSaveImageView.setImageResource(R.drawable.ic_favorite_border);
            }

            mPictureAdapter.setList(mProduct.getPictureList());
            mMarkTextView.setText(mProduct.getMark());
            mNameTextView.setText(mProduct.getName());
            mPriceTextView.setText(getString(R.string.price, mProduct.getPrice()));
            mViewNumberTextView.setText(getString(R.string.views_number, mProduct.getViewNumber()));
            mDescriptionTextView.setText(mProduct.getDescription());

            mShimmerFrameLayout.stopShimmer();
            mShimmerFrameLayout.setVisibility(View.GONE);
            mMainView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPictureClicked(Picture picture) {

    }
}
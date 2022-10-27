package com.mobidal.pharmacynamoune.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mobidal.pharmacynamoune.ProductActivity;
import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.adapter.ProductAdapter;
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration;
import com.mobidal.pharmacynamoune.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingBasketActivity extends AppCompatActivity implements ProductAdapter.OnProduct4ClickListener {

    @BindView(R.id.tb_main) Toolbar mToolbar;
    @BindView(R.id.v_main) View mMainView;

    @BindView(R.id.rv_product) RecyclerView mProductRecyclerView;
    @BindView(R.id.l_command) View mCommandView;
    @BindView(R.id.b_command) Button mCommandButton;

    ProductAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);

        // Bind Views
        ButterKnife.bind(this);

        // setup the {@link Toolbar}
        setupToolbar();

        // Setup command {@ Button}
        setupCommand();

        // setup {@Product} list
        setupProductList();
        // Load {@Product} list
        loadProductList();
    }

    private void setupCommand() {
        mCommandView.setVisibility(View.GONE);
    }

    private void setupProductList() {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 1);
        // Create {@link RecyclerView.ItemDecoration}
        int spacingInPixel =
                getResources().getDimensionPixelSize(R.dimen.grid_product_spacing);
        GridSpacingItemDecoration gridSpacingItemDecoration =
                new GridSpacingItemDecoration(1, spacingInPixel, false);

        mProductAdapter =
                new ProductAdapter(this, null, ProductAdapter.TYPE_FOUR);

        mProductAdapter.setOnProduct4ClickListener(this);
        mProductAdapter.setShimmerItemCount(5);

        mProductRecyclerView.setLayoutManager(gridLayoutManager);
        mProductRecyclerView.setAdapter(mProductAdapter);
        mProductRecyclerView.addItemDecoration(gridSpacingItemDecoration);
    }

    private void loadProductList() {
        // TODO load product list
        Product.Pivot pivot = new Product.Pivot(false, 1);
        Product.Pivot pivot2 = new Product.Pivot(false, 2);

        Product product =
                new Product(1, "Doliprane", "Doliprane 1000mg",
                        "Douleurs et fievre", 300,
                        "https://www.pharma-medicaments.com/wp-content/uploads/2022/01/3304746.jpg",
                        null, 120, pivot);

        Product product2 =
                new Product(1, "Doliprane", "Doliprane 1000mg",
                        "Douleurs et fievre", 300,
                        "https://www.pharma-medicaments.com/wp-content/uploads/2022/01/3304746.jpg",
                        null, 120, pivot2);

        List<Product> productList = new ArrayList<>();

        productList.add(product);
        productList.add(product2);

        mProductRecyclerView.setAdapter(null);

        mProductAdapter.setLoading(false);
        mProductAdapter.setAll(productList);

        mProductRecyclerView.setAdapter(mProductAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Shopping Basket");
    }

    @Override
    public void onProductClicked(Product product) {
        Intent intent = new Intent(this, ProductActivity.class);

        intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, product.getId());

        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(Product product, int position) {
        mProductAdapter.remove(position);
        mProductAdapter.notifyItemChanged(position);
    }

    @Override
    public void onDecrementClicked(Product product, int position) {
        product.getPivot().decrementQuantity();
        mProductAdapter.notifyItemChanged(position);
    }

    @Override
    public void onIncrementClicked(Product product, int position) {
        product.getPivot().incrementQuantity();
        mProductAdapter.notifyItemChanged(position);
    }
}
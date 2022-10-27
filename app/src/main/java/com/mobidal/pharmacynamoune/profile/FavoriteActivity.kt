package com.mobidal.pharmacynamoune.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mobidal.pharmacynamoune.ProductActivity;
import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.adapter.ProductAdapter;
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration;
import com.mobidal.pharmacynamoune.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity implements ProductAdapter.OnProduct2ClickListener {

    @BindView(R.id.tb_main) Toolbar mToolbar;
    @BindView(R.id.v_main) View mMainView;

    @BindView(R.id.rv_product) RecyclerView mProductRecyclerView;

    ProductAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Bind Views
        ButterKnife.bind(this);

        // setup the {@link Toolbar}
        setupToolbar();

        // setup {@Article} list
        setupProductList();
        // Load {@Article} list
        loadProductList();
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

    private void setupProductList() {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 1);

        // Create {@link RecyclerView.ItemDecoration}
        int spacingInPixel =
                getResources().getDimensionPixelSize(R.dimen.grid_product_spacing);
        GridSpacingItemDecoration gridSpacingItemDecoration =
                new GridSpacingItemDecoration(1, spacingInPixel, false);

        mProductAdapter =
                new ProductAdapter(this, null, ProductAdapter.TYPE_TWO);

        mProductAdapter.setOnProduct2ClickListener(this);

        mProductRecyclerView.setLayoutManager(gridLayoutManager);
        mProductRecyclerView.setAdapter(mProductAdapter);
        mProductRecyclerView.addItemDecoration(gridSpacingItemDecoration);
    }

    private void loadProductList() {
        // TODO load product list
        Product product =
                new Product(1, "Doliprane", "Doliprane 1000mg",
                        "Douleurs et fievre", 300,
                        "https://www.pharma-medicaments.com/wp-content/uploads/2022/01/3304746.jpg",
                        null, 120, null);

        List<Product> productList = new ArrayList<>();

        productList.add(product);
        productList.add(product);
        productList.add(product);
        productList.add(product);
        productList.add(product);
        productList.add(product);
        productList.add(product);
        productList.add(product);

        mProductRecyclerView.setAdapter(null);

        mProductAdapter.setLoading(false);
        mProductAdapter.setAll(productList);

        mProductRecyclerView.setAdapter(mProductAdapter);

    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.favorite));
    }

    @Override
    public void onProductClicked(Product product) {
        Intent intent = new Intent(this, ProductActivity.class);

        intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, product.getId());

        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(Product product, int adapterPosition) {
        mProductAdapter.remove(adapterPosition);
        Toast.makeText(this,
                getString(R.string.product_removed_from_favorite_list), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseClicked(Product product) {
        Toast.makeText(this,
                getString(R.string.the_product_has_been_purchased), Toast.LENGTH_SHORT).show();
    }
}
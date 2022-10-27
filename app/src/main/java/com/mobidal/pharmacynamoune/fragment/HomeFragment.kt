package com.mobidal.pharmacynamoune.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobidal.pharmacynamoune.ProductActivity;
import com.mobidal.pharmacynamoune.ProductListActivity;
import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.adapter.IntroCategoryAdapter;
import com.mobidal.pharmacynamoune.adapter.ProductAdapter;
import com.mobidal.pharmacynamoune.adapter.SecondaryCategoryAdapter;
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration;
import com.mobidal.pharmacynamoune.model.Category;
import com.mobidal.pharmacynamoune.model.IntroCategory;
import com.mobidal.pharmacynamoune.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements SecondaryCategoryAdapter.OnSecondaryCategoryClickListener, ProductAdapter.OnProductClickListener, IntroCategoryAdapter.OnViewAllClickListener {

    private Context mContext;

    // Top Secondary Views
    @BindView(R.id.rv_top_secondary_category) RecyclerView mTopCategoryRecyclerView;
    @BindView(R.id.rv_intro_category) RecyclerView mIntroCategoryRecyclerView;

    SecondaryCategoryAdapter mTopCategoryAdapter;
    IntroCategoryAdapter mIntroCategoryAdapter;

    public HomeFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_home, container, false);

        // Bind HomeFragment views
        ButterKnife.bind(this, rootView);

        // Setup Top {@link SecondaryCategory} RecyclerView
        setupTopCategory();
        // Load Top SecondaryCategory list
        loadTopCategoryList();

        // Setup {@link IntroCategory} RecyclerView
        setupIntroCategory();
        // Load {@link IntroCategory}
        loadIntroCategoryList();

        return rootView;
    }

    private void setupTopCategory() {
        // Create {@link GridLayoutManager} for the {@link Category} {@link RecyclerView}
        GridLayoutManager topSecondaryCategoryLayoutManager =
                new GridLayoutManager(mContext, 2);
        topSecondaryCategoryLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Create {@link SecondaryCategoryAdapter}
        mTopCategoryAdapter = new SecondaryCategoryAdapter(mContext,
                null,this);

        // Create {@link RecyclerView.ItemDecoration}
        int spacingInPixel =
                getResources().getDimensionPixelSize(R.dimen.grid_top_category_spacing);
        GridSpacingItemDecoration gridSpacingItemDecoration =
                new GridSpacingItemDecoration(2, spacingInPixel, false);

        // Setup {link RecyclerView} for {@link Category}
        mTopCategoryRecyclerView.setLayoutManager(topSecondaryCategoryLayoutManager);
        mTopCategoryRecyclerView.setAdapter(mTopCategoryAdapter);
        mTopCategoryRecyclerView.addItemDecoration(gridSpacingItemDecoration);
    }

    private void loadTopCategoryList() {
        // TODO load top category
        List<Category> categoryList = new ArrayList<>();

        Category category =
                new Category(1, "Antibiotic", "",
                        "https://www.pharma-medicaments.com/wp-content/uploads/2022/01/3304746.jpg",
                        null);

        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);

        mTopCategoryAdapter.setList(categoryList);
        mTopCategoryAdapter.setLoading(false);

        mTopCategoryRecyclerView.setAdapter(null);
        mTopCategoryRecyclerView.setAdapter(mTopCategoryAdapter);
    }

    private void setupIntroCategory() {
        LinearLayoutManager introSecondaryCategoryLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mIntroCategoryAdapter =
                new IntroCategoryAdapter(mContext, null,
                        this, this);

        // Create {@link RecyclerView.ItemDecoration}
        int spacingInPixel =
                getResources().getDimensionPixelSize(R.dimen.grid_intro_category_spacing);
        GridSpacingItemDecoration gridSpacingItemDecoration =
                new GridSpacingItemDecoration(1, spacingInPixel, false);

        mIntroCategoryRecyclerView.setLayoutManager(introSecondaryCategoryLayoutManager);
        mIntroCategoryRecyclerView.setAdapter(mIntroCategoryAdapter);
        mIntroCategoryRecyclerView.addItemDecoration(gridSpacingItemDecoration);
    }

    private void loadIntroCategoryList() {
        // TODO load intro category
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

        IntroCategory introCategory =
                new IntroCategory(1, "Antibiotic", "Get the best deal today",
                        productList);

        List<IntroCategory> introCategoryList = new ArrayList<>();
        introCategoryList.add(introCategory);
        introCategoryList.add(introCategory);
        introCategoryList.add(introCategory);
        introCategoryList.add(introCategory);

        mIntroCategoryAdapter.setList(introCategoryList);
        mIntroCategoryAdapter.setLoading(false);

        mIntroCategoryRecyclerView.setAdapter(null);
        mIntroCategoryRecyclerView.setAdapter(mIntroCategoryAdapter);
    }

    @Override
    public void onSecondaryCategoryClicked(Category category) {
        Intent intent = new Intent(mContext, ProductListActivity.class);

        intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category.getId());

        startActivity(intent);
    }

    @Override
    public void onProductClicked(Product product) {
        Intent intent = new Intent(mContext, ProductActivity.class);

        intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, product.getId());

        startActivity(intent);
    }

    @Override
    public void onViewAllClicked(IntroCategory category) {
        Intent intent = new Intent(mContext, ProductListActivity.class);

        intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category.getId());

        startActivity(intent);
    }
}

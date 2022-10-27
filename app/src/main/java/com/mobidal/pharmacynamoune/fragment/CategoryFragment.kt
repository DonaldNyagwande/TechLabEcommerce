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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobidal.pharmacynamoune.ProductListActivity;
import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.adapter.PrimaryCategoryAdapter;
import com.mobidal.pharmacynamoune.adapter.SecondaryCategoryAdapter;
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration;
import com.mobidal.pharmacynamoune.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment implements PrimaryCategoryAdapter.OnPrimaryCategoryClickListener, SecondaryCategoryAdapter.OnSecondaryCategoryClickListener {

    @BindView(R.id.rv_category) RecyclerView mCategoryRecyclerView;

    private Context mContext;
    private PrimaryCategoryAdapter mCategoryAdapter;

    public CategoryFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_category, container, false);

        // Bind HomeFragment views
        ButterKnife.bind(this, rootView);

        // Setup Primary Category list
        setupCategoryList();

        // Load Primary Category
        loadCategoryList();

        return rootView;
    }

    private void setupCategoryList() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        mCategoryAdapter = new PrimaryCategoryAdapter(mContext, null,
                this, this);

        // Create {@link RecyclerView.ItemDecoration}
        int spacingInPixel = mContext.getResources()
                .getDimensionPixelSize(R.dimen.grid_primary_category_spacing);
        GridSpacingItemDecoration gridSpacingItemDecoration =
                new GridSpacingItemDecoration(1, spacingInPixel, true);

        mCategoryRecyclerView.setLayoutManager(layoutManager);
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryRecyclerView.addItemDecoration(gridSpacingItemDecoration);
    }

    private void loadCategoryList() {
        // TODO load category list
        Category category =
                new Category(1, "Antibiotic", "",
                "https://www.pharma-medicaments.com/wp-content/uploads/2022/01/3304746.jpg",
                null);

        List<Category> categoryList = new ArrayList<>();

        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);

        Category primaryCategory =
                new Category(1, "Antibiotic", "", null, categoryList);

        List<Category> primaryCategoryList = new ArrayList<>();

        primaryCategoryList.add(primaryCategory);
        primaryCategoryList.add(primaryCategory);

        mCategoryAdapter.setList(primaryCategoryList);
        mCategoryAdapter.setLoading(false);

        mCategoryRecyclerView.setAdapter(null);
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);
    }

    @Override
    public void onPrimaryCategoryClicked(Category category) {
        Intent intent = new Intent(mContext, ProductListActivity.class);

        intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category.getId());

        startActivity(intent);
    }

    @Override
    public void onSecondaryCategoryClicked(Category category) {
        Intent intent = new Intent(mContext, ProductListActivity.class);

        intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category.getId());

        startActivity(intent);
    }
}

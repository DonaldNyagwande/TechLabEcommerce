package com.mobidal.pharmacynamoune.fragment;

import android.content.Context;
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

import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.adapter.SecondaryCategoryAdapter;
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration;
import com.mobidal.pharmacynamoune.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements SecondaryCategoryAdapter.OnSecondaryCategoryClickListener {

    private Context mContext;

    // Top Secondary Views
    @BindView(R.id.rv_top_secondary_category) RecyclerView mTopCategoryRecyclerView;

    SecondaryCategoryAdapter mTopCategoryAdapter;

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

        return rootView;
    }

    private void setupTopCategory() {
        // Create {@link GridLayoutManager} for the {@link Category} {@link RecyclerView}
        GridLayoutManager topSecondaryCategoryLayoutManager =
                new GridLayoutManager(mContext, 3);
        topSecondaryCategoryLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Create {@link SecondaryCategoryAdapter}
        mTopCategoryAdapter = new SecondaryCategoryAdapter(mContext,
                null,this);

        // Create {@link RecyclerView.ItemDecoration}
        int spacingInPixel =
                getResources().getDimensionPixelSize(R.dimen.grid_top_category_spacing);
        GridSpacingItemDecoration gridSpacingItemDecoration =
                new GridSpacingItemDecoration(3, spacingInPixel, true);

        // Setup {link RecyclerView} for {@link Category}
        mTopCategoryRecyclerView.setLayoutManager(topSecondaryCategoryLayoutManager);
        mTopCategoryRecyclerView.setAdapter(mTopCategoryAdapter);
        mTopCategoryRecyclerView.addItemDecoration(gridSpacingItemDecoration);
    }

    @Override
    public void onSecondaryCategoryClicked(Category secondaryCategory) {

    }

    private void loadTopCategoryList() {
        // TODO load top category
        List<Category> categoryList = new ArrayList<>();

        Category category =
                new Category(1, "Phone", "",
                        "https://dz.jumia.is/unsafe/fit-in/680x680/filters:fill(white)/product/55/5423/1.jpg?8047",
                        null);

        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
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
}

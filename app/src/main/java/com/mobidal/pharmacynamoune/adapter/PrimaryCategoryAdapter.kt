package com.mobidal.pharmacynamoune.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration;
import com.mobidal.pharmacynamoune.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrimaryCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SHIMMER_ITEM_COUNT = 2;

    private Context mContext;
    private List<Category> mCategoryList;
    private OnPrimaryCategoryClickListener mOnPrimaryCategoryClickListener;
    private SecondaryCategoryAdapter.OnSecondaryCategoryClickListener mOnSecondaryCategoryClickListener;

    private boolean mIsLoading = true;
    private int mShimmerItemCount = SHIMMER_ITEM_COUNT;

    public PrimaryCategoryAdapter(Context mContext, List<Category> mCategoryList,
                                  OnPrimaryCategoryClickListener mOnPrimaryCategoryClickListener,
                                  SecondaryCategoryAdapter.OnSecondaryCategoryClickListener mOnSecondaryCategoryClickListener) {
        this.mContext = mContext;
        this.mCategoryList = mCategoryList;
        this.mOnPrimaryCategoryClickListener = mOnPrimaryCategoryClickListener;
        this.mOnSecondaryCategoryClickListener = mOnSecondaryCategoryClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_placeholder_primary_category;

        if (!mIsLoading) {
            layoutId = R.layout.item_primary_category;
        }

        View itemView = LayoutInflater.from(mContext)
                .inflate(layoutId, parent, false);

        if (mIsLoading) {
            return new ShimmerViewHolder(itemView);
        } else {
            return new PrimaryCategoryViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mIsLoading) {
            ShimmerViewHolder viewHolder = (ShimmerViewHolder) holder;
            viewHolder.startShimmer();
        } else {
            PrimaryCategoryViewHolder viewHolder = (PrimaryCategoryViewHolder) holder;
            viewHolder.bind(mCategoryList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mIsLoading) {
            return mShimmerItemCount;
        } else {
            return mCategoryList == null ? 0 : mCategoryList.size();
        }
    }

    public void setList(List<Category> list) {
        mCategoryList = list;
        notifyDataSetChanged();
    }

    public void setLoading(boolean isLoading) {
        mIsLoading = isLoading;
    }

    public void setShimmerItemCount(int shimmerItemCount) {
        mShimmerItemCount = shimmerItemCount;
    }

    class PrimaryCategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.l_view_all) View mViewAllView;

        @BindView(R.id.tv_name) TextView mNameTextView;

        @BindView(R.id.rv_category) RecyclerView mCategoryRecyclerView;

        SecondaryCategoryAdapter mCategoryAdapter;

        public PrimaryCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind Views
            ButterKnife.bind(this, itemView);
        }

        public void bind(Category category) {
            // Setup data
            mNameTextView.setText(category.getName());

            // OnPrimaryCategoryClickListener
            mViewAllView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnPrimaryCategoryClickListener != null) {
                        mOnPrimaryCategoryClickListener.onPrimaryCategoryClicked(category);
                    }
                }
            });

            //Setup Secondary Category
            setupSecondaryCategoryList();

            // Load Secondary Category
            loadSecondaryCategoryList();
        }

        private void loadSecondaryCategoryList() {
            Category category = mCategoryList.get(getAdapterPosition());

            mCategoryAdapter.setLoading(false);

            mCategoryRecyclerView.setAdapter(null);
            mCategoryRecyclerView.setAdapter(mCategoryAdapter);

            mCategoryAdapter.setList(category.getCategoryList());
        }

        private void setupSecondaryCategoryList() {
            // Create {@link GridLayoutManager} for the {@link SecondaryCategory} {@link RecyclerView}
            GridLayoutManager secondaryCategoryLayoutManager =
                    new GridLayoutManager(mContext, 2);
            secondaryCategoryLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            mCategoryAdapter = new SecondaryCategoryAdapter(mContext,
                    null, mOnSecondaryCategoryClickListener);

            // Create {@link RecyclerView.ItemDecoration}
            int spacingInPixel = mContext.getResources()
                    .getDimensionPixelSize(R.dimen.grid_secondary_category_spacing);
            GridSpacingItemDecoration gridSpacingItemDecoration =
                    new GridSpacingItemDecoration(2, spacingInPixel, false);

            // Setup {link RecyclerView} for {@link SecondaryCategory}
            mCategoryRecyclerView.setLayoutManager(secondaryCategoryLayoutManager);
            mCategoryRecyclerView.setAdapter(mCategoryAdapter);
            mCategoryRecyclerView.addItemDecoration(gridSpacingItemDecoration);
        }

    }

    class ShimmerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sfl_primary_category)
        ShimmerFrameLayout mCategoryShimmerFrameLayout;

        @BindView(R.id.rv_category) RecyclerView mCategoryRecyclerView;

        SecondaryCategoryAdapter mCategoryAdapter;

        public ShimmerViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind views
            ButterKnife.bind(this, itemView);

            // Setup {@SecondaryCategory} list
            setupCategoryList();
        }

        private void setupCategoryList() {
            // Create {@link GridLayoutManager} for the {@link SecondaryCategory} {@link RecyclerView}
            GridLayoutManager secondaryCategoryLayoutManager =
                    new GridLayoutManager(mContext, 3);
            secondaryCategoryLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            mCategoryAdapter = new SecondaryCategoryAdapter(mContext,
                    null, mOnSecondaryCategoryClickListener);

            // Create {@link RecyclerView.ItemDecoration}
            int spacingInPixel = mContext.getResources()
                    .getDimensionPixelSize(R.dimen.grid_secondary_category_spacing);
            GridSpacingItemDecoration gridSpacingItemDecoration =
                    new GridSpacingItemDecoration(3, spacingInPixel, false);

            // Setup {link RecyclerView} for {@link SecondaryCategory}
            mCategoryRecyclerView.setLayoutManager(secondaryCategoryLayoutManager);
            mCategoryRecyclerView.setAdapter(mCategoryAdapter);
            mCategoryRecyclerView.addItemDecoration(gridSpacingItemDecoration);

        }

        public void startShimmer() {
            // Start shimmer
            mCategoryShimmerFrameLayout.startShimmer();
        }

        public void stopShimmer() {
            // Stop shimmer
            mCategoryShimmerFrameLayout.stopShimmer();
            mCategoryShimmerFrameLayout.setShimmer(null);
        }
    }

    public interface OnPrimaryCategoryClickListener {
        void onPrimaryCategoryClicked(Category category);
    }

}

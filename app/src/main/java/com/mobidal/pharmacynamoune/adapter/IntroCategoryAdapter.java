package com.mobidal.pharmacynamoune.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.model.IntroCategory;
import com.mobidal.pharmacynamoune.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroCategoryAdapter extends RecyclerView
        .Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<IntroCategory> mIntroCategoryList;
    private OnViewAllClickListener mOnViewAllClickListener;
    private ProductAdapter.OnProductClickListener mOnProductClickListener;

    private boolean mIsLoading = true;
    private static final int SHIMMER_ITEM_COUNT = 2;

    public IntroCategoryAdapter(Context mContext,
                                List<IntroCategory> mIntroCategoryList,
                                OnViewAllClickListener mOnViewAllClickListener,
                                ProductAdapter.OnProductClickListener mOnProductClickListener) {
        this.mContext = mContext;
        this.mIntroCategoryList = mIntroCategoryList;
        this.mOnViewAllClickListener = mOnViewAllClickListener;
        this.mOnProductClickListener = mOnProductClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_placeholder_intro_category;

        if (!mIsLoading) {
            layoutId = R.layout.item_intro_category;
        }

        View itemView = LayoutInflater.from(mContext)
                .inflate(layoutId, parent, false);

        if (mIsLoading) {
            return new ShimmerViewHolder(itemView);
        } else {
            return new IntroSecondaryCategoryViewHolder(itemView);
        }

    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mIsLoading) {
            ShimmerViewHolder viewHolder = (ShimmerViewHolder) holder;
            viewHolder.startShimmer();
        } else {
            IntroSecondaryCategoryViewHolder viewHolder = (IntroSecondaryCategoryViewHolder) holder;
            viewHolder.bind(mIntroCategoryList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mIsLoading) {
            return SHIMMER_ITEM_COUNT;
        } else {
            return mIntroCategoryList == null ? 0 : mIntroCategoryList.size();
        }
    }

    public void setList(List<IntroCategory> list) {
        mIntroCategoryList = list;
        notifyDataSetChanged();
    }

    public void setLoading(boolean isLoading) {
        mIsLoading = isLoading;
    }

    class IntroSecondaryCategoryViewHolder extends RecyclerView.ViewHolder
            implements ProductAdapter.OnProductClickListener, View.OnClickListener {

        @BindView(R.id.tv_name) TextView mNameTextView;
        @BindView(R.id.tv_offer_text) TextView mOfferTextView;
        @BindView(R.id.tv_view_all) View mViewAllView;
        @BindView(R.id.rv_product) RecyclerView mProductRecyclerView;

        ProductAdapter mProductAdapter;

        public IntroSecondaryCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind views
            ButterKnife.bind(this, itemView);

            // Setup {@Article} list
            setupProductList();
        }

        private void setupProductList() {
            GridLayoutManager gridLayoutManager =
                    new GridLayoutManager(mContext, 2);
            mProductAdapter =
                    new ProductAdapter(mContext, null,this);

            mProductAdapter.setLoading(false);

            mProductRecyclerView.setLayoutManager(gridLayoutManager);
            mProductRecyclerView.setAdapter(mProductAdapter);
            mProductRecyclerView.addItemDecoration(
                    new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
            mProductRecyclerView.addItemDecoration(
                    new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        }

        public void bind(IntroCategory introCategory) {
            List<Product> productList = introCategory.getProductList();

            mProductAdapter.setAll(productList);

            mNameTextView.setText(introCategory.getName());
            mOfferTextView.setText(introCategory.getOfferText());

            mViewAllView.setOnClickListener(this);
        }

        @Override
        public void onProductClicked(Product product) {
            if (mOnProductClickListener != null) {
                mOnProductClickListener.onProductClicked(product);
            }
        }

        @Override
        public void onClick(View v) {
            if (mOnViewAllClickListener != null) {
                mOnViewAllClickListener.onViewAllClicked(
                        mIntroCategoryList
                                .get(getAdapterPosition()));
            }
        }
    }

    class ShimmerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sfl_category)
        ShimmerFrameLayout mCategoryShimmerFrameLayout;
        @BindView(R.id.rv_product) RecyclerView mArticleRecyclerView;

        ProductAdapter mProductAdapter;

        public ShimmerViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind views
            ButterKnife.bind(this, itemView);

            // Setup {@Article} list
            setupProductList();
        }

        private void setupProductList() {
            GridLayoutManager gridLayoutManager =
                    new GridLayoutManager(mContext, 2);
            mProductAdapter =
                    new ProductAdapter(mContext, null, null);

            mArticleRecyclerView.setLayoutManager(gridLayoutManager);
            mArticleRecyclerView.setAdapter(mProductAdapter);
            mArticleRecyclerView.addItemDecoration(
                    new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
            mArticleRecyclerView.addItemDecoration(
                    new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
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


    public interface OnViewAllClickListener {
        void onViewAllClicked(IntroCategory category);
    }

}

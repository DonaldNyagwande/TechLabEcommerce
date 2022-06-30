package com.mobidal.pharmacynamoune.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SHIMMER_ITEM_COUNT = 4;

    private Context mContext;
    private List<Product> mProductList;
    private OnArticleClickListener mOnArticleClickListener;

    private boolean mIsLoading = true;
    private int mShimmerItemCount = SHIMMER_ITEM_COUNT;

    public ProductAdapter(Context mContext, List<Product> mProductList, OnArticleClickListener mOnArticleClickListener) {
        this.mContext = mContext;
        this.mProductList = mProductList;
        this.mOnArticleClickListener = mOnArticleClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_placeholder_product;

        if (!mIsLoading) {
            layoutId = R.layout.item_product;
        }

        View itemView = LayoutInflater.from(mContext)
                .inflate(layoutId, parent, false);

        if (mIsLoading) {
            return new ShimmerViewHolder(itemView);
        } else {
            return new ArticleViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mIsLoading) {
            ShimmerViewHolder viewHolder = (ShimmerViewHolder) holder;
            viewHolder.startShimmer();
        } else {
            ArticleViewHolder viewHolder = (ArticleViewHolder) holder;
            viewHolder.bind(mProductList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mIsLoading) {
            return mShimmerItemCount;
        } else {
            return mProductList == null ? 0 : mProductList.size();
        }
    }

    public void setAll(List<Product> productList) {
        mProductList = productList;
        notifyDataSetChanged();
    }

    public void setLoading(boolean isLoading) {
        mIsLoading = isLoading;
    }

    public void setShimmerItemCount(int shimmerItemCount) {
        mShimmerItemCount = shimmerItemCount;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_picture)
        ImageView mPictureImageView;
        @BindView(R.id.tv_mark)
        TextView mMarkTextView;
        @BindView(R.id.tv_name) TextView mNameTextView;
        @BindView(R.id.tv_view_number) TextView mViewNumberTextView;
        @BindView(R.id.tv_price) TextView mPriceTextView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind views
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void bind(Product product) {
            Picasso.get()
                    .load(product.getPictureUrl())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(mPictureImageView);

            mMarkTextView.setText(product.getMark());
            mNameTextView.setText(product.getName());
            mViewNumberTextView.setText(
                    mContext.getString(R.string.views_number, product.getViewNumber()));
            mPriceTextView.setText(
                    mContext.getString(R.string.price, product.getPrice()));
        }

        @Override
        public void onClick(View v) {
            if (mOnArticleClickListener != null) {
                mOnArticleClickListener.onArticleClicked(mProductList.get(getAdapterPosition()));
            }
        }
    }

    class ShimmerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sfl_article)
        ShimmerFrameLayout mCategoryShimmerFrameLayout;

        public ShimmerViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind views
            ButterKnife.bind(this, itemView);
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

    public interface OnArticleClickListener {
        void onArticleClicked(Product product);
    }

}

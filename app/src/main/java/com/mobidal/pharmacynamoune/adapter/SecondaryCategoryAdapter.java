package com.mobidal.pharmacynamoune.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mobidal.pharmacynamoune.R;
import com.mobidal.pharmacynamoune.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondaryCategoryAdapter extends RecyclerView
        .Adapter<RecyclerView.ViewHolder> {

    private static final int SHIMMER_ITEM_COUNT = 9;

    private Context mContext;
    private List<Category> mCategoryList;
    private OnSecondaryCategoryClickListener mOnSecondaryCategoryClickListener;

    private boolean mIsLoading = true;
    private int mShimmerItemCount = SHIMMER_ITEM_COUNT;

    public SecondaryCategoryAdapter(Context mContext,
                                    List<Category> mCategoryList,
                                    OnSecondaryCategoryClickListener mOnSecondaryCategoryClickListener) {
        this.mContext = mContext;
        this.mCategoryList = mCategoryList;
        this.mOnSecondaryCategoryClickListener = mOnSecondaryCategoryClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_placeholder_secondary_category;

        if (!mIsLoading) {
            layoutId = R.layout.item_secondary_category;
        }

        View itemView = LayoutInflater.from(mContext)
                .inflate(layoutId, parent, false);

        if (mIsLoading) {
            return new ShimmerViewHolder(itemView);
        } else {
            return new SecondaryCategoryViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mIsLoading) {
            ShimmerViewHolder viewHolder = (ShimmerViewHolder) holder;
            viewHolder.startShimmer();
        } else {
            SecondaryCategoryViewHolder viewHolder = (SecondaryCategoryViewHolder) holder;
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

    public void setList(List<Category> categoryList) {
        mCategoryList = categoryList;
        notifyDataSetChanged();
    }

    public void setLoading(boolean isLoading) {
        mIsLoading = isLoading;
    }

    public void setShimmerItemCount(int shimmerItemCount) {
        mShimmerItemCount = shimmerItemCount;
    }

    class SecondaryCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_picture)
        ImageView mPictureImageView;
        @BindView(R.id.tv_name)
        TextView mNameTextView;

        public SecondaryCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind views
            ButterKnife.bind(this, itemView);

            // Setup OnClickListener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnSecondaryCategoryClickListener != null) {
                // get selected item position
                int itemPosition = getAdapterPosition();
                // get selected item
                Category selectedSecondaryCategory =
                        mCategoryList.get(itemPosition);
                // Callback for the selected item
                mOnSecondaryCategoryClickListener
                        .onSecondaryCategoryClicked(selectedSecondaryCategory);
            }
        }

        public void bind(Category category) {
            Picasso.get()
                    .load(category.getPictureUrl())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(mPictureImageView);

            Log.v("SCA", category.getPictureUrl());

            mNameTextView.setText(category.getName());
        }
    }

    class ShimmerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sfl_category)
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

    public interface OnSecondaryCategoryClickListener {
        void onSecondaryCategoryClicked(Category secondaryCategory);
    }

}
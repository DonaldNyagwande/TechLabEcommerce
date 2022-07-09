package com.mobidal.pharmacynamoune.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;
    public static final int TYPE_FOUR = 4;

    private Context mContext;
    private List<Product> mProductList;
    private OnProductClickListener mOnProductClickListener;
    private OnProduct2ClickListener mOnProduct2ClickListener;
    private OnProduct3ClickListener mOnProduct3ClickListener;
    private OnProduct4ClickListener mOnProduct4ClickListener;

    private int mType = TYPE_ONE;
    private boolean mIsLoading = true;
    private int mShimmerItemCount = SHIMMER_ITEM_COUNT;

    public ProductAdapter(Context mContext, List<Product> mProductList, int mType) {
        this.mContext = mContext;
        this.mProductList = mProductList;
        this.mOnProductClickListener = mOnProductClickListener;
        this.mType = mType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;

        if (mType == TYPE_ONE) {
            // Product type one
            if (mIsLoading) {
                layoutId = R.layout.item_placeholder_product;
            } else {
                layoutId = R.layout.item_product;
            }
        } else if (mType == TYPE_TWO) {
            // Product type two
            if (mIsLoading) {
                layoutId = R.layout.item_placeholder_product_2;
            } else {
                layoutId = R.layout.item_product_2;
            }
        } else if (mType == TYPE_THREE) {
            // Product type three
            if (mIsLoading) {
                layoutId = R.layout.item_placeholder_product_3;
            } else {
                layoutId = R.layout.item_product_3;
            }
        } else {
            // Product type four
            if (mIsLoading) {
                layoutId = R.layout.item_placeholder_product_4;
            } else {
                layoutId = R.layout.item_product_4;
            }
        }

        View itemView = LayoutInflater.from(mContext)
                .inflate(layoutId, parent, false);

        if (mIsLoading) {
            return new ShimmerViewHolder(itemView);
        } else {
            if (mType == TYPE_ONE) {
                // Product type one
                return new ProductViewHolder(itemView);
            } else if (mType == TYPE_TWO) {
                // Product type two
                return new Product2ViewHolder(itemView);
            } else if (mType == TYPE_THREE) {
                // Product type three
                return new Product3ViewHolder(itemView);
            } else {
                // Product type four
                return new Product4ViewHolder(itemView);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mIsLoading) {
            ShimmerViewHolder viewHolder = (ShimmerViewHolder) holder;
            viewHolder.startShimmer();
        } else {
            if (mType == TYPE_ONE) {
                // Product type one
                ProductViewHolder viewHolder = (ProductViewHolder) holder;
                viewHolder.bind(mProductList.get(position));
            } else if (mType == TYPE_TWO) {
                // Product type two
                Product2ViewHolder viewHolder = (Product2ViewHolder) holder;
                viewHolder.bind(mProductList.get(position));
            } else if (mType == TYPE_THREE) {
                // Product type three
                Product3ViewHolder viewHolder = (Product3ViewHolder) holder;
                viewHolder.bind(mProductList.get(position));
            } else {
                // Product type four
                Product3ViewHolder viewHolder = (Product3ViewHolder) holder;
                viewHolder.bind(mProductList.get(position));
            }
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

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        mOnProductClickListener = onProductClickListener;
    }

    public void setOnProduct2ClickListener(OnProduct2ClickListener onProduct2ClickListener) {
        mOnProduct2ClickListener = onProduct2ClickListener;
    }

    public void setOnProduct3ClickListener(OnProduct3ClickListener onProduct3ClickListener) {
        mOnProduct3ClickListener = onProduct3ClickListener;
    }

    public void setOnProduct4ClickListener(OnProduct4ClickListener onProduct4ClickListener) {
        mOnProduct4ClickListener = onProduct4ClickListener;
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

    public void remove(int position) {
        mProductList.remove(position);
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_picture) ImageView mPictureImageView;
        @BindView(R.id.tv_mark) TextView mMarkTextView;
        @BindView(R.id.tv_name) TextView mNameTextView;
        @BindView(R.id.tv_view_number) TextView mViewNumberTextView;
        @BindView(R.id.tv_price) TextView mPriceTextView;

        public ProductViewHolder(@NonNull View itemView) {
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
            if (mOnProductClickListener != null) {
                mOnProductClickListener.onProductClicked(mProductList.get(getAdapterPosition()));
            }
        }
    }

    class Product2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.v_product) View mProductView;

        @BindView(R.id.iv_picture) ImageView mPictureImageView;
        @BindView(R.id.tv_mark) TextView mMarkTextView;
        @BindView(R.id.tv_name) TextView mNameTextView;
        @BindView(R.id.tv_price) TextView mPriceTextView;

        @BindView(R.id.b_remove) Button mRemoveButton;
        @BindView(R.id.b_purchase) Button mPurchaseButton;

        public Product2ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind views
            ButterKnife.bind(this, itemView);

            // Setup product click listener
            mProductView.setOnClickListener(this);

            // Setup remove product click listener
            setupRemoveProduct();

            // Setup purchase product click listener
            setupPurchaseProduct();
        }

        private void setupPurchaseProduct() {
            mPurchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnProduct2ClickListener != null) {
                        mOnProduct2ClickListener
                                .onPurchaseClicked(mProductList.get(getAdapterPosition()));
                    }
                }
            });
        }

        private void setupRemoveProduct() {
            mRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnProduct2ClickListener != null) {
                        mOnProduct2ClickListener
                                .onDeleteClicked(mProductList.get(getAdapterPosition()),
                                        getAdapterPosition());
                    }
                }
            });
        }

        public void bind(Product product) {
            Picasso.get()
                    .load(product.getPictureUrl())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(mPictureImageView);
            mMarkTextView.setText(product.getMark());
            mNameTextView.setText(product.getName());
            mPriceTextView.setText(mContext.getString(R.string.price, product.getPrice()));
        }

        @Override
        public void onClick(View v) {
            if (mOnProduct2ClickListener != null) {
                mOnProduct2ClickListener.onProductClicked(mProductList.get(getAdapterPosition()));
            }
        }
    }

    class Product3ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.v_product) View mProductView;

        @BindView(R.id.iv_picture) ImageView mPictureImageView;
        @BindView(R.id.tv_mark) TextView mMarkTextView;
        @BindView(R.id.tv_name) TextView mNameTextView;
        @BindView(R.id.tv_price) TextView mPriceTextView;

        @BindView(R.id.b_purchase) Button mPurchaseButton;

        public Product3ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind views
            ButterKnife.bind(this, itemView);

            // Setup product click listener
            mProductView.setOnClickListener(this);

            // Setup remove product click listener
            setupPurchaseProduct();
        }

        private void setupPurchaseProduct() {
            mPurchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnProduct3ClickListener != null) {
                        mOnProduct3ClickListener
                                .onPurchaseClicked(mProductList.get(getAdapterPosition()));
                    }
                }
            });
        }

        public void bind(Product product) {
            Picasso.get()
                    .load(product.getPictureUrl())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(mPictureImageView);
            mMarkTextView.setText(product.getMark());
            mNameTextView.setText(product.getName());
            mPriceTextView.setText(mContext.getString(R.string.price, product.getPrice()));
        }

        @Override
        public void onClick(View v) {
            if (mOnProduct3ClickListener != null) {
                mOnProduct3ClickListener.onProductClicked(mProductList.get(getAdapterPosition()));
            }
        }
    }

    class Product4ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.v_product) View mProductView;

        @BindView(R.id.iv_picture) ImageView mPictureImageView;
        @BindView(R.id.tv_mark) TextView mMarkTextView;
        @BindView(R.id.tv_name) TextView mNameTextView;
        @BindView(R.id.tv_price) TextView mPriceTextView;

        @BindView(R.id.b_remove) Button mRemoveButton;
        @BindView(R.id.ib_decrement) View mDecrementView;
        @BindView(R.id.tv_quantity) TextView mQuantityTextView;
        @BindView(R.id.ib_increment) View mIncrementView;

        public Product4ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind views
            ButterKnife.bind(this, itemView);

            // Setup product click listener
            mProductView.setOnClickListener(this);

            // Setup remove product click listener
            setupRemoveProduct();

            // Setup change quantity click listener
            setupChangeQuantity();
        }

        private void setupChangeQuantity() {
            // Setup Decrement quantity
            setupDecrementQuantity();

            // Setup Increment quantity
            setupIncrementQuantity();
        }

        private void setupDecrementQuantity() {
            mDecrementView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnProduct3ClickListener != null) {
                        Product product = mProductList.get(getAdapterPosition());

                        if (product.getPivot().getQuantity() > 1) {
                            mOnProduct4ClickListener.onDecrementClicked(product);
                        }
                    }
                }
            });
        }

        private void setupIncrementQuantity() {
            mIncrementView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnProduct3ClickListener != null) {
                        Product product = mProductList.get(getAdapterPosition());

                        mOnProduct4ClickListener.onIncrementClicked(product);
                    }
                }
            });
        }

        private void setupRemoveProduct() {
            mRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnProduct2ClickListener != null) {
                        mOnProduct2ClickListener
                                .onDeleteClicked(mProductList.get(getAdapterPosition()),
                                        getAdapterPosition());
                    }
                }
            });
        }

        public void bind(Product product) {
            Picasso.get()
                    .load(product.getPictureUrl())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(mPictureImageView);
            mMarkTextView.setText(product.getMark());
            mNameTextView.setText(product.getName());
            mPriceTextView.setText(mContext.getString(R.string.price, product.getPrice()));
            mQuantityTextView.setText(String.valueOf(product.getPivot().getQuantity()));
        }

        @Override
        public void onClick(View v) {
            if (mOnProduct2ClickListener != null) {
                mOnProduct2ClickListener.onProductClicked(mProductList.get(getAdapterPosition()));
            }
        }
    }

    class ShimmerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sfl_product)
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

    public interface OnProductClickListener {
        void onProductClicked(Product product);
    }

    public interface OnProduct2ClickListener {
        void onProductClicked(Product product);
        void onDeleteClicked(Product product, int adapterPosition);
        void onPurchaseClicked(Product product);
    }

    public interface OnProduct3ClickListener {
        void onProductClicked(Product product);
        void onPurchaseClicked(Product product);
    }

    public interface OnProduct4ClickListener {
        void onProductClicked(Product product);
        void onDecrementClicked(Product product);
        void onIncrementClicked(Product product);
    }

}

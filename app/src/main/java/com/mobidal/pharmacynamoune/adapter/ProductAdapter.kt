package com.mobidal.pharmacynamoune.adapter

import android.content.Context
import com.mobidal.pharmacynamoune.model.Product
import androidx.recyclerview.widget.RecyclerView
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProductClickListener
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProduct2ClickListener
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProduct3ClickListener
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProduct4ClickListener
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProduct5ClickListener
import com.mobidal.pharmacynamoune.adapter.ProductAdapter
import android.view.ViewGroup
import com.mobidal.pharmacynamoune.R
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.ProductViewHolder
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.Product2ViewHolder
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.Product3ViewHolder
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.Product4ViewHolder
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.Product5ViewHolder
import butterknife.BindView
import android.widget.TextView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import com.facebook.shimmer.ShimmerFrameLayout

class ProductAdapter(
    private val mContext: Context,
    private var mProductList: List<Product>?,
    mType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mOnProductClickListener: OnProductClickListener? = null
    private var mOnProduct2ClickListener: OnProduct2ClickListener? = null
    private var mOnProduct3ClickListener: OnProduct3ClickListener? = null
    private var mOnProduct4ClickListener: OnProduct4ClickListener? = null
    private var mOnProduct5ClickListener: OnProduct5ClickListener? = null
    private var mType = TYPE_ONE
    private var mIsLoading = true
    private var mShimmerItemCount = SHIMMER_ITEM_COUNT

    init {
        this.mType = mType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutId: Int
        layoutId = if (mType == TYPE_ONE) {
            // Product type one
            if (mIsLoading) {
                R.layout.item_placeholder_product
            } else {
                R.layout.item_product
            }
        } else if (mType == TYPE_TWO) {
            // Product type two
            if (mIsLoading) {
                R.layout.item_placeholder_product_2
            } else {
                R.layout.item_product_2
            }
        } else if (mType == TYPE_THREE) {
            // Product type three
            if (mIsLoading) {
                R.layout.item_placeholder_product_3
            } else {
                R.layout.item_product_3
            }
        } else if (mType == TYPE_FOUR) {
            // Product type four
            if (mIsLoading) {
                R.layout.item_placeholder_product_4
            } else {
                R.layout.item_product_4
            }
        } else {
            R.layout.item_product_5
        }
        val itemView = LayoutInflater.from(mContext)
            .inflate(layoutId, parent, false)
        return if (mIsLoading) {
            ShimmerViewHolder(itemView)
        } else {
            if (mType == TYPE_ONE) {
                // Product type one
                ProductViewHolder(itemView)
            } else if (mType == TYPE_TWO) {
                // Product type two
                Product2ViewHolder(itemView)
            } else if (mType == TYPE_THREE) {
                // Product type three
                Product3ViewHolder(itemView)
            } else if (mType == TYPE_FOUR) {
                // Product type four
                Product4ViewHolder(itemView)
            } else {
                // Product type five
                Product5ViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mIsLoading) {
            val viewHolder = holder as ShimmerViewHolder
            viewHolder.startShimmer()
        } else {
            if (mType == TYPE_ONE) {
                // Product type one
                val viewHolder = holder as ProductViewHolder
                viewHolder.bind(mProductList!![position])
            } else if (mType == TYPE_TWO) {
                // Product type two
                val viewHolder = holder as Product2ViewHolder
                viewHolder.bind(mProductList!![position])
            } else if (mType == TYPE_THREE) {
                // Product type three
                val viewHolder = holder as Product3ViewHolder
                viewHolder.bind(mProductList!![position])
            } else if (mType == TYPE_FOUR) {
                // Product type four
                val viewHolder = holder as Product4ViewHolder
                viewHolder.bind(mProductList!![position])
            } else {
                // Product type five
                val viewHolder = holder as Product5ViewHolder
                viewHolder.bind(mProductList!![position])
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mIsLoading) {
            mShimmerItemCount
        } else {
            if (mProductList == null) 0 else mProductList!!.size
        }
    }

    fun setOnProductClickListener(onProductClickListener: OnProductClickListener?) {
        mOnProductClickListener = onProductClickListener
    }

    fun setOnProduct2ClickListener(onProduct2ClickListener: OnProduct2ClickListener?) {
        mOnProduct2ClickListener = onProduct2ClickListener
    }

    fun setOnProduct3ClickListener(onProduct3ClickListener: OnProduct3ClickListener?) {
        mOnProduct3ClickListener = onProduct3ClickListener
    }

    fun setOnProduct4ClickListener(onProduct4ClickListener: OnProduct4ClickListener?) {
        mOnProduct4ClickListener = onProduct4ClickListener
    }

    fun setOnProduct5ClickListener(onProduct5ClickListener: OnProduct5ClickListener?) {
        mOnProduct5ClickListener = onProduct5ClickListener
    }

    fun setAll(productList: List<Product>?) {
        mProductList = productList
        notifyDataSetChanged()
    }

    fun setLoading(isLoading: Boolean) {
        mIsLoading = isLoading
    }

    fun setShimmerItemCount(shimmerItemCount: Int) {
        mShimmerItemCount = shimmerItemCount
    }

    fun remove(position: Int) {
       // mProductList.removeAt(position)
        notifyDataSetChanged()
    }

    internal inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        @JvmField
        @BindView(R.id.iv_picture)
        var mPictureImageView: ImageView? = null

        @JvmField
        @BindView(R.id.tv_mark)
        var mMarkTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_name)
        var mNameTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_view_number)
        var mViewNumberTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_price)
        var mPriceTextView: TextView? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        fun bind(product: Product) {
            Picasso.get()
                .load(product.pictureUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(mPictureImageView)
            mMarkTextView!!.text = product.mark
            mNameTextView!!.text = product.name
            mViewNumberTextView!!.text =
                mContext.getString(R.string.views_number, product.viewNumber)
            mPriceTextView!!.text = mContext.getString(R.string.price, product.price)
        }

        override fun onClick(v: View) {
            if (mOnProductClickListener != null) {
                mOnProductClickListener!!.onProductClicked(mProductList!![adapterPosition])
            }
        }
    }

    internal inner class Product2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        @JvmField
        @BindView(R.id.v_product)
        var mProductView: View? = null

        @JvmField
        @BindView(R.id.iv_picture)
        var mPictureImageView: ImageView? = null

        @JvmField
        @BindView(R.id.tv_mark)
        var mMarkTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_name)
        var mNameTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_price)
        var mPriceTextView: TextView? = null

        @JvmField
        @BindView(R.id.b_remove)
        var mRemoveButton: Button? = null

        @JvmField
        @BindView(R.id.b_purchase)
        var mPurchaseButton: Button? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)

            // Setup product click listener
            mProductView!!.setOnClickListener(this)

            // Setup remove product click listener
            setupRemoveProduct()

            // Setup purchase product click listener
            setupPurchaseProduct()
        }

        private fun setupPurchaseProduct() {
            mPurchaseButton!!.setOnClickListener {
                if (mOnProduct2ClickListener != null) {
                    mOnProduct2ClickListener!!
                        .onPurchaseClicked(mProductList!![adapterPosition])
                }
            }
        }

        private fun setupRemoveProduct() {
            mRemoveButton!!.setOnClickListener {
                if (mOnProduct2ClickListener != null) {
                    mOnProduct2ClickListener!!
                        .onDeleteClicked(mProductList!![adapterPosition],
                            adapterPosition)
                }
            }
        }

        fun bind(product: Product) {
            Picasso.get()
                .load(product.pictureUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(mPictureImageView)
            mMarkTextView!!.text = product.mark
            mNameTextView!!.text = product.name
            mPriceTextView!!.text = mContext.getString(R.string.price, product.price)
        }

        override fun onClick(v: View) {
            if (mOnProduct2ClickListener != null) {
                mOnProduct2ClickListener!!.onProductClicked(mProductList!![adapterPosition])
            }
        }
    }

    internal inner class Product3ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        @JvmField
        @BindView(R.id.v_product)
        var mProductView: View? = null

        @JvmField
        @BindView(R.id.iv_picture)
        var mPictureImageView: ImageView? = null

        @JvmField
        @BindView(R.id.tv_mark)
        var mMarkTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_name)
        var mNameTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_price)
        var mPriceTextView: TextView? = null

        @JvmField
        @BindView(R.id.b_purchase)
        var mPurchaseButton: Button? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)

            // Setup product click listener
            mProductView!!.setOnClickListener(this)

            // Setup remove product click listener
            setupPurchaseProduct()
        }

        private fun setupPurchaseProduct() {
            mPurchaseButton!!.setOnClickListener {
                if (mOnProduct3ClickListener != null) {
                    mOnProduct3ClickListener!!
                        .onPurchaseClicked(mProductList!![adapterPosition])
                }
            }
        }

        fun bind(product: Product) {
            Picasso.get()
                .load(product.pictureUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(mPictureImageView)
            mMarkTextView!!.text = product.mark
            mNameTextView!!.text = product.name
            mPriceTextView!!.text = mContext.getString(R.string.price, product.price)
        }

        override fun onClick(v: View) {
            if (mOnProduct3ClickListener != null) {
                mOnProduct3ClickListener!!.onProductClicked(mProductList!![adapterPosition])
            }
        }
    }

    internal inner class Product4ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        @JvmField
        @BindView(R.id.v_product)
        var mProductView: View? = null

        @JvmField
        @BindView(R.id.iv_picture)
        var mPictureImageView: ImageView? = null

        @JvmField
        @BindView(R.id.tv_mark)
        var mMarkTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_name)
        var mNameTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_price)
        var mPriceTextView: TextView? = null

        @JvmField
        @BindView(R.id.b_remove)
        var mRemoveButton: Button? = null

        @JvmField
        @BindView(R.id.ib_decrement)
        var mDecrementView: View? = null

        @JvmField
        @BindView(R.id.tv_quantity)
        var mQuantityTextView: TextView? = null

        @JvmField
        @BindView(R.id.ib_increment)
        var mIncrementView: View? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)

            // Setup product click listener
            mProductView!!.setOnClickListener(this)

            // Setup remove product click listener
            setupRemoveProduct()

            // Setup change quantity click listener
            setupChangeQuantity()
        }

        private fun setupChangeQuantity() {
            // Setup Decrement quantity
            setupDecrementQuantity()

            // Setup Increment quantity
            setupIncrementQuantity()
        }

        private fun setupDecrementQuantity() {
            mDecrementView!!.setOnClickListener {
                if (mOnProduct4ClickListener != null) {
                    val product = mProductList!![adapterPosition]
                    if (product.pivot.quantity > 1) {
                        mOnProduct4ClickListener!!
                            .onDecrementClicked(product, adapterPosition)
                    }
                }
            }
        }

        private fun setupIncrementQuantity() {
            mIncrementView!!.setOnClickListener {
                if (mOnProduct4ClickListener != null) {
                    val product = mProductList!![adapterPosition]
                    mOnProduct4ClickListener!!
                        .onIncrementClicked(product, adapterPosition)
                }
            }
        }

        private fun setupRemoveProduct() {
            mRemoveButton!!.setOnClickListener {
                if (mOnProduct4ClickListener != null) {
                    mOnProduct4ClickListener!!
                        .onDeleteClicked(mProductList!![adapterPosition],
                            adapterPosition)
                }
            }
        }

        fun bind(product: Product) {
            Picasso.get()
                .load(product.pictureUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(mPictureImageView)
            mMarkTextView!!.text = product.mark
            mNameTextView!!.text = product.name
            mPriceTextView!!.text = mContext.getString(R.string.price, product.price)
            mQuantityTextView!!.text = product.pivot.quantity.toString()
        }

        override fun onClick(v: View) {
            if (mOnProduct4ClickListener != null) {
                mOnProduct4ClickListener!!.onProductClicked(mProductList!![adapterPosition])
            }
        }
    }

    internal inner class Product5ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        @JvmField
        @BindView(R.id.iv_picture)
        var mPictureImageView: ImageView? = null

        @JvmField
        @BindView(R.id.tv_mark)
        var mMarkTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_name)
        var mNameTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_quantity)
        var mQuantityTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_price)
        var mPriceTextView: TextView? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        fun bind(product: Product) {
            Picasso.get()
                .load(product.pictureUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(mPictureImageView)
            mMarkTextView!!.text = product.mark
            mNameTextView!!.text = product.name
            mQuantityTextView!!.text = mContext.getString(R.string.quantity, product.pivot.quantity)
            mPriceTextView!!.text = mContext.getString(R.string.price, product.price)
        }

        override fun onClick(v: View) {
            if (mOnProduct5ClickListener != null) {
                mOnProduct5ClickListener!!.onProductClicked(mProductList!![adapterPosition])
            }
        }
    }

    internal inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField
        @BindView(R.id.sfl_product)
        var mCategoryShimmerFrameLayout: ShimmerFrameLayout? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)
        }

        fun startShimmer() {
            // Start shimmer
            mCategoryShimmerFrameLayout!!.startShimmer()
        }

        fun stopShimmer() {
            // Stop shimmer
            mCategoryShimmerFrameLayout!!.stopShimmer()
            mCategoryShimmerFrameLayout!!.setShimmer(null)
        }
    }

    interface OnProductClickListener {
        fun onProductClicked(product: Product?)
    }

    interface OnProduct2ClickListener {
        fun onProductClicked(product: Product?)
        fun onDeleteClicked(product: Product?, position: Int)
        fun onPurchaseClicked(product: Product?)
    }

    interface OnProduct3ClickListener {
        fun onProductClicked(product: Product?)
        fun onPurchaseClicked(product: Product?)
    }

    interface OnProduct4ClickListener {
        fun onProductClicked(product: Product?)
        fun onDeleteClicked(product: Product?, position: Int)
        fun onDecrementClicked(product: Product?, position: Int)
        fun onIncrementClicked(product: Product?, position: Int)
    }

    interface OnProduct5ClickListener {
        fun onProductClicked(product: Product?)
    }

    companion object {
        private const val SHIMMER_ITEM_COUNT = 4
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
        const val TYPE_THREE = 3
        const val TYPE_FOUR = 4
        const val TYPE_FIVE = 5
    }
}
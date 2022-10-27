package com.mobidal.pharmacynamoune.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.shimmer.ShimmerFrameLayout
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.model.Category
import com.squareup.picasso.Picasso

class SecondaryCategoryAdapter(
    private val mContext: Context,
    private var mCategoryList: List<Category>?,
    private val mOnSecondaryCategoryClickListener: OnSecondaryCategoryClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mIsLoading = true
    private var mShimmerItemCount = SHIMMER_ITEM_COUNT
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutId = R.layout.item_placeholder_secondary_category
        if (!mIsLoading) {
            layoutId = R.layout.item_secondary_category
        }
        val itemView = LayoutInflater.from(mContext)
            .inflate(layoutId, parent, false)
        return if (mIsLoading) {
            ShimmerViewHolder(itemView)
        } else {
            SecondaryCategoryViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mIsLoading) {
            val viewHolder = holder as ShimmerViewHolder
            viewHolder.startShimmer()
        } else {
            val viewHolder = holder as SecondaryCategoryViewHolder
            viewHolder.bind(mCategoryList!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (mIsLoading) {
            mShimmerItemCount
        } else {
            if (mCategoryList == null) 0 else mCategoryList!!.size
        }
    }

    fun setList(categoryList: List<Category>?) {
        mCategoryList = categoryList
        notifyDataSetChanged()
    }

    fun setLoading(isLoading: Boolean) {
        mIsLoading = isLoading
    }

    fun setShimmerItemCount(shimmerItemCount: Int) {
        mShimmerItemCount = shimmerItemCount
    }

    internal inner class SecondaryCategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @JvmField
        @BindView(R.id.iv_picture)
        var mPictureImageView: ImageView? = null

        @JvmField
        @BindView(R.id.tv_name)
        var mNameTextView: TextView? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)

            // Setup OnClickListener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (mOnSecondaryCategoryClickListener != null) {
                // get selected item position
                val itemPosition = adapterPosition
                // get selected item
                val selectedSecondaryCategory = mCategoryList!![itemPosition]
                // Callback for the selected item
                mOnSecondaryCategoryClickListener
                    .onSecondaryCategoryClicked(selectedSecondaryCategory)
            }
        }

        fun bind(category: Category) {
            Picasso.get()
                .load(category.pictureUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(mPictureImageView)
            Log.v("SCA", category.pictureUrl)
            mNameTextView!!.text = category.name
        }
    }

    internal inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField
        @BindView(R.id.sfl_category)
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

    interface OnSecondaryCategoryClickListener {
        fun onSecondaryCategoryClicked(secondaryCategory: Category?)
    }

    companion object {
        private const val SHIMMER_ITEM_COUNT = 9
    }
}
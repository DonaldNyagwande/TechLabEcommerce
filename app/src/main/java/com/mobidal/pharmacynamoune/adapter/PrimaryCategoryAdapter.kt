package com.mobidal.pharmacynamoune.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.shimmer.ShimmerFrameLayout
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.adapter.SecondaryCategoryAdapter.OnSecondaryCategoryClickListener
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration
import com.mobidal.pharmacynamoune.model.Category

class PrimaryCategoryAdapter(
    private val mContext: Context, private var mCategoryList: List<Category>?,
    private val mOnPrimaryCategoryClickListener: OnPrimaryCategoryClickListener?,
    private val mOnSecondaryCategoryClickListener: OnSecondaryCategoryClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mIsLoading = true
    private var mShimmerItemCount = SHIMMER_ITEM_COUNT
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutId = R.layout.item_placeholder_primary_category
        if (!mIsLoading) {
            layoutId = R.layout.item_primary_category
        }
        val itemView = LayoutInflater.from(mContext)
            .inflate(layoutId, parent, false)
        return if (mIsLoading) {
            ShimmerViewHolder(itemView)
        } else {
            PrimaryCategoryViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mIsLoading) {
            val viewHolder = holder as ShimmerViewHolder
            viewHolder.startShimmer()
        } else {
            val viewHolder = holder as PrimaryCategoryViewHolder
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

    fun setList(list: List<Category>?) {
        mCategoryList = list
        notifyDataSetChanged()
    }

    fun setLoading(isLoading: Boolean) {
        mIsLoading = isLoading
    }

    fun setShimmerItemCount(shimmerItemCount: Int) {
        mShimmerItemCount = shimmerItemCount
    }

    internal inner class PrimaryCategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        @JvmField
        @BindView(R.id.l_view_all)
        var mViewAllView: View? = null

        @JvmField
        @BindView(R.id.tv_name)
        var mNameTextView: TextView? = null

        @JvmField
        @BindView(R.id.rv_category)
        var mCategoryRecyclerView: RecyclerView? = null
        var mCategoryAdapter: SecondaryCategoryAdapter? = null

        init {

            // Bind Views
            ButterKnife.bind(this, itemView)
        }

        fun bind(category: Category) {
            // Setup data
            mNameTextView!!.text = category.name

            // OnPrimaryCategoryClickListener
            mViewAllView!!.setOnClickListener {
                mOnPrimaryCategoryClickListener?.onPrimaryCategoryClicked(category)
            }

            //Setup Secondary Category
            setupSecondaryCategoryList()

            // Load Secondary Category
            loadSecondaryCategoryList()
        }

        private fun loadSecondaryCategoryList() {
            val category = mCategoryList!![adapterPosition]
            mCategoryAdapter!!.setLoading(false)
            mCategoryRecyclerView!!.adapter = null
            mCategoryRecyclerView!!.adapter = mCategoryAdapter
            mCategoryAdapter!!.setList(category.categoryList)
        }

        private fun setupSecondaryCategoryList() {
            // Create {@link GridLayoutManager} for the {@link SecondaryCategory} {@link RecyclerView}
            val secondaryCategoryLayoutManager = GridLayoutManager(mContext, 2)
            secondaryCategoryLayoutManager.orientation = LinearLayoutManager.VERTICAL
            mCategoryAdapter = SecondaryCategoryAdapter(mContext,
                null, mOnSecondaryCategoryClickListener)

            // Create {@link RecyclerView.ItemDecoration}
            val spacingInPixel = mContext.resources
                .getDimensionPixelSize(R.dimen.grid_secondary_category_spacing)
            val gridSpacingItemDecoration = GridSpacingItemDecoration(2, spacingInPixel, false)

            // Setup {link RecyclerView} for {@link SecondaryCategory}
            mCategoryRecyclerView!!.layoutManager = secondaryCategoryLayoutManager
            mCategoryRecyclerView!!.adapter = mCategoryAdapter
            mCategoryRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
        }
    }

    internal inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField
        @BindView(R.id.sfl_primary_category)
        var mCategoryShimmerFrameLayout: ShimmerFrameLayout? = null

        @JvmField
        @BindView(R.id.rv_category)
        var mCategoryRecyclerView: RecyclerView? = null
        var mCategoryAdapter: SecondaryCategoryAdapter? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)

            // Setup {@SecondaryCategory} list
            setupCategoryList()
        }

        private fun setupCategoryList() {
            // Create {@link GridLayoutManager} for the {@link SecondaryCategory} {@link RecyclerView}
            val secondaryCategoryLayoutManager = GridLayoutManager(mContext, 3)
            secondaryCategoryLayoutManager.orientation = LinearLayoutManager.VERTICAL
            mCategoryAdapter = SecondaryCategoryAdapter(mContext,
                null, mOnSecondaryCategoryClickListener)

            // Create {@link RecyclerView.ItemDecoration}
            val spacingInPixel = mContext.resources
                .getDimensionPixelSize(R.dimen.grid_secondary_category_spacing)
            val gridSpacingItemDecoration = GridSpacingItemDecoration(3, spacingInPixel, false)

            // Setup {link RecyclerView} for {@link SecondaryCategory}
            mCategoryRecyclerView!!.layoutManager = secondaryCategoryLayoutManager
            mCategoryRecyclerView!!.adapter = mCategoryAdapter
            mCategoryRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
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

    interface OnPrimaryCategoryClickListener {
        fun onPrimaryCategoryClicked(category: Category?)
    }

    companion object {
        private const val SHIMMER_ITEM_COUNT = 2
    }
}
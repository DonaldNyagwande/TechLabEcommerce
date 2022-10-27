package com.mobidal.pharmacynamoune.adapter

import android.content.Context
import com.mobidal.pharmacynamoune.model.IntroCategory
import com.mobidal.pharmacynamoune.adapter.IntroCategoryAdapter.OnViewAllClickListener
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProductClickListener
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.mobidal.pharmacynamoune.R
import android.view.LayoutInflater
import android.view.View
import com.mobidal.pharmacynamoune.adapter.IntroCategoryAdapter.IntroSecondaryCategoryViewHolder
import com.mobidal.pharmacynamoune.adapter.IntroCategoryAdapter
import butterknife.BindView
import android.widget.TextView
import com.mobidal.pharmacynamoune.adapter.ProductAdapter
import butterknife.ButterKnife
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.mobidal.pharmacynamoune.model.Product
import com.facebook.shimmer.ShimmerFrameLayout

class IntroCategoryAdapter(
    private val mContext: Context,
    private var mIntroCategoryList: List<IntroCategory>?,
    private val mOnViewAllClickListener: OnViewAllClickListener?,
    private val mOnProductClickListener: OnProductClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mIsLoading = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutId = R.layout.item_placeholder_intro_category
        if (!mIsLoading) {
            layoutId = R.layout.item_intro_category
        }
        val itemView = LayoutInflater.from(mContext)
            .inflate(layoutId, parent, false)
        return if (mIsLoading) {
            ShimmerViewHolder(itemView)
        } else {
            IntroSecondaryCategoryViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mIsLoading) {
            val viewHolder = holder as ShimmerViewHolder
            viewHolder.startShimmer()
        } else {
            val viewHolder = holder as IntroSecondaryCategoryViewHolder
            viewHolder.bind(mIntroCategoryList!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (mIsLoading) {
            SHIMMER_ITEM_COUNT
        } else {
            if (mIntroCategoryList == null) 0 else mIntroCategoryList!!.size
        }
    }

    fun setList(list: List<IntroCategory>?) {
        mIntroCategoryList = list
        notifyDataSetChanged()
    }

    fun setLoading(isLoading: Boolean) {
        mIsLoading = isLoading
    }

    internal inner class IntroSecondaryCategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), OnProductClickListener, View.OnClickListener {
        @JvmField
        @BindView(R.id.tv_name)
        var mNameTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_offer_text)
        var mOfferTextView: TextView? = null

        @JvmField
        @BindView(R.id.tv_view_all)
        var mViewAllView: View? = null

        @JvmField
        @BindView(R.id.rv_product)
        var mProductRecyclerView: RecyclerView? = null
        var mProductAdapter: ProductAdapter? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)

            // Setup {@Article} list
            setupProductList()
        }

        private fun setupProductList() {
            val gridLayoutManager = GridLayoutManager(mContext, 2)
            mProductAdapter = ProductAdapter(mContext, null, ProductAdapter.TYPE_ONE)
            mProductAdapter!!.setOnProductClickListener(this)
            mProductAdapter!!.setLoading(false)
            mProductRecyclerView!!.layoutManager = gridLayoutManager
            mProductRecyclerView!!.adapter = mProductAdapter
            mProductRecyclerView!!.addItemDecoration(
                DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL))
            mProductRecyclerView!!.addItemDecoration(
                DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        }

        fun bind(introCategory: IntroCategory) {
            val productList = introCategory.productList
            mProductAdapter!!.setAll(productList)
            mNameTextView!!.text = introCategory.name
            mOfferTextView!!.text = introCategory.offerText
            mViewAllView!!.setOnClickListener(this)
        }

        override fun onProductClicked(product: Product) {
            mOnProductClickListener?.onProductClicked(product)
        }

        override fun onClick(v: View) {
            mOnViewAllClickListener?.onViewAllClicked(
                mIntroCategoryList?.get(adapterPosition))
        }
    }

    internal inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField
        @BindView(R.id.sfl_category)
        var mCategoryShimmerFrameLayout: ShimmerFrameLayout? = null

        @JvmField
        @BindView(R.id.rv_product)
        var mArticleRecyclerView: RecyclerView? = null
        var mProductAdapter: ProductAdapter? = null

        init {

            // Bind views
            ButterKnife.bind(this, itemView)

            // Setup {@Article} list
            setupProductList()
        }

        private fun setupProductList() {
            val gridLayoutManager = GridLayoutManager(mContext, 2)
            mProductAdapter = ProductAdapter(mContext, null, ProductAdapter.TYPE_ONE)
            mArticleRecyclerView!!.layoutManager = gridLayoutManager
            mArticleRecyclerView!!.adapter = mProductAdapter
            mArticleRecyclerView!!.addItemDecoration(
                DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL))
            mArticleRecyclerView!!.addItemDecoration(
                DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
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

    interface OnViewAllClickListener {
        fun onViewAllClicked(category: IntroCategory?)
    }

    companion object {
        private const val SHIMMER_ITEM_COUNT = 2
    }
}
package com.mobidal.pharmacynamoune.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.ProductActivity
import com.mobidal.pharmacynamoune.ProductListActivity
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.adapter.IntroCategoryAdapter
import com.mobidal.pharmacynamoune.adapter.IntroCategoryAdapter.OnViewAllClickListener
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProductClickListener
import com.mobidal.pharmacynamoune.adapter.SecondaryCategoryAdapter
import com.mobidal.pharmacynamoune.adapter.SecondaryCategoryAdapter.OnSecondaryCategoryClickListener
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.db.entity.ProductEntity
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration
import com.mobidal.pharmacynamoune.model.Category
import com.mobidal.pharmacynamoune.model.IntroCategory
import com.mobidal.pharmacynamoune.model.Product

class HomeFragment(private val mContext: Context) : Fragment(), OnSecondaryCategoryClickListener,
    OnProductClickListener, OnViewAllClickListener {
    // Top Secondary Views
    @JvmField
    @BindView(R.id.rv_top_secondary_category)
    var mTopCategoryRecyclerView: RecyclerView? = null

    @JvmField
    @BindView(R.id.rv_intro_category)
    var mIntroCategoryRecyclerView: RecyclerView? = null
    var mTopCategoryAdapter: SecondaryCategoryAdapter? = null
    var mIntroCategoryAdapter: IntroCategoryAdapter? = null
    private var mDb: AppDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = LayoutInflater.from(mContext)
            .inflate(R.layout.fragment_home, container, false)

        // Bind HomeFragment views
        ButterKnife.bind(this, rootView)

        // Initials AppDatabase
        mDb = getInstance(requireActivity())

        // Setup Top {@link SecondaryCategory} RecyclerView
        setupTopCategory()
        // Load Top SecondaryCategory list
        loadTopCategoryList()

        // Setup {@link IntroCategory} RecyclerView
        setupIntroCategory()
        // Load {@link IntroCategory}
        loadIntroCategoryList()
        return rootView
    }

    private fun setupTopCategory() {
        // Create {@link GridLayoutManager} for the {@link Category} {@link RecyclerView}
        val topSecondaryCategoryLayoutManager = GridLayoutManager(mContext, 2)
        topSecondaryCategoryLayoutManager.orientation = LinearLayoutManager.VERTICAL

        // Create {@link SecondaryCategoryAdapter}
        mTopCategoryAdapter = SecondaryCategoryAdapter(mContext,
            null, this)

        // Create {@link RecyclerView.ItemDecoration}
        val spacingInPixel = resources.getDimensionPixelSize(R.dimen.grid_top_category_spacing)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(2, spacingInPixel, false)

        // Setup {link RecyclerView} for {@link Category}
        mTopCategoryRecyclerView!!.layoutManager = topSecondaryCategoryLayoutManager
        mTopCategoryRecyclerView!!.adapter = mTopCategoryAdapter
        mTopCategoryRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
    }

    private fun loadTopCategoryList() {
        val secondaryCategoryEntities = mDb!!.secondaryCategoryDao()!!
            .findTopCategories()
        val categories: MutableList<Category> = ArrayList()
        for (category in secondaryCategoryEntities!!) {
            categories.add(Category(category!!.id!!.toInt(), category.name,
                category.offerText, category.pictureUrl, null))
        }
        mTopCategoryAdapter!!.setList(categories)
        mTopCategoryAdapter!!.setLoading(false)
        mTopCategoryRecyclerView!!.adapter = null
        mTopCategoryRecyclerView!!.adapter = mTopCategoryAdapter
    }

    private fun setupIntroCategory() {
        val introSecondaryCategoryLayoutManager = LinearLayoutManager(
            mContext, LinearLayoutManager.VERTICAL, false)
        mIntroCategoryAdapter = IntroCategoryAdapter(mContext, null,
            this, this)

        // Create {@link RecyclerView.ItemDecoration}
        val spacingInPixel = resources.getDimensionPixelSize(R.dimen.grid_intro_category_spacing)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(1, spacingInPixel, false)
        mIntroCategoryRecyclerView!!.layoutManager = introSecondaryCategoryLayoutManager
        mIntroCategoryRecyclerView!!.adapter = mIntroCategoryAdapter
        mIntroCategoryRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
    }

    private fun loadIntroCategoryList() {
        val secondaryCategoryEntities = mDb!!.secondaryCategoryDao()!!
            .findTopCategories()
        val introCategories: MutableList<IntroCategory> = ArrayList()
        var productEntities: List<ProductEntity?>?
        for (category in secondaryCategoryEntities!!) {
            productEntities = mDb!!.productDao()!!.findFourBySecondaryCategoryId(category!!.id)
            val products: MutableList<Product> = ArrayList()
            for (productEntity in productEntities!!) {
                products.add(Product(productEntity!!.id, productEntity.mark,
                    productEntity.name, productEntity.description,
                    productEntity.price, productEntity.pictureUrl,
                    null, 120, null))
            }
            introCategories.add(IntroCategory(category.id!!.toInt(), category.name,
                category.offerText, products))
        }
        mIntroCategoryAdapter!!.setList(introCategories)
        mIntroCategoryAdapter!!.setLoading(false)
        mIntroCategoryRecyclerView!!.adapter = null
        mIntroCategoryRecyclerView!!.adapter = mIntroCategoryAdapter
    }

    override fun onSecondaryCategoryClicked(category: Category?) {
        val intent = Intent(mContext, ProductListActivity::class.java)
        intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category!!.id.toLong())
        startActivity(intent)
    }

    override fun onProductClicked(product: Product?) {
        val intent = Intent(mContext, ProductActivity::class.java)
        intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, product!!.id.toLong())
        startActivity(intent)
    }

    override fun onViewAllClicked(category: IntroCategory?) {
        val intent = Intent(mContext, ProductListActivity::class.java)
        intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category!!.id.toLong())
        startActivity(intent)
    }
}
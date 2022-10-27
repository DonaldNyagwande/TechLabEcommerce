package com.mobidal.pharmacynamoune.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.ProductListActivity
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.adapter.PrimaryCategoryAdapter
import com.mobidal.pharmacynamoune.adapter.PrimaryCategoryAdapter.OnPrimaryCategoryClickListener
import com.mobidal.pharmacynamoune.adapter.SecondaryCategoryAdapter.OnSecondaryCategoryClickListener
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration
import com.mobidal.pharmacynamoune.model.Category

class CategoryFragment(private val mContext: Context) : Fragment(), OnPrimaryCategoryClickListener,
    OnSecondaryCategoryClickListener {
    @JvmField
    @BindView(R.id.rv_category)
    var mCategoryRecyclerView: RecyclerView? = null
    private var mCategoryAdapter: PrimaryCategoryAdapter? = null
    private var mDb: AppDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = LayoutInflater.from(mContext)
            .inflate(R.layout.fragment_category, container, false)

        // Bind HomeFragment views
        ButterKnife.bind(this, rootView)

        // Initials AppDatabase
        mDb = getInstance(requireActivity())

        // Setup Primary Category list
        setupCategoryList()

        // Load Primary Category
        loadCategoryList()
        return rootView
    }

    private fun setupCategoryList() {
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mCategoryAdapter = PrimaryCategoryAdapter(mContext, null,
            this, this)

        // Create {@link RecyclerView.ItemDecoration}
        val spacingInPixel = mContext.resources
            .getDimensionPixelSize(R.dimen.grid_primary_category_spacing)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(1, spacingInPixel, true)
        mCategoryRecyclerView!!.layoutManager = layoutManager
        mCategoryRecyclerView!!.adapter = mCategoryAdapter
        mCategoryRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
    }

    private fun loadCategoryList() {
        val primaryCategoryEntities = mDb!!.primaryCategoryDao()!!
            .findAll()
        val primaryCategories: MutableList<Category> = ArrayList()
        for (primaryCategoryEntity in primaryCategoryEntities!!) {
            val secondaryCategoryEntities = mDb!!.secondaryCategoryDao()?.findAllByPrimaryCategory(primaryCategoryEntity!!.id!!.toInt())
            val secondaryCategories: MutableList<Category> = ArrayList()
            for (category in secondaryCategoryEntities!!) {
                secondaryCategories.add(Category(category!!.id!!.toInt(), category.name,
                    category.offerText, category.pictureUrl, null))
            }
            if (primaryCategoryEntity != null) {
                primaryCategories.add(Category(primaryCategoryEntity.id!!.toInt(),
                    primaryCategoryEntity.name,
                    "", primaryCategoryEntity.pictureUrl, secondaryCategories))
            }
        }
        mCategoryAdapter!!.setList(primaryCategories)
        mCategoryAdapter!!.setLoading(false)
        mCategoryRecyclerView!!.adapter = null
        mCategoryRecyclerView!!.adapter = mCategoryAdapter
    }

    override fun onPrimaryCategoryClicked(category: Category?) {
        val intent = Intent(mContext, ProductListActivity::class.java)
        intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category!!.id.toLong())
        startActivity(intent)
    }

    override fun onSecondaryCategoryClicked(category: Category?) {
        val intent = Intent(mContext, ProductListActivity::class.java)
        intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category!!.id.toLong())
        startActivity(intent)
    }
}
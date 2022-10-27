package com.mobidal.pharmacynamoune

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.ProductActivity
import com.mobidal.pharmacynamoune.adapter.ProductAdapter
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProductClickListener
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.db.entity.RecentlySearchedEntity
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration
import com.mobidal.pharmacynamoune.model.Product
import com.mobidal.pharmacynamoune.profile.ShoppingBasketActivity

class ProductListActivity : AppCompatActivity(), OnProductClickListener {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.v_main)
    var mMainView: View? = null

    @JvmField
    @BindView(R.id.rv_product)
    var mProductRecyclerView: RecyclerView? = null
    var mProductAdapter: ProductAdapter? = null
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        // Bind Views
        ButterKnife.bind(this)

        // Initials AppDatabase
        mDb = getInstance(this)

        // setup the {@link Toolbar}
        setupToolbar()

        // setup {@Product} list
        setupProductList()
        // Load {@Product} list
        loadProductList(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_shopping_basket -> {
                shoppingBasketAction()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shoppingBasketAction() {
        val intent = Intent(this, ShoppingBasketActivity::class.java)
        startActivity(intent)
    }

    private fun setupToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setTitle(R.string.product_list)
    }

    private fun setupProductList() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        mProductAdapter = ProductAdapter(this, null, ProductAdapter.TYPE_ONE)
        mProductAdapter!!.setOnProductClickListener(this)
        mProductAdapter!!.setShimmerItemCount(8)

        // Create {@link RecyclerView.ItemDecoration}
        val spacingInPixel = resources.getDimensionPixelSize(R.dimen.grid_product_spacing)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(2, spacingInPixel, false)
        mProductRecyclerView!!.layoutManager = gridLayoutManager
        mProductRecyclerView!!.adapter = mProductAdapter
        mProductRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
    }

    private fun loadProductList(intent: Intent?) {
        if (intent != null) {
            if (intent.hasExtra(EXTRA_CATEGORY_ID)) {
                val categoryId = intent.getLongExtra(EXTRA_CATEGORY_ID, 0)
                val productEntities = mDb!!.productDao()!!
                    .findAllBySecondaryCategoryId(categoryId)
                val products: MutableList<Product> = ArrayList()
                for (productEntity in productEntities!!) {
                    products.add(Product(productEntity!!.id, productEntity.mark!!,
                        productEntity.name!!, productEntity.description!!,
                        productEntity.price, productEntity.pictureUrl!!,
                        null, 120, null))
                }
                mProductAdapter!!.setLoading(false)
                mProductAdapter!!.setAll(products)
                mProductRecyclerView!!.adapter = null
                mProductRecyclerView!!.adapter = mProductAdapter
            } else if (intent.hasExtra(EXTRA_SEARCH_QUERY)) {
                val query = intent.getStringExtra(EXTRA_SEARCH_QUERY)
                val productEntities = mDb!!.productDao()!!
                    .query(query)
                val products: MutableList<Product> = ArrayList()
                for (productEntity in productEntities!!) {
                    products.add(Product(productEntity!!.id, productEntity.mark!!,
                        productEntity.name!!, productEntity.description!!,
                        productEntity.price, productEntity.pictureUrl!!,
                        null, 120, null))
                }
                mProductAdapter!!.setLoading(false)
                mProductAdapter!!.setAll(products)
                mProductRecyclerView!!.adapter = null
                mProductRecyclerView!!.adapter = mProductAdapter
                val userId = getSharedPreferences("user", MODE_PRIVATE)
                    .getLong("user_id", 0)
                val recentlySearchedEntity = RecentlySearchedEntity()
                recentlySearchedEntity.userId = userId.toInt()
                recentlySearchedEntity.text = query
                mDb!!.recentlySearchedDao()!!.insert(recentlySearchedEntity)
            }
        }
    }

    override fun onProductClicked(product: Product?) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, product!!.id.toLong())
        startActivity(intent)
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "extra_category_id"
        const val EXTRA_SEARCH_QUERY = "extra_search_query"
    }
}
package com.mobidal.pharmacynamoune.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.ProductActivity
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.adapter.ProductAdapter
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProduct3ClickListener
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.db.entity.ShoppingBasketEntity
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration
import com.mobidal.pharmacynamoune.model.Product
import com.mobidal.pharmacynamoune.model.Product.Pivot

class RecentlyViewedActivity : AppCompatActivity(), OnProduct3ClickListener {
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
        setContentView(R.layout.activity_recently_viewed)

        // Bind Views
        ButterKnife.bind(this)

        // Initials AppDatabase
        mDb = getInstance(applicationContext)

        // setup the {@link Toolbar}
        setupToolbar()

        // setup {@Product} list
        setupProductList()
        // Load {@Product} list
        loadProductList()
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

    private fun setupProductList() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        // Create {@link RecyclerView.ItemDecoration}
        val spacingInPixel = resources.getDimensionPixelSize(R.dimen.grid_product_spacing)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(1, spacingInPixel, false)
        mProductAdapter = ProductAdapter(this, null, ProductAdapter.TYPE_THREE)
        mProductAdapter!!.setOnProduct3ClickListener(this)
        mProductAdapter!!.setShimmerItemCount(5)
        mProductRecyclerView!!.layoutManager = gridLayoutManager
        mProductRecyclerView!!.adapter = mProductAdapter
        mProductRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
    }

    private fun loadProductList() {
        // TODO load product list
        val pivot = Pivot(false, 1)
        val productList: MutableList<Product> = ArrayList()
        val spList = mDb!!.recentlyViewedDao()!!
            .findAll()
        for (sp in spList!!) {
            val pe = mDb!!.productDao()!!
                .find(Integer.toUnsignedLong(sp!!.productId))
            val prod = Product(pe!!.id,
                pe.mark!!,
                pe.name!!,
                pe.description!!,
                pe.price,
                pe.pictureUrl!!,
                null,
                pe.viewNumber,
                null)
            productList.add(prod)
        }
        mProductRecyclerView!!.adapter = null
        mProductAdapter!!.setLoading(false)
        mProductAdapter!!.setAll(productList)
        mProductRecyclerView!!.adapter = mProductAdapter
    }

    private fun setupToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = getString(R.string.recently_viewed)
    }

    override fun onProductClicked(product: Product?) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, product!!.id.toLong())
        startActivity(intent)
    }

    override fun onPurchaseClicked(product: Product?) {
        val userId = getSharedPreferences("user", MODE_PRIVATE).getLong("user_id", 0)
        val shoppingBasketEntity = ShoppingBasketEntity()
        shoppingBasketEntity.userId = userId
        shoppingBasketEntity.productId = product!!.id.toLong()
        shoppingBasketEntity.quantity = 1L
        mDb!!.shoppingBasketDao()!!.insert(shoppingBasketEntity)
        Toast.makeText(this,
            getString(R.string.the_product_has_been_purchased), Toast.LENGTH_SHORT).show()
    }
}
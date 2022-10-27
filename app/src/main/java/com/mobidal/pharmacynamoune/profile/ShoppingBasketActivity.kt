package com.mobidal.pharmacynamoune.profile

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.ProductActivity
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.adapter.ProductAdapter
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProduct4ClickListener
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.db.entity.CommandEntity
import com.mobidal.pharmacynamoune.db.entity.CommandProductsEntity
import com.mobidal.pharmacynamoune.db.entity.ProductEntity
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration
import com.mobidal.pharmacynamoune.model.Product
import com.mobidal.pharmacynamoune.model.Product.Pivot

class ShoppingBasketActivity : AppCompatActivity(), OnProduct4ClickListener {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.v_main)
    var mMainView: View? = null

    @JvmField
    @BindView(R.id.rv_product)
    var mProductRecyclerView: RecyclerView? = null

    @JvmField
    @BindView(R.id.l_command)
    var mCommandView: View? = null

    @JvmField
    @BindView(R.id.b_command)
    var mCommandButton: Button? = null
    var mProductAdapter: ProductAdapter? = null
    var mProducts: MutableList<Product>? = null
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_basket)

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

        // Setup command {@ Button}
        setupCommand()
    }

    private fun setupCommand() {
        val userId = getSharedPreferences("user", MODE_PRIVATE).getLong("user_id", 0)
        val shoppingBasketEntities = mDb!!.shoppingBasketDao()!!
            .findAllByUser(userId)
        if (shoppingBasketEntities!!.isEmpty()) mCommandView!!.visibility = View.GONE else {
            mCommandView!!.visibility = View.VISIBLE
        }
        var totalPrice = 0
        for (p in mProducts!!) {
            totalPrice += p.price * p.pivot!!.quantity
        }
        mCommandButton!!.text = "Command [ $$totalPrice ]"
        mCommandButton!!.setOnClickListener {
            val commandEntity = CommandEntity()
            commandEntity.userId = userId.toInt()
            commandEntity.deliveryPrice = 20
            commandEntity.state = "Ordered"
            val cId = mDb!!.commandDao()!!.insert(commandEntity)
            val shoppingBasketEntities = mDb!!.shoppingBasketDao()!!
                .findAllByUser(userId)
            mProducts = ArrayList()
            var productEntity: ProductEntity?
            var pivot: Pivot
            for (shoppingBasketEntity in shoppingBasketEntities!!) {
                productEntity = mDb!!.productDao()!!.find(shoppingBasketEntity!!.productId)
                pivot = Pivot(false, shoppingBasketEntity.quantity!!.toInt())
                (mProducts as ArrayList<Product>).add(Product(productEntity!!.id, productEntity.mark!!,
                    productEntity.name!!, productEntity.description!!,
                    productEntity.price, productEntity.pictureUrl!!,
                    null, 120, pivot))
            }
            var commandProductsEntity: CommandProductsEntity
            for (p in mProducts as ArrayList<Product>) {
                commandProductsEntity = CommandProductsEntity()
                commandProductsEntity.commandId = cId.toInt()
                commandProductsEntity.productId = p.id
                mDb!!.commandProductsDao()!!.insert(commandProductsEntity)
            }
            for (s in shoppingBasketEntities) {
                mDb!!.shoppingBasketDao()!!.delete(s)
            }
            val intent = Intent(this@ShoppingBasketActivity, CommandActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupProductList() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        // Create {@link RecyclerView.ItemDecoration}
        val spacingInPixel = resources.getDimensionPixelSize(R.dimen.grid_product_spacing)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(1, spacingInPixel, false)
        mProductAdapter = ProductAdapter(this, null, ProductAdapter.TYPE_FOUR)
        mProductAdapter!!.setOnProduct4ClickListener(this)
        mProductAdapter!!.setShimmerItemCount(5)
        mProductRecyclerView!!.layoutManager = gridLayoutManager
        mProductRecyclerView!!.adapter = mProductAdapter
        mProductRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
    }

    private fun loadProductList() {
        val userId = getSharedPreferences("user", MODE_PRIVATE).getLong("user_id", 0)
        val shoppingBasketEntities = mDb!!.shoppingBasketDao()!!
            .findAllByUser(userId)
        mProducts = ArrayList()
        var productEntity: ProductEntity?
        var pivot: Pivot
        for (shoppingBasketEntity in shoppingBasketEntities!!) {
            productEntity = mDb!!.productDao()!!.find(shoppingBasketEntity!!.productId)
            pivot = Pivot(false, shoppingBasketEntity.quantity!!.toInt())
            (mProducts as ArrayList<Product>).add(Product(productEntity!!.id, productEntity.mark!!,
                productEntity.name!!, productEntity.description!!,
                productEntity.price, productEntity.pictureUrl!!,
                null, 120, pivot))
        }
        mProductRecyclerView!!.adapter = null
        mProductAdapter!!.setLoading(false)
        mProductAdapter!!.setAll(mProducts)
        mProductRecyclerView!!.adapter = mProductAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Shopping Basket"
    }

    override fun onProductClicked(product: Product?) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra(ProductActivity.EXTRA_PRODUCT_ID, product!!.id)
        startActivity(intent)
    }

    override fun onDeleteClicked(product: Product?, position: Int) {
        val userId = getSharedPreferences("user", MODE_PRIVATE).getLong("user_id", 0)
        val shoppingBasketEntity = mDb!!.shoppingBasketDao()!!
            .find(userId, product!!.id.toLong())
        mDb!!.shoppingBasketDao()!!.delete(shoppingBasketEntity)
        mProductAdapter!!.remove(position)
        mProductAdapter!!.notifyItemChanged(position)
    }

    override fun onDecrementClicked(product: Product?, position: Int) {
        if (product!!.pivot!!.quantity > 1) {
            val userId = getSharedPreferences("user", MODE_PRIVATE).getLong("user_id", 0)
            val shoppingBasketEntity = mDb!!.shoppingBasketDao()!!
                .find(userId, product.id.toLong())
            shoppingBasketEntity!!.quantity = shoppingBasketEntity.quantity!! - 1
            mDb!!.shoppingBasketDao()!!.update(shoppingBasketEntity)
            product.pivot!!.decrementQuantity()
            mProductAdapter!!.notifyItemChanged(position)
            var totalPrice = 0
            for (p in mProducts!!) {
                totalPrice += p.price * p.pivot!!.quantity
            }
            mCommandButton!!.text = "Command [ $$totalPrice ]"
        }
    }

    override fun onIncrementClicked(product: Product?, position: Int) {
        val userId = getSharedPreferences("user", MODE_PRIVATE).getLong("user_id", 0)
        val shoppingBasketEntity = mDb!!.shoppingBasketDao()!!
            .find(userId, product!!.id.toLong())
        shoppingBasketEntity!!.quantity = shoppingBasketEntity.quantity!! + 1
        mDb!!.shoppingBasketDao()!!.update(shoppingBasketEntity)
        product.pivot!!.incrementQuantity()
        mProductAdapter!!.notifyItemChanged(position)
        var totalPrice = 0
        for (p in mProducts!!) {
            totalPrice += p.price * p.pivot!!.quantity
        }
        mCommandButton!!.text = "Command [ $$totalPrice ]"
    }
}
package com.mobidal.pharmacynamoune

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mobidal.pharmacynamoune.adapter.PictureAdapter
import com.mobidal.pharmacynamoune.adapter.PictureAdapter.OnPictureClickListener
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.db.entity.RecentlyViewedEntity
import com.mobidal.pharmacynamoune.db.entity.SavedProductEntity
import com.mobidal.pharmacynamoune.db.entity.ShoppingBasketEntity
import com.mobidal.pharmacynamoune.model.Picture
import com.mobidal.pharmacynamoune.model.Product
import com.mobidal.pharmacynamoune.model.Product.Pivot
import com.mobidal.pharmacynamoune.profile.ShoppingBasketActivity

class ProductActivity : AppCompatActivity(), OnPictureClickListener {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.l_main)
    var mMainView: View? = null

    @JvmField
    @BindView(R.id.sfl)
    var mShimmerFrameLayout: ShimmerFrameLayout? = null

    @JvmField
    @BindView(R.id.vp2_picture)
    var mPictureViewPager2: ViewPager2? = null

    @JvmField
    @BindView(R.id.tl_picture_indicator)
    var mPictureIndicatorTabLayout: TabLayout? = null

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
    @BindView(R.id.tv_view_number)
    var mViewNumberTextView: TextView? = null

    @JvmField
    @BindView(R.id.iv_save)
    var mSaveImageView: ImageView? = null

    @JvmField
    @BindView(R.id.tv_description)
    var mDescriptionTextView: TextView? = null

    @JvmField
    @BindView(R.id.b_purchase)
    var mPurchaseButton: Button? = null
    var mProduct: Product? = null
    var mPictureAdapter: PictureAdapter? = null
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // Bind HomeFragment views
        ButterKnife.bind(this)

        // Initials AppDatabase
        mDb = getInstance(this)

        // setup the {@link Toolbar}
        setupToolbar()

        // setup {@link Product}
        setupProduct()
        // Load {@link Article}
        loadProduct(intent)
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
        supportActionBar!!.title = "Product Details"
    }

    private fun setupProduct() {
        // Start Shimmer
        startShimmer()

        // Setup {@link Picture} horizontal {@link RecyclerView}
        setupPictureView()

        // Setup purchase {@link Product}
        setupPurchaseProduct()

        // Setup save {@link Product}
        setupSaveProduct()
    }

    private fun startShimmer() {
        mMainView!!.visibility = View.GONE
        mShimmerFrameLayout!!.visibility = View.VISIBLE
        mShimmerFrameLayout!!.startShimmer()
    }

    private fun setupPictureView() {
        // Create {@link LinearLayoutManager} for the {@link Picture} {@link RecyclerView}
        val topArticleLayoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        // Create {@link PictureAdapter}
        mPictureAdapter = PictureAdapter(this, null, this)

        // Setup {@link Picture} {@link RecyclerView} {@link Adapter}
        mPictureViewPager2!!.adapter = mPictureAdapter

        // Setup {@link TabLayout} with {@link ViewPager2}
        TabLayoutMediator(mPictureIndicatorTabLayout!!, mPictureViewPager2!!) { tab, position ->
            // This method do nothing
        }.attach()
        val transformer = ViewPager2.PageTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.alpha = 0.25f + r
            page.scaleY = 0.75f + r * 0.25f
        }
        mPictureViewPager2!!.setPageTransformer(transformer)
    }

    private fun setupPurchaseProduct() {
        // TODO purchase product
        mPurchaseButton!!.setOnClickListener {
            mPurchaseButton!!.isEnabled = false
            val userId = getSharedPreferences("user", MODE_PRIVATE).getLong("user_id", 0)
            val shoppingBasketEntity = ShoppingBasketEntity()
            shoppingBasketEntity.userId = userId
            shoppingBasketEntity.productId = mProduct!!.id.toLong()
            shoppingBasketEntity.quantity = 1L
            mDb!!.shoppingBasketDao()!!.insert(shoppingBasketEntity)
            val stateMessage = getString(R.string.your_purchase_has_been_successfully_completed)
            Toast.makeText(this@ProductActivity, stateMessage,
                Toast.LENGTH_SHORT).show()
            mPurchaseButton!!.isEnabled = true
        }
    }

    //    private void setupSaveProduct() {
    //        mSaveImageView.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View view) {
    //                mSaveImageView.setEnabled(false);
    //                String stateMessage = "";
    //
    //                if (mProduct.getPivot().isSaved()) {
    //                    // Unsave product
    //                    mProduct.getPivot().setSaved(false);
    //                    mSaveImageView.setImageResource(R.drawable.ic_favorite_border);
    //                    stateMessage = getString(R.string.product_saved);
    //
    //                    Long userId =
    //                            getSharedPreferences("user", Context.MODE_PRIVATE)
    //                                    .getLong("user_id", 0);
    //
    //                    SavedProductEntity savedProductEntity =
    //                            mDb.savedProductDao()
    //                                    .findByUserIdAndProductId(userId, (long) mProduct.getId());
    //
    //                    if (savedProductEntity != null) {
    //                        mDb.savedProductDao().delete(savedProductEntity);
    //                    }
    //                } else {
    //                    // Save product
    //                    mProduct.getPivot().setSaved(true);
    //                    mSaveImageView.setImageResource(R.drawable.ic_favorite);
    //                    stateMessage = getString(R.string.product_unsaved);
    //
    //                    Long userId =
    //                            getSharedPreferences("user", Context.MODE_PRIVATE)
    //                                    .getLong("user_id", 0);
    //
    //                    SavedProductEntity savedProductEntity = new SavedProductEntity();
    //                    savedProductEntity.setUserId(userId.intValue());
    //                    savedProductEntity.setProductId(mProduct.getId());
    //
    //                    mDb.savedProductDao().insert(savedProductEntity);
    //
    //                }
    //
    //                Toast.makeText(ProductActivity.this, stateMessage,
    //                        Toast.LENGTH_SHORT).show();
    //
    //                mSaveImageView.setEnabled(true);
    //            }
    //        });
    //    }
    private fun setupSaveProduct() {
        // TODO save/unsave product
        mSaveImageView!!.setOnClickListener {
            mSaveImageView!!.isEnabled = false
            val stateMessage = ""
            if (mProduct!!.pivot!!.isSaved) {
                // Unsave product
                mProduct!!.pivot!!.isSaved = false
                mSaveImageView!!.setImageResource(R.drawable.ic_favorite_border)
                val userId = getSharedPreferences("user", MODE_PRIVATE)
                    .getLong("user_id", 0)
                val savedProductEntity = mDb!!.savedProductDao()
                    ?.findByUserIdAndProductId(userId, mProduct!!.id.toLong())
                mDb!!.savedProductDao()!!.delete(savedProductEntity)
            } else {
                // Save product
                mProduct!!.pivot!!.isSaved = true
                mSaveImageView!!.setImageResource(R.drawable.ic_favorite)
                // Save product
                mProduct!!.pivot!!.isSaved = true
                mSaveImageView!!.setImageResource(R.drawable.ic_favorite)
                val userId = getSharedPreferences("user", MODE_PRIVATE)
                    .getLong("user_id", 0)
                val savedProductEntity = SavedProductEntity()
                savedProductEntity.userId = userId.toInt()
                savedProductEntity.productId = mProduct!!.id
                mDb!!.savedProductDao()!!.insert(savedProductEntity)
            }
            mSaveImageView!!.isEnabled = true
        }
    }

    private fun loadProduct(intent: Intent?) {
        // TODO load product
        if (intent != null && intent.hasExtra(EXTRA_PRODUCT_ID)) {
            val productId = intent.getLongExtra(EXTRA_PRODUCT_ID, 0)
            val userId = getSharedPreferences("user", MODE_PRIVATE)
                .getLong("user_id", 0)
            val savedProductEntity = mDb!!.savedProductDao()
                ?.findByUserIdAndProductId(userId, productId)
            val productEntity = mDb!!.productDao()!!.find(productId)
            val pictureEntities = mDb!!.pictureDao()!!.findAllByProductId(productId)
            val pictureList: MutableList<Picture> = ArrayList()
            for (pictureEntity in pictureEntities!!) {
                pictureList.add(Picture(pictureEntity!!.id!!.toInt(), pictureEntity.pictureUrl!!))
            }
            val pivot: Pivot
            pivot = if (savedProductEntity != null) Pivot(true, 1) else Pivot(false, 1)
            mProduct = Product(productEntity!!.id, productEntity.mark!!,
                productEntity.name!!, productEntity.description!!,
                productEntity.price, productEntity.pictureUrl!!,
                pictureList, 120, pivot)
            if (mProduct!!.pivot!!.isSaved) {
                mSaveImageView!!.setImageResource(R.drawable.ic_favorite)
            } else {
                mSaveImageView!!.setImageResource(R.drawable.ic_favorite_border)
            }
            mPictureAdapter!!.setList(mProduct!!.pictureList)
            mMarkTextView!!.text = mProduct!!.mark
            mNameTextView!!.text = mProduct!!.name
            mPriceTextView!!.text = getString(R.string.price, mProduct!!.price)
            mViewNumberTextView!!.text = getString(R.string.views_number, mProduct!!.viewNumber)
            mDescriptionTextView!!.text = mProduct!!.description
            mShimmerFrameLayout!!.stopShimmer()
            mShimmerFrameLayout!!.visibility = View.GONE
            mMainView!!.visibility = View.VISIBLE
            val recentlyViewedEntity = RecentlyViewedEntity()
            recentlyViewedEntity.userId = userId.toInt()
            recentlyViewedEntity.productId = mProduct!!.id
            mDb!!.recentlyViewedDao()!!.insert(recentlyViewedEntity)
        }
    }

    override fun onPictureClicked(picture: Picture?) {}

    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
    }
}
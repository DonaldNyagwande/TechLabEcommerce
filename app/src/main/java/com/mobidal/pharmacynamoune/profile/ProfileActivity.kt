package com.mobidal.pharmacynamoune.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.R

class ProfileActivity : AppCompatActivity() {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.tv_welcome)
    var mWelcomeTextView: TextView? = null

    @JvmField
    @BindView(R.id.tv_welcome_description)
    var mWelcomeDescriptionTextView: TextView? = null

    @JvmField
    @BindView(R.id.b_connect)
    var mConnectButton: Button? = null

    @JvmField
    @BindView(R.id.ll_my_commands)
    var mMyCommandsView: View? = null

    @JvmField
    @BindView(R.id.ll_saved_products)
    var mSavedProductsView: View? = null

    @JvmField
    @BindView(R.id.ll_recently_viewed)
    var mRecentlyViewedView: View? = null

    @JvmField
    @BindView(R.id.ll_recently_searched)
    var mRecentSearchedView: View? = null

    @JvmField
    @BindView(R.id.ll_details)
    var mDetailsView: View? = null

    @JvmField
    @BindView(R.id.ll_address_book)
    var mAddressBookView: View? = null

    @JvmField
    @BindView(R.id.ll_change_password)
    var mChangePasswordView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Bind views
        ButterKnife.bind(this)

        // setup the {@link Toolbar}
        setupToolbar()

        // Setup profile
        setupProfile()
        val sp = getSharedPreferences("user", MODE_PRIVATE)
        val userId = sp.getLong("user_id", 0)
        if (userId > 0) {
            mConnectButton!!.visibility = View.INVISIBLE
            mWelcomeDescriptionTextView!!.text = sp.getString("username", "")
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Profile"
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

    private fun setupProfile() {
        mConnectButton!!.setOnClickListener { }
        mMyCommandsView!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, CommandActivity::class.java)
            startActivity(intent)
        }
        mSavedProductsView!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, FavoriteActivity::class.java)
            startActivity(intent)
        }
        mRecentlyViewedView!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, RecentlyViewedActivity::class.java)
            startActivity(intent)
        }
        mRecentSearchedView!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, RecentSearchedActivity::class.java)
            startActivity(intent)
        }
        mDetailsView!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, AccountActivity::class.java)
            startActivity(intent)
        }
        mAddressBookView!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, AddressActivity::class.java)
            startActivity(intent)
        }
        mChangePasswordView!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }
}
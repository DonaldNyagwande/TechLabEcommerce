package com.mobidal.pharmacynamoune

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mobidal.pharmacynamoune.adapter.MainFragmentAdapter
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.fragment.CategoryFragment
import com.mobidal.pharmacynamoune.fragment.HomeFragment
import com.mobidal.pharmacynamoune.profile.ProfileActivity
import com.mobidal.pharmacynamoune.profile.ShoppingBasketActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.dl_main)
    var mDrawerLayout: DrawerLayout? = null

    @JvmField
    @BindView(R.id.nv_main)
    var mNavigationView: NavigationView? = null

    @JvmField
    @BindView(R.id.vp_fragment)
    var mFragmentViewPager: ViewPager2? = null

    @JvmField
    @BindView(R.id.tl_main)
    var mFragmentTabLayout: TabLayout? = null
    private var mProfileView: View? = null
    private var mPictureImageView: ImageView? = null
    private var mNameTextView: TextView? = null
    private var mScoreTextView: TextView? = null
    private var mPhoneTextView: TextView? = null
    private var mEmailTextView: TextView? = null
    private var mLocationTextView: TextView? = null
    private var mConnectButton: Button? = null
    var mFragmentAdapter: MainFragmentAdapter? = null
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind Views
        ButterKnife.bind(this)

        // Initials AppDatabase
        mDb = getInstance(applicationContext)

        // setup the {@link Toolbar}
        setSupportActionBar(mToolbar)

        // Setup Navigation Drawer
        setupNavigationDrawer()

        // Setup {@link ViewPager}
        setupFragmentViewPager()
    }

    private fun setupNavigationDrawer() {
        val actionBarDrawerToggle = ActionBarDrawerToggle(this,
            mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer)
        mDrawerLayout!!.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // Setup Navigation Drawer Click Listener
        mNavigationView!!.setNavigationItemSelectedListener(this)

        // Bind views
        val headerView = mNavigationView!!.getHeaderView(0)
        mProfileView = headerView.findViewById(R.id.g_profile) as View
        mPictureImageView = headerView.findViewById<View>(R.id.iv_picture) as ImageView
        mNameTextView = headerView.findViewById<View>(R.id.tv_name) as TextView
        mScoreTextView = headerView.findViewById<View>(R.id.tv_score) as TextView
        mEmailTextView = headerView.findViewById<View>(R.id.tv_email) as TextView
        mPhoneTextView = headerView.findViewById<View>(R.id.tv_phone) as TextView
        mLocationTextView = headerView.findViewById<View>(R.id.tv_location) as TextView
        mConnectButton = headerView.findViewById<View>(R.id.b_connect) as Button
        mConnectButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
        }
        setupNavigationDrawerData()
    }

    private fun setupNavigationDrawerData() {
        val sp = getSharedPreferences("user", MODE_PRIVATE)
        val user = mDb!!.userDao()!!.find(sp.getLong("user_id", 0))
        if (user != null) {
            mConnectButton!!.visibility = View.GONE
            mProfileView!!.visibility = View.VISIBLE
            mNameTextView!!.text = user.fullName
            mEmailTextView!!.text = user.username
            mPhoneTextView!!.text = user.phoneNumber
        } else {
            mConnectButton!!.visibility = View.VISIBLE
            mProfileView!!.visibility = View.GONE
        }
    }

    private fun setupFragmentViewPager() {
        // Create {@link MainFragmentAdapter} for {@link Fragment} {@link ViewPager2}
        mFragmentAdapter = MainFragmentAdapter(this)

        // Add {@link Fragment}s
        mFragmentAdapter!!.addFragment(HomeFragment(this),
            getString(R.string.tab_home),
            R.drawable.ic_home)
        mFragmentAdapter!!.addFragment(CategoryFragment(this),
            getString(R.string.tab_category),
            R.drawable.ic_category)

        // Setup {@link Fragment} {@link ViewPager} with {@link Adapter}
        mFragmentViewPager!!.adapter = mFragmentAdapter

        // Disable swiping
        mFragmentViewPager!!.isUserInputEnabled = false

        // Setup {@link TabLayout} with {@link ViewPager}
        TabLayoutMediator(mFragmentTabLayout!!, mFragmentViewPager!!) { tab, position ->
            tab.setIcon(mFragmentAdapter!!.getIconResourceId(position))
            tab.text = mFragmentAdapter!!.getName(position)
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)

        // Set on search expand listener
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                return true
            }
        })

        // Set on query text listener
        val searchView = searchMenuItem.actionView as SearchView?
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity,
                    "Submit: $query", Toast.LENGTH_SHORT).show()
                searchMenuItem.collapseActionView()
                val intent = Intent(this@MainActivity, ProductListActivity::class.java)
                intent.putExtra(ProductListActivity.EXTRA_SEARCH_QUERY, query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.nav_profile -> {
                val profileIntent = Intent(this, ProfileActivity::class.java)
                startActivity(profileIntent)
            }
            R.id.nav_about -> {
                val aboutIntent = Intent(this, AboutActivity::class.java)
                startActivity(aboutIntent)
            }
            R.id.nav_share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download app https://www.walmart.com")
                sendIntent.type = "text/plain"
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            R.id.nav_contact -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:") // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("contact@walmart.com"))
                startActivity(intent)
            }
        }
        mDrawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }
}
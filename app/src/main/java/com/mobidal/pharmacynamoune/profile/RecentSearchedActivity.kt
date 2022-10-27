package com.mobidal.pharmacynamoune.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.ProductListActivity
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.adapter.SearchAdapter
import com.mobidal.pharmacynamoune.adapter.SearchAdapter.OnSearchClickListener
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.model.Search

class RecentSearchedActivity : AppCompatActivity(), OnSearchClickListener {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.v_main)
    var mMainView: View? = null

    @JvmField
    @BindView(R.id.rv_search)
    var mSearchRecyclerView: RecyclerView? = null
    var mSearchAdapter: SearchAdapter? = null
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_searched)

        // Bind views
        ButterKnife.bind(this)

        // Initials AppDatabase
        mDb = getInstance(applicationContext)

        // setup the {@link Toolbar}
        setupToolbar()

        // setup {@Search} list
        setupSearchList()
        // load {@Search} list
        loadSearchList()
    }

    private fun setupToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Recent Searched"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)

        // Set on search expand listener
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                Toast.makeText(this@RecentSearchedActivity,
                    "Expand", Toast.LENGTH_SHORT).show()
                mMainView!!.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                Toast.makeText(this@RecentSearchedActivity,
                    "Collapse", Toast.LENGTH_SHORT).show()
                mMainView!!.visibility = View.VISIBLE
                return true
            }
        })

        // Set on query text listener
        val searchView = searchMenuItem.actionView as SearchView?
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@RecentSearchedActivity,
                    "Submit: $query", Toast.LENGTH_SHORT).show()
                searchMenuItem.collapseActionView()
                val intent = Intent(this@RecentSearchedActivity, ProductListActivity::class.java)
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

    private fun setupSearchList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mSearchAdapter = SearchAdapter(this, null, this)
        mSearchRecyclerView!!.layoutManager = layoutManager
        mSearchRecyclerView!!.adapter = mSearchAdapter
        mSearchRecyclerView!!.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    private fun loadSearchList() {
        val searches: MutableList<Search> = ArrayList()
        val spList = mDb!!.recentlySearchedDao()!!
            .findAll()
        for (sp in spList!!) {
            val search = Search()
            search.value = sp!!.text
            searches.add(search)
        }
        mSearchAdapter!!.setList(searches)
    }

    private val searchListHolder: List<String>
        private get() {
            val list: MutableList<String> = ArrayList()
            list.add("HP")
            list.add("Dell")
            list.add("MSI")
            list.add("Laptop")
            list.add("Keyboard")
            return list
        }

    override fun onSearchClicked(search: Search?) {
        val intent = Intent(this, ProductListActivity::class.java)
        intent.putExtra(ProductListActivity.EXTRA_SEARCH_QUERY, search!!.value)
        startActivity(intent)
    }
}
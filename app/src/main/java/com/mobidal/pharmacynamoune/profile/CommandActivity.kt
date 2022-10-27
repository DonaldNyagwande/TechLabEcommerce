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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobidal.pharmacynamoune.ProductListActivity
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.adapter.CommandAdapter
import com.mobidal.pharmacynamoune.adapter.CommandAdapter.OnCommandClickListener
import com.mobidal.pharmacynamoune.adapter.ProductAdapter.OnProduct5ClickListener
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.helper.GridSpacingItemDecoration
import com.mobidal.pharmacynamoune.model.Command
import com.mobidal.pharmacynamoune.model.Product
import com.mobidal.pharmacynamoune.model.Product.Pivot

class CommandActivity : AppCompatActivity(), OnCommandClickListener, OnProduct5ClickListener {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.rv_command)
    var mCommandRecyclerView: RecyclerView? = null
    private var mCommandAdapter: CommandAdapter? = null
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command)

        // Bind views
        ButterKnife.bind(this)

        // Initials AppDatabase
        mDb = getInstance(applicationContext)

        // setup the {@link Toolbar}
        setupToolbar()

        // Setup {@ Command} list
        setupCommandList()

        // Load {@ Command} list
        loadCommandList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)

        // Set on search expand listener
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                Toast.makeText(this@CommandActivity,
                    "Expand", Toast.LENGTH_SHORT).show()
                mCommandRecyclerView!!.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                Toast.makeText(this@CommandActivity,
                    "Collapse", Toast.LENGTH_SHORT).show()
                mCommandRecyclerView!!.visibility = View.VISIBLE
                return true
            }
        })

        // Set on query text listener
        val searchView = searchMenuItem.actionView as SearchView?
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@CommandActivity,
                    "Submit: $query", Toast.LENGTH_SHORT).show()
                searchMenuItem.collapseActionView()
                val intent = Intent(this@CommandActivity, ProductListActivity::class.java)
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

    private fun setupToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "My Commands"
    }

    private fun setupCommandList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mCommandAdapter = CommandAdapter(this, null,
            this, this)

        // Create {@link RecyclerView.ItemDecoration}
        val spacingInPixel = resources
            .getDimensionPixelSize(R.dimen.grid_command_spacing)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(1, spacingInPixel, true)
        mCommandRecyclerView!!.layoutManager = layoutManager
        mCommandRecyclerView!!.adapter = mCommandAdapter
        mCommandRecyclerView!!.addItemDecoration(gridSpacingItemDecoration)
    }

    private fun loadCommandList() {
        val pivot = Pivot(false, 1)
        val productList: MutableList<Product> = ArrayList()
        val ceList = mDb!!.commandDao()!!.findAll()
        val commands: MutableList<Command> = ArrayList()
        for (ce in ceList!!) {
            // Get command product entities
            val cpList = mDb!!.commandProductsDao()!!
                .findByCommandId(ce!!.id.toLong())
            // Get product list
            for (cp in cpList!!) {
                val pe = mDb!!.productDao()!!
                    .find(Integer.toUnsignedLong(cp!!.productId))
                val prod = Product(pe!!.id,
                    pe.mark!!,
                    pe.name!!,
                    pe.description!!,
                    pe.price,
                    pe.pictureUrl!!,
                    null,
                    pe.viewNumber,
                    pivot)
                productList.add(prod)
            }
            val cmd = Command(
                ce.id, ce.state!!, ce.deliveryPrice, productList)
            commands.add(cmd)
        }
        mCommandAdapter!!.setList(commands)
    }

    //list.add(null);
    //list.add(null);
    //list.add(null);
    private val commandList: List<Command>
        private get() =//list.add(null);
        //list.add(null);
            //list.add(null);
            ArrayList()

    override fun onCommandClicked(command: Command?) {}
    override fun onProductClicked(product: Product?) {}
}
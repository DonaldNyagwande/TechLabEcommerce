package com.mobidal.pharmacynamoune.profile

import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import com.mobidal.pharmacynamoune.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import butterknife.ButterKnife

class ChangePasswordActivity : AppCompatActivity() {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        // Bind views
        ButterKnife.bind(this)

        // setup the {@link Toolbar}
        setupToolbar()
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
        supportActionBar!!.title = "Change Password"
    }
}
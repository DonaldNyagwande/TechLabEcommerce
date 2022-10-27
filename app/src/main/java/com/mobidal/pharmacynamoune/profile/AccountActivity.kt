package com.mobidal.pharmacynamoune.profile

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputLayout
import com.mobidal.pharmacynamoune.R
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance

class AccountActivity : AppCompatActivity() {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null
    private var mDb: AppDatabase? = null

    @JvmField
    @BindView(R.id.s_gender)
    var mGenderSpinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        // Bind views
        ButterKnife.bind(this)

        // Initials AppDatabase
        mDb = getInstance(applicationContext)
        val sp = getSharedPreferences("user", MODE_PRIVATE)
        val user = mDb!!.userDao()!!.find(sp.getLong("user_id", 0))

        // setup the {@link Toolbar}
        setupToolbar()

        // Setup gender list
        setupGenderList()
        (findViewById<View>(R.id.til_firstname) as TextInputLayout).editText!!
            .setText(user!!.firstName)
        (findViewById<View>(R.id.til_lastname) as TextInputLayout).editText!!
            .setText(user.lastName)
        (findViewById<View>(R.id.til_email) as TextInputLayout).editText!!
            .setText(user.username)
        (findViewById<View>(R.id.til_phone) as TextInputLayout).editText!!
            .setText(user.phoneNumber)
        (findViewById<View>(R.id.b_save) as Button).setOnClickListener {
            val fn = (findViewById<View>(R.id.til_firstname) as TextInputLayout).editText!!
                .text.toString()
            val ln = (findViewById<View>(R.id.til_lastname) as TextInputLayout).editText!!
                .text.toString()
            val phone = (findViewById<View>(R.id.til_phone) as TextInputLayout).editText!!
                .text.toString()
            user.firstName = fn
            user.lastName = ln
            user.phoneNumber = phone
            mDb!!.userDao()!!.update(user)
            Toast.makeText(this@AccountActivity,
                "User updated successfully", Toast.LENGTH_SHORT).show()
        }
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
        supportActionBar!!.title = "My Account"
    }

    private fun setupGenderList() {
        val genders = resources.getStringArray(R.array.genders)
        val genderAdapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, genders)
        mGenderSpinner!!.adapter = genderAdapter
    }
}
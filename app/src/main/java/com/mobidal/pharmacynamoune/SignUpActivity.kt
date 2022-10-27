package com.mobidal.pharmacynamoune

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputLayout
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance
import com.mobidal.pharmacynamoune.db.entity.UserEntity
import com.mobidal.pharmacynamoune.executor.AppExecutors.Companion.instance

class SignUpActivity : AppCompatActivity() {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.til_firstname)
    var mFirstNameTextInputLayout: TextInputLayout? = null

    @JvmField
    @BindView(R.id.til_lastname)
    var mLastNameTextInputLayout: TextInputLayout? = null

    @JvmField
    @BindView(R.id.til_email)
    var mEmailTextInputLayout: TextInputLayout? = null

    @JvmField
    @BindView(R.id.s_gender)
    var mGenderSpinner: Spinner? = null

    @JvmField
    @BindView(R.id.til_password)
    var mPasswordTextInputLayout: TextInputLayout? = null

    @JvmField
    @BindView(R.id.til_phone)
    var mPhoneTextInputLayout: TextInputLayout? = null

    @JvmField
    @BindView(R.id.b_sign_up)
    var mSignUpButton: Button? = null
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Bind Views
        ButterKnife.bind(this)

        // Initials AppDatabase
        mDb = getInstance(applicationContext)

        // setup the {@link Toolbar}
        setupToolbar()

        // Setup gender list
        setupGenderList()
        mSignUpButton!!.setOnClickListener {
            instance!!.diskIO().execute {
                val user = UserEntity()
                user.username = mEmailTextInputLayout!!.editText!!.text.toString()
                user.password = mPasswordTextInputLayout!!.editText!!.text.toString()
                user.firstName = mFirstNameTextInputLayout!!.editText!!.text.toString()
                user.lastName = mLastNameTextInputLayout!!.editText!!.text.toString()
                user.gender = mGenderSpinner!!.selectedItem.toString()
                user.phoneNumber = mPhoneTextInputLayout!!.editText!!.text.toString()
                val userId = mDb!!.userDao()!!.insert(user)
                val sp = getSharedPreferences("user", MODE_PRIVATE)
                val spe = sp.edit()
                spe.putLong("user_id", userId)
                spe.apply()
            }
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            startActivity(intent)
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
        supportActionBar!!.title = "Sign Up"
    }

    private fun setupGenderList() {
        val genders = resources.getStringArray(R.array.genders)
        val genderAdapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, genders)
        mGenderSpinner!!.adapter = genderAdapter
    }
}
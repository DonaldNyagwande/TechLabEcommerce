package com.mobidal.pharmacynamoune

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputLayout
import com.mobidal.pharmacynamoune.db.AppDatabase
import com.mobidal.pharmacynamoune.db.AppDatabase.Companion.getInstance

class SignInActivity : AppCompatActivity() {
    @JvmField
    @BindView(R.id.tb_main)
    var mToolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.til_email)
    var mEmailTextInputLayout: TextInputLayout? = null

    @JvmField
    @BindView(R.id.til_password)
    var mPasswordTextInputLayout: TextInputLayout? = null

    @JvmField
    @BindView(R.id.tv_forget_password)
    var mForgetPasswordView: View? = null

    @JvmField
    @BindView(R.id.b_sign_in)
    var mSignInButton: Button? = null

    @JvmField
    @BindView(R.id.tv_create_account)
    var mCreateAccountView: View? = null
    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Bind Views
        ButterKnife.bind(this)

        // Initials AppDatabase
        mDb = getInstance(applicationContext)

        // setup the {@link Toolbar}
        setupToolbar()
        mCreateAccountView!!.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        mSignInButton!!.setOnClickListener {
            val username = mEmailTextInputLayout!!.editText!!.text.toString()
            val password = mPasswordTextInputLayout!!.editText!!.text.toString()
            val user = mDb!!.userDao()!!.isExist(username, password)
            if (user != null) {
                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                startActivity(intent)
            }
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
        supportActionBar!!.title = "Sign In"
    }
}
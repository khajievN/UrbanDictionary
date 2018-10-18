package com.everything4droid.urbandickotlin.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.everything4droid.urbandickotlin.R
import com.everything4droid.urbandickotlin.data.response.UserResponse
import com.everything4droid.urbandickotlin.data.util.ERROR_STATUS
import com.everything4droid.urbandickotlin.data.util.ErrorKit
import com.everything4droid.urbandickotlin.data.util.observeWith
import com.everything4droid.urbandickotlin.mvvm.LoginViewModel
import com.everything4droid.urbandickotlin.mvvm.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

/**
 * Created by Khajiev Nizomjon on 14/10/2018.
 */
class LoginActivity : AppCompatActivity() {
    lateinit var vm: LoginViewModel
    private val vmFactory by inject<LoginViewModelFactory>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        vm = ViewModelProviders.of(this, vmFactory).get(LoginViewModel::class.java)

        vm.progress.observeWith(this, this::onProgress)

        vm.errorBase.observeWith(this, this::onError)

        vm.uiModel.observeWith(this, this::onSuccess)


        loginBtn.setOnClickListener {
            validate()
            hideKeyboard(this)
            val email = emailT.text.toString()
            val password = passwordT.text.toString()
            vm.login(email, password)
        }

        signBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }

    private fun validate() {
        if (emailT.text.toString() == "" && passwordT.text.toString() == "") {
            return
        }
    }

    private fun onProgress(isShown: Boolean) {
        progressBar.visibility = if (isShown) View.VISIBLE else View.GONE
    }

    private fun onError(errorKit: ErrorKit) = when (errorKit.errorStatus) {
        ERROR_STATUS.DEV -> {
            Toast.makeText(this, errorKit.message, Toast.LENGTH_SHORT).show()
        }
        ERROR_STATUS.NETWORK -> {
            Toast.makeText(this, getString(R.string.networ_issue), Toast.LENGTH_SHORT).show()
        }
        else -> {
            Toast.makeText(this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSuccess(data: UserResponse) {
        hideKeyboard(this)
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()

    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
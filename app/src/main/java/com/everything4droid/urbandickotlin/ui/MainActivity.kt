package com.everything4droid.urbandickotlin.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.everything4droid.urbandickotlin.R
import com.everything4droid.urbandickotlin.data.datasource.SharedDataSource
import com.everything4droid.urbandickotlin.data.response.Word
import com.everything4droid.urbandickotlin.data.util.*
import com.everything4droid.urbandickotlin.mvvm.UrbanViewModel
import com.everything4droid.urbandickotlin.mvvm.UrbanViewModelFactory
import com.everything4droid.urbandickotlin.ui.adapter.WordAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdListener


class MainActivity : AppCompatActivity() {

    lateinit var vm: UrbanViewModel
    private val vmFactory by inject<UrbanViewModelFactory>()
    private val sharedDataSource by inject<SharedDataSource>()
    lateinit var wordAdapter: WordAdapter

    lateinit var mAdView: AdView

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!sharedDataSource.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_main)
        vm = ViewModelProviders.of(this, vmFactory).get(UrbanViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        wordAdapter = WordAdapter()
        recyclerView.adapter = wordAdapter



        vm.progress.observeWith(this, this::onProgress)

        vm.errorBase.observeWith(this, this::onError)

        vm.uiModel.observeWith(this, this::onSuccess)

        searchBtn.setOnClickListener {
            vm.getDefinition(wordT.text.toString().trim())
        }

        wordAdapter.setOnItemClickListener(object : WordAdapter.OnItemClickListener {
            override fun onClick(view: View, data: Word) {
                startActivity(DefinitionDetailIntent(data))
            }


        })
        initAdMob()

    }

    fun initAdMob() {

        MobileAds.initialize(this, Const.AdMob_ID)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

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

    private fun onSuccess(data: List<Word>) {
        hideKeyboard(this)
        wordAdapter.addWords(data)
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_favorite -> {
                startActivity(Intent(this, FavoritiesActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

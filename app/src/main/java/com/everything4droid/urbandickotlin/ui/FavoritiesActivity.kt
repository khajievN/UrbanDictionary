package com.everything4droid.urbandickotlin.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.everything4droid.urbandickotlin.R
import com.everything4droid.urbandickotlin.data.datasource.SharedDataSource
import com.everything4droid.urbandickotlin.data.util.observeWith
import com.everything4droid.urbandickotlin.mvvm.UrbanViewModel
import com.everything4droid.urbandickotlin.mvvm.UrbanViewModelFactory
import com.everything4droid.urbandickotlin.ui.adapter.WordAdapter
import kotlinx.android.synthetic.main.activity_favorities.*
import org.koin.android.ext.android.inject
import android.content.Intent
import android.net.Uri
import android.view.View
import com.everything4droid.urbandickotlin.data.response.Word
import com.everything4droid.urbandickotlin.data.util.Const
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener


/**
 * Created by Khajiev Nizomjon on 11/10/2018.
 */
class FavoritiesActivity : AppCompatActivity(), RewardedVideoAdListener {

    lateinit var vm: UrbanViewModel
    private val vmFactory by inject<UrbanViewModelFactory>()
    private val sharedDataSource by inject<SharedDataSource>()
    lateinit var wordAdapter: WordAdapter
    private lateinit var mRewardedVideoAd: RewardedVideoAd

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorities)

        vm = ViewModelProviders.of(this, vmFactory).get(UrbanViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        wordAdapter = WordAdapter()
        recyclerView.adapter = wordAdapter

        vm.allFavoriteWords.observeWith(this) {
            wordAdapter.addWords(it)
        }
        wordAdapter.setOnItemClickListener(object : WordAdapter.OnItemClickListener {
            override fun onClick(view: View, data: Word) {
            }


        })

        vm.getAllWords()

        pdfBtn.setOnClickListener {
            val url = "http://everything4droid.com/api/v1/pdf/${sharedDataSource.userId}/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
//            if (mRewardedVideoAd.isLoaded) {
//                mRewardedVideoAd.show()
//            }
        }

        MobileAds.initialize(this, Const.AdMob_ID)

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this

        loadRewardedVideoAd()

    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(Const.AdMob_REWARD_UNIT_Id,
                AdRequest.Builder().build())
    }

    override fun onRewarded(reward: RewardItem) {
    }

    override fun onRewardedVideoAdLeftApplication() {
    }

    override fun onRewardedVideoAdClosed() {

    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
    }

    override fun onRewardedVideoAdLoaded() {
    }

    override fun onRewardedVideoAdOpened() {
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoCompleted() {
    }
}
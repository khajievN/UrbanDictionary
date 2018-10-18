package com.everything4droid.urbandickotlin.ui

import android.annotation.SuppressLint
import android.media.AudioManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.everything4droid.urbandickotlin.R
import com.everything4droid.urbandickotlin.data.response.Sound
import com.everything4droid.urbandickotlin.data.response.Word
import com.everything4droid.urbandickotlin.databinding.ActivityDefinitionBinding
import com.everything4droid.urbandickotlin.ui.adapter.SoundAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_definition.*
import java.util.ArrayList
import android.media.MediaPlayer
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.everything4droid.urbandickotlin.data.util.Const
import com.everything4droid.urbandickotlin.mvvm.UrbanViewModel
import com.everything4droid.urbandickotlin.mvvm.UrbanViewModelFactory
import org.koin.android.ext.android.inject
import android.view.Menu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.work.WorkManager
import com.everything4droid.urbandickotlin.data.util.observeWith
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.lang.Exception


/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */


class DefinitionActivity : AppCompatActivity() {

    lateinit var vm: UrbanViewModel
    private val vmFactory by inject<UrbanViewModelFactory>()

    private lateinit var word: Word
    private lateinit var soundAdapter: SoundAdapter
    private var soundList: ArrayList<Sound> = ArrayList()

    private var mediaPlayer: MediaPlayer? = null

    private var isFavorite = false

    private var isSynced = true
    lateinit var mAdView : AdView

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDefinitionBinding = DataBindingUtil.setContentView(this, R.layout.activity_definition)
        vm = ViewModelProviders.of(this, vmFactory).get(UrbanViewModel::class.java)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val gson = Gson()
        val wordJsonBody = intent.getStringExtra(Const.INTENT_WORD_JSON)

        word = gson.fromJson(wordJsonBody, Word::class.java)
        binding.model = word

        word.soundList.forEachIndexed { index, sound ->
            val position = index + 1
            soundList.add(Sound("Sound $position", sound, false))
        }

        vm.isFavorite(word)

        vm.isFavorite.observeWith(this, this::onFavorite)
        vm.isSynced.observeWith(this, this::onSynced)

        MobileAds.initialize(this, Const.AdMob_ID)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        soundAdapter = SoundAdapter(soundList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = soundAdapter

        soundAdapter.setOnItemClickListener(object : SoundAdapter.OnItemClickListener {
            override fun onClick(view: View, data: Sound) {
                try {
                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.setDataSource(data.soundUrl)
                    mediaPlayer!!.prepareAsync()
                    mediaPlayer!!.setOnPreparedListener {
                        mediaPlayer!!.start()
                    }
                } catch (e: Exception) {
                    print(e.localizedMessage)
                }
            }
        })
    }

    private fun onFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        invalidateOptionsMenu()
    }

    private fun onSynced(isSynced: Boolean) {
        this.isSynced = isSynced
        invalidateOptionsMenu()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_favorite -> {
                vm.insertOrUpdateWord(word)
                isFavorite = !isFavorite
                invalidateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.definition_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val isFavoriteIcon = menu!!.findItem(R.id.action_favorite)
        val isSyncedIcon = menu.findItem(R.id.action_sync)
        if (isFavorite) {
            isFavoriteIcon.icon = ContextCompat.getDrawable(this, R.drawable.baseline_favorite_black_18dp)
        } else {
            isFavoriteIcon.icon = ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_black_18dp)

        }

        isSyncedIcon.isVisible = !isSynced

        return super.onPrepareOptionsMenu(menu)
    }
}
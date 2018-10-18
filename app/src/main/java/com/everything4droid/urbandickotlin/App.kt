package com.everything4droid.urbandickotlin

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.everything4droid.urbandickotlin.data.CoroutinesContextProvider
import com.everything4droid.urbandickotlin.data.datasource.UrbanDataSource
import com.everything4droid.urbandickotlin.data.repository.UrbanLocalRepository
import com.everything4droid.urbandickotlin.data.repository.UrbanRepository
import com.everything4droid.urbandickotlin.data.service.RoomService
import com.everything4droid.urbandickotlin.data.service.api.Api
import com.everything4droid.urbandickotlin.data.service.api.ApiCreator
import com.everything4droid.urbandickotlin.domain.UrbanLocalUseCase
import com.everything4droid.urbandickotlin.domain.UrbanUseCase
import com.everything4droid.urbandickotlin.mvvm.UrbanViewModel
import com.everything4droid.urbandickotlin.mvvm.UrbanViewModelFactory
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidApplication
import com.crashlytics.android.Crashlytics
import com.everything4droid.urbandickotlin.data.datasource.SharedDataSource
import com.everything4droid.urbandickotlin.data.service.api.MainApi
import com.everything4droid.urbandickotlin.data.util.Const
import com.everything4droid.urbandickotlin.domain.LoginUseCase
import com.everything4droid.urbandickotlin.domain.SignupUseCase
import com.everything4droid.urbandickotlin.jobs.AddFavoriteWordJob
import com.everything4droid.urbandickotlin.mvvm.LoginViewModel
import com.everything4droid.urbandickotlin.mvvm.LoginViewModelFactory
import com.google.android.gms.ads.MobileAds
import io.fabric.sdk.android.Fabric


/**
 * Created by Khajiev Nizomjon on 09/10/2018.
 */
class App : Application() {

    private val module = org.koin.dsl.module.applicationContext {
        bean { SharedDataSource(androidApplication()) }
        bean { ApiCreator.get(Api::class.java) }
        bean { ApiCreator.getMainEndpoint(MainApi::class.java) }
        bean { CoroutinesContextProvider() }
        bean { UrbanDataSource(get(), get()) }
        bean { UrbanRepository(get()) }
        bean { UrbanUseCase(get()) }
        bean { LoginUseCase(get()) }
        bean { SignupUseCase(get()) }
        bean { UrbanViewModelFactory(get(), get(), get(), get()) }
        bean { LoginViewModelFactory(get(), get(), get(), get()) }


        bean {
            Room.databaseBuilder(androidApplication(), RoomService::class.java, "word-db")
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .build()
        }

        bean { get<RoomService>().getWordDao() }
        bean { UrbanLocalRepository(get()) }
        bean { UrbanLocalUseCase(get()) }



        viewModel { UrbanViewModel(get(), get(), get(), get()) }
        viewModel { LoginViewModel(get(), get(), get(), get()) }

    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(module))
        Fabric.with(this, Crashlytics())
    }

    private val MIGRATION_1_2: Migration = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE word " + " ADD COLUMN isSynced INTEGER DEFAULT 0")
        }
    }
}
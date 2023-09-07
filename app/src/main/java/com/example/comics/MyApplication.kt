package com.example.comics

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.comics.di.DependencyModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(DependencyModule.moduleApp)
        }
    }

    companion object {
        private var instance: MyApplication? = null
        private var connected = false

        fun hasNetwork(): Boolean = isNetworkConnected()

        private fun isNetworkConnected(): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connected
            } else {
                val cm: ConnectivityManager? =
                    instance?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
                activeNetwork != null && activeNetwork.isConnectedOrConnecting
            }
        }
    }

}
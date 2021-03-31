package com.example.tawkapp

import androidx.lifecycle.MutableLiveData
import androidx.multidex.MultiDexApplication
import com.example.tawkapp.recievers.NetworkReceiver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class TawkApplication: MultiDexApplication() {
    var updateUserData = MutableLiveData<Boolean>()

    companion object: TawkApplication() {
        var mApplication: TawkApplication = TawkApplication()
    }

    fun getInstance(): TawkApplication? {
        if (mApplication == null) {
            mApplication =
                TawkApplication()
        }
        return mApplication
    }

    fun setNetworkListener(listener: NetworkReceiver.ConnectivityReceiverListener) {
        NetworkReceiver.connectivityReceiverListener = listener
    }
}
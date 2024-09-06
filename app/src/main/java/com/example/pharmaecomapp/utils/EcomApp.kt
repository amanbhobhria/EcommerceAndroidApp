package com.example.pharmaecomapp.utils


import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.NonNull
import androidx.annotation.Nullable


class EcomApp : Application(), Application.ActivityLifecycleCallbacks {
    companion object{
        lateinit var appContext: Context
    }
    lateinit var deviceId: String



    override fun onCreate() {
        super.onCreate()
        appContext = this
        deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(@NonNull activity: Activity, @Nullable savedInstanceState: Bundle?) {
        Utility.changeStatusBarColor(activity)
        // Additional activity creation logic if needed
    }

    override fun onActivityStarted(activity: Activity) {
        // Override with logic if needed
    }

    override fun onActivityResumed(activity: Activity) {
        // Override with logic if needed
    }

    override fun onActivityPaused(activity: Activity) {
        // Override with logic if needed
    }

    override fun onActivityStopped(activity: Activity) {
        // Override with logic if needed
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Override with logic if needed
    }

    override fun onActivityDestroyed(activity: Activity) {
        // Override with logic if needed
    }
}

package com.example.pharmaecomapp.utils

import android.content.Context
import android.content.SharedPreferences


class PrefManager {
    init {
        init()
    }

    companion object {
        var sharedPreferences: SharedPreferences? = null
        fun putString(key: String?, `val`: String?) {
            init()
            val editor = sharedPreferences!!.edit()
            editor.putString(key, `val`)
            editor.apply()
        }

        fun getString(key: String?, defaultValue: String?): String? {
            init()
            return sharedPreferences!!.getString(key, defaultValue)
        }

        fun putInt(key: String?, `val`: Int) {
            init()
            val editor = sharedPreferences!!.edit()
            editor.putInt(key, `val`)
            editor.apply()
        }

        fun getInt(key: String?, defaultValue: Int): Int {
            init()
            return sharedPreferences!!.getInt(key, defaultValue)
        }

        fun putBoolean(key: String?, `val`: Boolean) {
            init()
            val editor = sharedPreferences!!.edit()
            editor.putBoolean(key, `val`)
            editor.apply()
        }

        fun getBoolean(key: String?, defaultToMetric: Boolean): Boolean {
            init()
            return sharedPreferences!!.getBoolean(key, defaultToMetric)
        }

        fun checkKey(key: String?): Boolean {
            sharedPreferences = EcomApp().getApplicationContext()
                .getSharedPreferences("antimatter", Context.MODE_PRIVATE)
            return sharedPreferences!!.contains(key)
        }

        private fun init() {
            sharedPreferences =
                EcomApp.appContext.getSharedPreferences("antimatter", Context.MODE_PRIVATE)
        }

        fun clear() {
            init()
            val editor = sharedPreferences!!.edit()
            editor.clear()
            editor.commit()
        }
    }
}

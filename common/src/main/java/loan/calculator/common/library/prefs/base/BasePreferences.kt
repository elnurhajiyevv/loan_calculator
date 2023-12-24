package loan.calculator.common.library.prefs.base

import android.content.SharedPreferences

abstract class BasePreferences {

    abstract val filename: String

    abstract val prefs: SharedPreferences


    open fun clear() {
        val editor = prefs.edit()
        editor.clear()
        editor?.apply()
    }

    fun remove(item: String) {
        val editor = prefs.edit()
        editor.remove(item)
        editor?.apply()
    }

    fun <T> set(item: String, value: T) {
        val editor = prefs.edit()

        when (value) {
            is Int -> {
                editor?.putInt(item, value)
            }
            is Long -> {
                editor?.putLong(item, value)
            }
            is Float -> {
                editor?.putFloat(item, value)
            }
            is String -> {
                editor?.putString(item, value)
            }
            is Boolean -> {
                editor?.putBoolean(item, value)
            }
            else -> {
                editor?.putString(item, value as String)
            }
        }
        editor?.apply()
    }

    inline fun <reified T> get(item: String, default: T): T {
        return when (default) {
            is Int -> {
                prefs.getInt(item, default as Int) as T
            }
            is Long -> {
                prefs.getLong(item, default as Long) as T
            }
            is Float -> {
                prefs.getFloat(item, default as Float) as T
            }
            is String -> {
                prefs.getString(item, default as String) as T
            }
            is Boolean -> {
                prefs.getBoolean(item, default as Boolean) as T
            }
            else -> {
                prefs.getString(item, default as String) as T
            }
        }
    }
}
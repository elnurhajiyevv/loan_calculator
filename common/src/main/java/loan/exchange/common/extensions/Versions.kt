package loan.exchange.common.extensions

import android.os.Build

fun isVersionHigherAndEqual(version: Int): Boolean {
    return Build.VERSION.SDK_INT >= version
}

fun isVersionHigher(version: Int): Boolean {
    return Build.VERSION.SDK_INT > version
}

fun isVersionLowerAndEqual(version: Int): Boolean {
    return Build.VERSION.SDK_INT <= version
}

fun isVersionLower(version: Int): Boolean {
    return Build.VERSION.SDK_INT < version
}
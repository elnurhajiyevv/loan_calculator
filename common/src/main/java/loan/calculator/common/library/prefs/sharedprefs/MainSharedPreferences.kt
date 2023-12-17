/*
 * Created by Elnur Hajiyev on on 7/27/22, 12:53 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.common.library.prefs.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import loan.calculator.common.library.prefs.base.BasePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class MainSharedPreferences @Inject constructor(@ApplicationContext context: Context) : BasePreferences() {

    companion object {
        const val PREF_NAME = "main_shared_pref"
    }

    override val filename: String
        get() = PREF_NAME

    override val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        )
    }

}
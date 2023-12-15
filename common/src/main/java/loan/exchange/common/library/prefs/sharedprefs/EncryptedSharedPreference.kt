/*
 * Created by Elnur Hajiyev on on 7/27/22, 12:23 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.exchange.common.library.prefs.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import loan.exchange.common.library.prefs.base.BasePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


open class EncryptedSharedPreference @Inject constructor(@ApplicationContext context: Context) : BasePreferences() {

    companion object {
        const val ENCRYPTED_PREF_NAME = "encrypted_shared_pref"
    }

    override val filename: String
        get() = ENCRYPTED_PREF_NAME

    override val prefs: SharedPreferences by lazy {
        val key = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        EncryptedSharedPreferences.create(
            context,
            filename,
            key,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
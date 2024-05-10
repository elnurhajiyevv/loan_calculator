/*
 * Created by Elnur Hajiyev on on 8/02/22, 5:44 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package loan.calculator.app.notification.entity

import android.os.Bundle
import androidx.core.os.bundleOf

import org.json.JSONException

import org.json.JSONObject


fun Map<String, String>.fromNotificationToDomain() = NotificationModel.notification {
    title = get("title")
    message = get("message")
    deepLink = get("deepLinkType")
    extras = get("extras")?.let {
        try {
            JSONObject(it).jsonToBundle()
        } catch (e: Exception) {
            bundleOf()
        }
    }
}

@Throws(JSONException::class)
fun JSONObject.jsonToBundle(): Bundle {
    val bundle = Bundle()
    val iter: Iterator<*> = keys()
    while (iter.hasNext()) {
        val key = iter.next() as String
        val value = getString(key)
        bundle.putString(key, value)
    }
    return bundle
}
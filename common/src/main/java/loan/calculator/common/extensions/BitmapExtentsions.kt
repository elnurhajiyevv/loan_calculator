package loan.calculator.common.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

fun Bitmap.encodeToString(): String? {
    return Base64.encodeToString(ByteArrayOutputStream().apply {
        this@encodeToString.compress(Bitmap.CompressFormat.JPEG, 100, this)
    }.toByteArray(), Base64.DEFAULT)
}

fun String.decodeToBitmap(): Bitmap? {
    val imageBytes = Base64.decode(this, 0)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}
package loan.exchange.data.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverter
fun fromStringMap(map: Map<String, String>): String {
    val gson = Gson()
    return gson.toJson(map)
}

@TypeConverter
fun fromString(value: String): Map<String, String> {
    val mapType = object : TypeToken<Map<String, String>>() {}.type
    return Gson().fromJson(value, mapType)
}
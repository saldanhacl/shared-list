package br.com.go5.sharedlist.utils

import androidx.room.TypeConverter
import br.com.go5.sharedlist.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ListConverter {
    var gson = Gson()
    @TypeConverter
    fun fromJSON(data: String?): List<User>? {

        if (data == null){
            return Collections.emptyList()
        }
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<User>?): String? {
        return gson.toJson(someObjects)
    }
}
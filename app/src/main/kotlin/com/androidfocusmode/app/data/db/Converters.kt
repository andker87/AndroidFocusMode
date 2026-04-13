package com.androidfocusmode.app.data.db

import androidx.room.TypeConverter
import com.androidfocusmode.app.data.model.ActivationTrigger
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromActivationTrigger(value: ActivationTrigger?): String? {
        return value?.name
    }

    @TypeConverter
    fun toActivationTrigger(value: String?): ActivationTrigger? {
        return value?.let { ActivationTrigger.valueOf(it) }
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return if (value == null) null else gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromIntList(value: List<Int>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String?): List<Int>? {
        return if (value == null) null else gson.fromJson(value, object : TypeToken<List<Int>>() {}.type)
    }
}

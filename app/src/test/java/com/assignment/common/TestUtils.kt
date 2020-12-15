package com.assignment.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Suppress("UnstableApiUsage")
class TestUtils {

    companion object {

        inline fun <reified T> fromJson(path: String): T {
            val json = MockResponseFileReader(path).content
            return Gson().fromJson(json, object : TypeToken<T>() {}.type)
        }
    }
}
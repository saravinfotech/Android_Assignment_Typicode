package com.assignment.util

import com.assignment.MockResponseFileReader
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

@Suppress("UnstableApiUsage")
class TestUtils {

    companion object {

        inline fun <reified T> fromJson(path: String): T {
            val json = MockResponseFileReader(path).content
            return Gson().fromJson(json, object : TypeToken<T>() {}.type)
        }
    }
}
package com.assignment.util

import java.io.InputStreamReader

class MockResponseFileReader(path: String) {

    var content: String = ""

    init {
        this.javaClass.classLoader?.let {
            val reader = InputStreamReader(it.getResourceAsStream(path))
            content = reader.readText()
            reader.close()
        }
    }

}
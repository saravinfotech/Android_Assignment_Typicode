package com.assignment

import java.io.InputStreamReader

/**
 * MockResponseFileReader is used to read the contents of provided file path
 */
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
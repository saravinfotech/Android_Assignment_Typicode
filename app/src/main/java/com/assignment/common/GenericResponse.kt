package com.assignment.common

import com.assignment.data.enums.Status

class GenericResponse(
    val status: Status,
    val data: Any?,
    val error: Exception?
) {

    companion object {
        fun loading(): GenericResponse {
            return GenericResponse(Status.LOADING, null, null)
        }

        fun success(data: Any?): GenericResponse {
            return GenericResponse(Status.SUCCESS, data, null)
        }

        fun error(error: Exception): GenericResponse {
            return GenericResponse(Status.ERROR, null, error)
        }

    }

}
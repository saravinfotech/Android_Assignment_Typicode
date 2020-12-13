package com.assignment.common

import com.assignment.data.enums.Status

/**
 *  GenericResponse is used to take the response from API call and transfer it back to calling side
 *  It's just a wrapper
 *  As we can have different Data Type being returned from API call, so we need to use a generic way to represent data
 */
class GenericResponse(
    val status: Status,
    val data: Any?,
    val error: Exception?
) {

    companion object {
        /**
         * Represent the loading state of API call
         */
        fun loading(): GenericResponse {
            return GenericResponse(Status.LOADING, null, null)
        }

        /**
         * Represent the success state of API call
         * Also provides the data to client side which has initiated the API call
         */
        fun success(data: Any?): GenericResponse {
            return GenericResponse(Status.SUCCESS, data, null)
        }

        /**
         * Represent the error state of API call
         * Also provides the error to client side which has initiated the API call
         */
        fun error(error: Exception): GenericResponse {
            return GenericResponse(Status.ERROR, null, error)
        }

    }

}
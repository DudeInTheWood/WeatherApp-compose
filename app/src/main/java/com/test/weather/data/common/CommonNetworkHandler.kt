package com.test.weather.data.common

import android.util.Log
import com.squareup.moshi.Moshi
import com.test.weather.data.model.ApiErrorResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: Int? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null, code: Int? = null) :
        Resource<T>(data, message, code)
}

class NetworkHandler @Inject constructor(
    private val moshi: Moshi
) {
    private val apiErrorAdapter by lazy { moshi.adapter(ApiErrorResponse::class.java) }

    suspend fun <T> handleNetworkCall(call: suspend () -> T): Resource<T> {
        return try {
            val result = call()
            Resource.Success(result)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val apiError = errorBody?.let {
                try {
                    apiErrorAdapter.fromJson(it)
                } catch (_: Exception) {
                    null
                }
            }

            val code = when (val cod = apiError?.cod) {
                is Int -> cod
                else -> e.code()
            }

            val message = apiError?.message ?: "HTTP Error ${code}: ${e.message()}"
            Resource.Error(message = message, code = code)

        } catch (_: IOException) {
            Resource.Error("Network failure. Check your connection.")
        } catch (e: Exception) {
            Resource.Error("An unexpected error occurred: ${e.message}")
        }
    }
}


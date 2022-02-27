package sk.himdeve.net

import com.squareup.moshi.Moshi
import okhttp3.Request
import okio.Buffer
import retrofit2.Response
import java.nio.charset.StandardCharsets

open class ApiHelper(private val moshi: Moshi) {

    open fun <T> body(response: Response<T>): T? {
        return if (!response.isSuccessful) {
            val errorResponse = try {
                val errorBodyString = response.errorBody()?.string()
                if (errorBodyString != null) {
                    try {
                        moshi.adapter(ErrorResponse::class.java).fromJson(errorBodyString)
                    } catch (ex: Exception) {
                        // Some apis return a xml, just stick the whole body into error message
                        // for logging purposes
                        ErrorResponse(ErrorResponse.Error(errorBodyString, null, null, null))
                    }
                } else {
                    null
                }
            } catch (ex: Exception) {
                null
            }

            val request = response.raw().request
            throw cleanApiException(
                ApiException(
                    verb = request.method,
                    path = request.url.encodedPath,
                    errorCode = response.code(),
                    requestBody = request.bodyAsString(),
                    errorMessage = errorResponse?.error?.errorMessage
                )
            )
        } else {
            response.body()
        }
    }

    // skip the first 2 rows from stackTrace to clean ApiException
    private fun cleanApiException(e: ApiException): ApiException {
        return try {
            val stackTrace = e.stackTrace
            val subTrace = arrayOfNulls<StackTraceElement>(stackTrace.size - 2)
            System.arraycopy(stackTrace, 2, subTrace, 0, subTrace.size)
            e.stackTrace = subTrace
            e
        } catch (ignored: Throwable) {
            e
        }
    }

    private fun Request.bodyAsString(): String? {
        val requestBody = body ?: return null
        return try {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            val contentType = requestBody.contentType()
            val charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

            buffer.readString(charset)
        } catch (ex: Exception) {
            null
        }
    }
}

fun <T> Response<T>.mapBody(apiHelper: ApiHelper): T {
    return apiHelper.body(this) ?: error("Null response body.")
}
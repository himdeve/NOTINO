package sk.himdeve.net

import com.squareup.moshi.JsonClass

class ApiException(
    verb: String,
    val path: String,
    val errorCode: Int,
    requestBody: String?,
    val errorMessage: String?
) : RuntimeException(toMessage(verb, path, errorCode, requestBody, errorMessage)) {

    data class InvalidParameter(
        val key: String?,
        val value: String?,
        val code: String?
    )
}

private fun toMessage(
    verb: String,
    path: String,
    errorCode: Int,
    requestBody: String?,
    errorMessage: String?
): String {
    return buildString {
        append("verb '$verb'")
        append(", path '$path'")
        append(", code '$errorCode'")
        if (!requestBody.isNullOrEmpty()) {
            append(", requestBody '$requestBody'")
        }
        if (!errorMessage.isNullOrEmpty()) {
            append(", message '$errorMessage'")
        }
    }
}

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val error: Error
) {
    @JsonClass(generateAdapter = true)
    data class Error(
        val errorMessage: String?,
        val errorCode: String?,
        val parameters: List<Parameter>?,
        val retryAfter: String?
    )

    @JsonClass(generateAdapter = true)
    data class Parameter(
        val key: String?,
        val value: String?,
        val valid: Boolean,
        val code: String?
    )
}
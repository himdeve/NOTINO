package sk.himdeve.sqldelight

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.sqldelight.ColumnAdapter
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Suppress("FunctionName")
inline fun <reified T : Any> JsonColumnAdapter(moshi: Moshi): ColumnAdapter<T, String> {
    return JsonColumnAdapter(moshi, typeOf<T>())
}

class JsonColumnAdapter<T : Any>(moshi: Moshi, type: KType) : ColumnAdapter<T, String> {
    private val jsonAdapter = moshi.adapter<T>(type)

    override fun decode(databaseValue: String): T {
        return jsonAdapter.fromJson(databaseValue)!!
    }

    override fun encode(value: T): String {
        return jsonAdapter.toJson(value)
    }
}
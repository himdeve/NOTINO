package sk.himdeve.sqldelight

import com.squareup.sqldelight.ColumnAdapter
import sk.himdeve.base.Id

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class IdColumnAdapter<T : Id>(val factory: (Long) -> T) : ColumnAdapter<T, String> {
    override fun decode(databaseValue: String): T = factory(databaseValue.toLong())
    override fun encode(value: T): String = value.value.toString()
}
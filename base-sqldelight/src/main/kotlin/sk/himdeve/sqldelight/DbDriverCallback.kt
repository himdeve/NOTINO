package sk.himdeve.sqldelight

import com.squareup.sqldelight.db.SqlDriver

interface DbCreateProcessor {
    fun process(db: SqlDriver)
}

interface DbUpgradeProcessor {
    fun process(db: SqlDriver, oldVersion: Int, newVersion: Int)
}

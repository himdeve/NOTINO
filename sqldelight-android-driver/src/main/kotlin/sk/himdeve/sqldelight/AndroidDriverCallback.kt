package sk.himdeve.sqldelight

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class AndroidDriverCallback(
    private val schema: SqlDriver.Schema,
    private val createProcessors: List<DbCreateProcessor>,
    private val upgradeProcessors: List<DbUpgradeProcessor>
) : SupportSQLiteOpenHelper.Callback(schema.version) {

    override fun onCreate(db: SupportSQLiteDatabase) {
        val driver = AndroidSqliteDriver(database = db, cacheSize = 1)
        schema.create(driver)
        createProcessors.forEach { it.process(driver) }
    }

    override fun onUpgrade(
        db: SupportSQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        val driver = AndroidSqliteDriver(database = db, cacheSize = 1)
        schema.migrate(driver, oldVersion, newVersion)
        upgradeProcessors.forEach { it.process(driver, oldVersion, newVersion) }
    }
}
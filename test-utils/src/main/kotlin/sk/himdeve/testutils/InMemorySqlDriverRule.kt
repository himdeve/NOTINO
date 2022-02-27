package sk.himdeve.testutils

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.rules.ExternalResource

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
class InMemorySqlDriverRule() : ExternalResource() {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)

    override fun before() {
    }

    override fun after() {
        driver.close()
    }
}
package sk.himdeve.sqldelight

interface DaoTransactor {
    fun <T> transaction(body: () -> T): T
}
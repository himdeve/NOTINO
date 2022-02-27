package sk.himdeve.sqldelight

import com.squareup.sqldelight.Transacter

fun <Local, Remote, Id> diff(
    db: Transacter,
    localsList: () -> List<Local>,
    localIdSelector: (Local) -> Id,
    remotesList: List<Remote>,
    remoteIdSelector: (Remote) -> Id,
    applyResult: (DiffResult<Local, Remote>) -> Unit
) {
    val remotes = remotesList.associateByTo(mutableMapOf(), remoteIdSelector)

    val updates = mutableListOf<LocalRemotePair<Local, Remote>>()
    val deletes = mutableListOf<Local>()

    db.transaction {
        val locals = localsList()
        for (l in locals) {
            val lId = localIdSelector(l)
            val r = remotes[lId]
            if (r != null) {
                updates.add(LocalRemotePair(l, r))
                remotes.remove(lId)
            } else {
                deletes.add(l)
            }
        }
        applyResult(DiffResult(remotes.values.toList(), updates, deletes))
    }
}

data class DiffResult<Local, Remote>(
    val inserts: List<Remote>,
    val updates: List<LocalRemotePair<Local, Remote>>,
    val deletes: List<Local>
)

data class LocalRemotePair<Local, Remote>(val local: Local, val remote: Remote)
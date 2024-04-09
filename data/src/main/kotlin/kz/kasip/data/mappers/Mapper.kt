package kz.kasip.data.mappers

import com.google.firebase.firestore.DocumentSnapshot
import kz.kasip.data.entities.Chat
import kz.kasip.data.entities.Message
import kz.kasip.data.entities.Rubric
import kz.kasip.data.entities.Subrubric
import kz.kasip.data.entities.Work
import java.util.Date


fun List<DocumentSnapshot>.toRubrics(): List<Rubric> = map {
    it.toRubric()
}

fun DocumentSnapshot.toRubric(): Rubric = Rubric(
    getString("name") ?: "",
    (get("subrubrics") as List<Map<String, Any>>).map {
        Subrubric(
            id = it["id"] as String?,
            name = it["name"] as String,
            rubricId = it["rubricId"] as String?,
        )
    }
)

fun DocumentSnapshot.toChat(): Chat {
    return Chat(
        id,
        get("participantUserIds") as List<String>
    )
}

fun DocumentSnapshot.toMessage(): Message = Message(
    authorUserId = getString("authorUserId") ?: "",
    message = getString("message") ?: "",
    sentAt = getDate("sentAt") ?: Date(),
)

fun DocumentSnapshot.toWork(): Work = Work(
    id = id,
    name = getString("name") ?: "",
    userId = getString("userId") ?: "",
    description = getString("description") ?: "",
    price = getString("price") ?: "",
    rate = getDouble("rate") ?: 4.9,
    reviewCount = getLong("reviewCount")?.toInt() ?: 10,
    favoredBy = get("favoredBy") as List<String>,
    viewedBy = get("viewedBy") as List<String>,
    isHidden = getBoolean("isHidden") == true,
    isArchived = getBoolean("isArchived") == true,
)
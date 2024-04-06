package kz.kasip.data.mappers

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import kz.kasip.data.entities.Rubric
import kz.kasip.data.entities.Subrubric


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
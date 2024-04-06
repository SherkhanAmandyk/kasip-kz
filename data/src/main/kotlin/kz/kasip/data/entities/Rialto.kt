package kz.kasip.data.entities

import com.google.firebase.Timestamp
import java.util.Date

data class Rialto(
    val id: String,
    val rubricId: String,
    val subrubricId: String,
    val isActive: Boolean,
    val name: String,
    val description: String,
    val price: String,
    val buyerUserId: String,
    val time: Timestamp,
) {

    companion object {
        val default = Rialto(
            id = "",
            rubricId = "",
            subrubricId = "",
            isActive = true,
            name = "Translate 17 pages of text from a scan.",
            description = "Translate text in English into Russian in a short period of time.",
            price = "15000Tg",
            buyerUserId = "",
            time = Timestamp(Date())
        )
    }
}

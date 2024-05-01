package kz.kasip.rialto

import android.net.Uri
import androidx.compose.ui.text.toLowerCase
import com.google.firebase.Timestamp
import kz.kasip.data.entities.Profile
import kz.kasip.data.entities.Rubric
import kz.kasip.data.entities.Subrubric
import java.util.Date
import java.util.Locale

data class RialtoUi(
    val id: String,
    val rubric: Rubric?,
    val subrubric: Subrubric?,
    val isActive: Boolean,
    val name: String,
    val description: String,
    val price: String,
    val buyer: Profile?,
    val time: Timestamp,
    val avatar: Uri?
) {
    fun contains(text: String): Boolean {
        return name.lowercase(Locale.getDefault()).contains(text) ||
                description.lowercase(Locale.getDefault()).contains(text)
    }

    companion object {
        val default = RialtoUi(
            id = "",
            rubric = Rubric.empty,
            subrubric = Subrubric.empty,
            isActive = true,
            name = "Translate 17 pages of text from a scan.",
            description = "Translate text in English into Russian in a short period of time.",
            price = "15000Tg",
            buyer = Profile.empty,
            avatar = null,
            time = Timestamp(Date())
        )
    }
}

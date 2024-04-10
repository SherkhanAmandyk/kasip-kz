package kz.kasip.data.entities

import java.util.Date

data class RialtoOffer(
    val id: String,
    val rialtoId: String,
    val offererUserId: String,
    val price: String,
    val sentAt: Date,
) {

    companion object {
        val default = RialtoOffer(
            id = "id",
            rialtoId = "rialtoId",
            offererUserId = "offererUserId",
            price = "0",
            Date()
        )
    }
}

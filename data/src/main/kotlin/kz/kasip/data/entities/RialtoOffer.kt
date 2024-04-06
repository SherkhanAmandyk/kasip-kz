package kz.kasip.data.entities

data class RialtoOffer(
    val id: String,
    val rialtoId: String,
    val offererUserId: String,
    val price: String,
) {

    companion object {
        val default = RialtoOffer(
            id = "id",
            rialtoId = "rialtoId",
            offererUserId = "offererUserId",
            price = "0"
        )
    }
}

package kz.kasip.data.entities

data class OrderOffer(
    val id: String,
    val orderId: String,
    val offererUserId: String,
    val price: String,
) {

    companion object {
        val default = OrderOffer(
            id = "id",
            orderId = "orderId",
            offererUserId = "offererUserId",
            price = "0"
        )
    }
}

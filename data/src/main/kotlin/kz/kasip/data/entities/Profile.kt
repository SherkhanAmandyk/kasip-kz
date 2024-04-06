package kz.kasip.data.entities

data class Profile(
    val userId: String,
    val name: String,
    val info: String,
    val speciality: String,
    val city: String,
    val country: String,
    val rate: String,
) {
    companion object {
        val empty = Profile(
            userId = "userId",
            name = "name",
            info = "info",
            speciality = "speciality",
            city = "city",
            country = "country",
            rate = "5.0"
        )
    }
}

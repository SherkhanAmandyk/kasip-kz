package kz.kasip.data.entities

data class Work(
    val id: String,
    val name: String,
    val userId: String,
    val description: String,
    val price: String,
    val rate: Double,
    val reviewCount: Int,
    val favoredBy: List<String>,
    val viewedBy: List<String>,
    val isHidden: Boolean,
    val isArchived: Boolean,
)

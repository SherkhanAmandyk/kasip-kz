package kz.kasip.data.entities

data class CatalogItem(
    val id: String,
    val image: String,
    val name: String,
    val description: String,
    val price: String,
    val catalogRubricsId: String,
    val favoredBy: List<String>,
    val viewedBy: List<String>,
)

package kz.kasip.data.entities

import com.google.firebase.firestore.DocumentReference

data class CatalogRubric(
    val id:String,
    val name: String,
    val image: String,
)
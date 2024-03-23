package kz.kasip.data.entities

import com.google.firebase.Timestamp

class User(
    val id: String,
    val email: String,
    val password: String,
    val deletedAt: Timestamp?
)
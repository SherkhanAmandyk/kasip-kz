package kz.kasip.data.entities

import com.google.firebase.Timestamp

class User(
    val id: String,
    val email: String,
    val login: String,
    val phone: String,
    val password: String,
    val fcmToken: String,
    val deletedAt: Timestamp?,
)
package kz.kasip.data.mappers

import com.google.firebase.firestore.DocumentSnapshot
import kz.kasip.data.entities.User

const val userName = "name"
const val userEmail = "email"
const val userPassword = "password"
const val userDeletedAt = "deletedAt"

fun DocumentSnapshot.toUser() = User(
    id = id,
    email = getString("email") ?: "Undefined",
    password = getString("password") ?: "",
    deletedAt = getTimestamp("deletedAt")
)

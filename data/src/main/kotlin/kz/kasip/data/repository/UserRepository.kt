package kz.kasip.data.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import kz.kasip.data.Resource
import kz.kasip.data.Resource.Failure
import kz.kasip.data.Resource.Success
import kz.kasip.data.entities.Profile
import kz.kasip.data.entities.User
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.mappers.toUser
import kz.kasip.data.mappers.userEmail

class UserRepository {
    companion object {
        private const val DOCUMENT = "users"
    }

    suspend fun addUser(user: User): Resource<User> {
        return runCatching {
            Firebase.firestore.collection(DOCUMENT)
                .add(user)
                .asDeferred()
                .await()
                .get()
                .await()
                .toUser()
        }.fold(::Success, ::Failure)
    }

    suspend fun findUser(email: String): Resource<Pair<User, Profile>?> {
        return runCatching {
            Firebase.firestore.collection(DOCUMENT)
                .whereEqualTo(userEmail, email)
                .get()
                .asDeferred()
                .await()
                .let {
                    it.firstOrNull()?.toUser()?.let {
                        it to Firebase.firestore.collection("profiles")
                            .whereEqualTo("userId", it.id)
                            .get()
                            .await()
                            .first()
                            .toProfile()
                    }
                }
        }.fold(::Success, ::Failure)
    }
}
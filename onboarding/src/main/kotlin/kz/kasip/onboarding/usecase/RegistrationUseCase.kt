package kz.kasip.onboarding.usecase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kz.kasip.data.Resource.Failure
import kz.kasip.data.Resource.Success
import kz.kasip.data.entities.Profile
import kz.kasip.data.entities.User
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.data.repository.UserRepository
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.EmailAlreadyExists
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.EmailInvalid
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.Error
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.FieldsAreEmpty
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.PasswordNotSame
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.RegisteredUser
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        repeatPassword: String,
    ): RegistrationResult {
        return if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            FieldsAreEmpty
        } else if (password != repeatPassword) {
            PasswordNotSame
        } else if (!Regex("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}").matches(email)) {
            EmailInvalid
        } else {
            val existingUser = userRepository.findUser(email)
            if (existingUser is Failure) {
                Error(existingUser.e)
            } else if (existingUser is Success && existingUser.value != null) {
                EmailAlreadyExists
            } else {
                val user = userRepository.addUser(
                    User(
                        id = "",
                        email = email,
                        login = "",
                        phone = "",
                        password = password,
                        fcmToken = "",
                        null
                    )
                )
                return when (user) {
                    is Failure -> Error(user.e)
                    is Success -> {
                        val profile = Firebase.firestore.collection("profiles").add(
                            Profile(
                                id = "",
                                userId = user.value.id,
                                name = "",
                                info = "",
                                speciality = "",
                                city = "",
                                country = "",
                                rate = "5.0"
                            )
                        ).await().let {
                            it.update("id", it.id).await()
                            it
                        }
                        dataStoreRepository.saveUserId(user.value.id)
                        dataStoreRepository.saveProfileId(profile.id)
                        RegisteredUser(user.value)
                    }
                }
            }
        }
    }

    sealed class RegistrationResult {
        data object FieldsAreEmpty : RegistrationResult()
        data object PasswordNotSame : RegistrationResult()

        data class Error(val e: Throwable) : RegistrationResult()

        data object EmailAlreadyExists : RegistrationResult()
        data object EmailInvalid : RegistrationResult()

        data class RegisteredUser(val user: User) : RegistrationResult()
    }
}
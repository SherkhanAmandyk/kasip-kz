package kz.kasip.onboarding.usecase

import kz.kasip.data.Resource.Failure
import kz.kasip.data.Resource.Success
import kz.kasip.data.entities.User
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.data.repository.UserRepository
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.EmailAlreadyExists
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
                        password = password,
                        null
                    )
                )
                return when (user) {
                    is Failure -> Error(user.e)
                    is Success -> {
                        dataStoreRepository.saveUserId(user.value.id)
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

        data class RegisteredUser(val user: User) : RegistrationResult()
    }
}
package kz.kasip.onboarding.usecase

import kz.kasip.data.Resource
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.data.repository.UserRepository
import kz.kasip.onboarding.usecase.SignInUseCase.SignInResult.Error
import kz.kasip.onboarding.usecase.SignInUseCase.SignInResult.SignedIn
import kz.kasip.onboarding.usecase.SignInUseCase.SignInResult.WrongEmailOrPassword
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(email: String, password: String): SignInResult {
        val userResource = userRepository.findUser(email)
        return when (userResource) {
            is Resource.Failure -> Error(userResource.e)
            is Resource.Success -> userResource.value.let {
                when {
                    it == null || it.password != password -> WrongEmailOrPassword
                    else -> {
                        dataStoreRepository.saveUserId(it.id)
                        SignedIn
                    }
                }
            }
        }

    }

    sealed class SignInResult {
        data object WrongEmailOrPassword : SignInResult()
        data class Error internal constructor(val e: Throwable) : SignInResult()
        data object SignedIn : SignInResult()
    }
}
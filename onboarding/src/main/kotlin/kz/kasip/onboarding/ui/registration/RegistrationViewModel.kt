package kz.kasip.onboarding.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.kasip.onboarding.usecase.RegistrationUseCase
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.EmailAlreadyExists
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.EmailInvalid
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.Error
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.FieldsAreEmpty
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.PasswordNotSame
import kz.kasip.onboarding.usecase.RegistrationUseCase.RegistrationResult.RegisteredUser
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val registrationUseCase: RegistrationUseCase,
) : ViewModel() {

    val isRegistrationInProgress = MutableStateFlow(false)
    val isEmailAlreadyExistsFlow = MutableStateFlow(false)
    val isEmailInvalidFlow = MutableStateFlow(false)
    val isPasswordNotSameFlow = MutableStateFlow(false)
    val isErrorFlow = MutableStateFlow(false)
    val isFieldsEmptyFlow = MutableStateFlow(false)
    val isRegisteredFlow = MutableStateFlow(false)

    fun onRegister(
        email: String,
        password: String,
        repeatPassword: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            isRegistrationInProgress.update { true }
            val user = registrationUseCase(email, password, repeatPassword)
            when (user) {
                EmailAlreadyExists -> isEmailAlreadyExistsFlow.update { true }
                is Error -> isErrorFlow.update { true }
                FieldsAreEmpty -> isFieldsEmptyFlow.update { true }
                PasswordNotSame -> isPasswordNotSameFlow.update { true }
                is RegisteredUser -> isRegisteredFlow.update { true }
                EmailInvalid -> isEmailInvalidFlow.update { true }
            }
            isRegistrationInProgress.update { false }
        }
    }

    fun invalidateStates() {
        isEmailAlreadyExistsFlow.update { false }
        isEmailInvalidFlow.update { false }
        isPasswordNotSameFlow.update { false }
        isErrorFlow.update { false }
        isFieldsEmptyFlow.update { false }
        isRegisteredFlow.update { false }
    }
}
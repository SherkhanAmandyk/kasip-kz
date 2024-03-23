package kz.kasip.onboarding.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.kasip.onboarding.usecase.SignInUseCase
import kz.kasip.onboarding.usecase.SignInUseCase.SignInResult.Error
import kz.kasip.onboarding.usecase.SignInUseCase.SignInResult.SignedIn
import kz.kasip.onboarding.usecase.SignInUseCase.SignInResult.WrongEmailOrPassword
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {
    val isWrongEmailOrPasswordFlow = MutableStateFlow(false)
    val isErrorFlow = MutableStateFlow(false)
    val isSignedInFlow = MutableStateFlow(false)

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (signInUseCase(email, password)) {
                WrongEmailOrPassword -> isWrongEmailOrPasswordFlow.update { true }
                is Error -> isErrorFlow.update { true }
                SignedIn -> isSignedInFlow.update { true }
            }
        }
    }

    fun invalidateStates() {
        isWrongEmailOrPasswordFlow.update { false }
        isErrorFlow.update { false }
        isSignedInFlow.update { false }
    }
}
package kz.kasip.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kz.kasip.usecase.LogOutUseCase

class MainViewModel(
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {
    val isLoggedOut = MutableStateFlow(false)
    fun logOut() {
        logOutUseCase()
        isLoggedOut.update { true }
    }
}
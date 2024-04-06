package kz.kasip.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kz.kasip.usecase.LogOutUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {
    val isLoggedOut = MutableStateFlow(false)
    fun logOut() {
        logOutUseCase()
        isLoggedOut.update { true }
    }
}
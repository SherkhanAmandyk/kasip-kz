package kz.kasip.rialto.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainRialtoViewModel @Inject constructor() : ViewModel() {
    val selectedTabIndexFlow = MutableStateFlow(0)

    fun onSelectedTabChange(newValue: Int) {
        selectedTabIndexFlow.update { newValue }
    }
}
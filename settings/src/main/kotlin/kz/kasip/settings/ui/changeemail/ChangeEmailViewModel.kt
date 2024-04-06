package kz.kasip.settings.ui.changeemail

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChangeEmailViewModel @Inject constructor(

) : ViewModel() {
    val textFlow = MutableStateFlow(TextFieldValue(text = ""))

    fun onTextChange(newText: TextFieldValue) {
        textFlow.update { newText }
    }

    fun onSave() {

    }
}
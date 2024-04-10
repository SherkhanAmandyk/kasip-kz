package kz.kasip.settings.ui.changepassword

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.mappers.toUser
import kz.kasip.data.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val text1Flow = MutableStateFlow(TextFieldValue(text = ""))
    val text2Flow = MutableStateFlow(TextFieldValue(text = ""))

    val isEmailInvalidFlow = MutableStateFlow(false)

    fun onText1Change(newText: TextFieldValue) {
        text1Flow.update { newText }
    }

    fun onText2Change(newText: TextFieldValue) {
        text2Flow.update { newText }
    }


    fun onSave() {
        val newPassword1 = text1Flow.value.text
        val newPassword2 = text2Flow.value.text
        if (newPassword1 != newPassword2) {
            isEmailInvalidFlow.update { true }
        } else {
            viewModelScope.launch {
                Firebase.firestore.document("users/${dataStoreRepository.getUserId()}")
                    .update("password", newPassword1)
                    .await()
            }
        }
    }
}
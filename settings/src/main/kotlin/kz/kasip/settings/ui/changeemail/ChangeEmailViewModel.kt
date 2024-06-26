package kz.kasip.settings.ui.changeemail

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
class ChangeEmailViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val textFlow = MutableStateFlow(TextFieldValue(text = ""))

    val isEmailInvalidFlow = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            textFlow.update {
                TextFieldValue(
                    text = Firebase.firestore.document("users/${dataStoreRepository.getUserId()}")
                        .get()
                        .await()
                        .toUser()
                        .email
                )
            }
        }
    }

    fun onTextChange(newText: TextFieldValue) {
        textFlow.update { newText }
    }

    fun onSave() {
        val newEmail = textFlow.value.text
        if (!Regex("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}").matches(newEmail)) {
            isEmailInvalidFlow.update { true }
        } else {
            viewModelScope.launch {
                Firebase.firestore.document("users/${dataStoreRepository.getUserId()}")
                    .update("email", newEmail)
                    .await()
            }
        }
    }
}
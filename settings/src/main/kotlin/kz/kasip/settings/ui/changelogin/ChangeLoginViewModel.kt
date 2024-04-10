package kz.kasip.settings.ui.changelogin

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
class ChangeLoginViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val textFlow = MutableStateFlow(TextFieldValue(text = ""))

    init {
        viewModelScope.launch {
            textFlow.update {
                TextFieldValue(
                    text = Firebase.firestore.document("users/${dataStoreRepository.getUserId()}")
                        .get()
                        .await()
                        .toUser()
                        .login
                )
            }
        }
    }

    fun onTextChange(newText: TextFieldValue) {
        textFlow.update { newText }
    }

    fun onSave() {
        val newLogin = textFlow.value.text
        viewModelScope.launch {
            Firebase.firestore.document("users/${dataStoreRepository.getUserId()}")
                .update("login", newLogin)
                .await()
        }
    }
}
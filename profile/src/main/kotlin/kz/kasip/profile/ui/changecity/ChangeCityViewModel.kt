package kz.kasip.profile.ui.changecity

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
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class ChangeCityViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val textFlow = MutableStateFlow(TextFieldValue(text = ""))

    init {
        viewModelScope.launch {
            textFlow.update {
                TextFieldValue(
                    text = Firebase.firestore.document("profiles/${dataStoreRepository.getProfileId()}")
                        .get()
                        .await()
                        .toProfile()
                        .city
                )
            }
        }
    }
    fun onText1Change(newText: TextFieldValue) {
        textFlow.update { newText }
    }

    fun onSave() {
        val newCity1 = textFlow.value.text
        viewModelScope.launch {
            Firebase.firestore.document("profiles/${dataStoreRepository.getProfileId()}")
                .update("city", newCity1)
                .await()
        }
    }
}
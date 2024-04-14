package kz.kasip.profile.ui.changespeciality

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
class ChangeSpecialityViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val textFlow = MutableStateFlow(TextFieldValue(text = ""))

    val isSpecialityInvalidFlow = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            textFlow.update {
                TextFieldValue(
                    text = Firebase.firestore.document("profiles/${dataStoreRepository.getProfileId()}")
                        .get()
                        .await()
                        .toProfile()
                        .speciality
                )
            }
        }
    }

    fun onTextChange(newText: TextFieldValue) {
        textFlow.update { newText }
    }

    fun onSave() {
        val newSpeciality = textFlow.value.text
        viewModelScope.launch {
            Firebase.firestore.document("profiles/${dataStoreRepository.getProfileId()}")
                .update("speciality", newSpeciality)
                .await()
        }
    }
}
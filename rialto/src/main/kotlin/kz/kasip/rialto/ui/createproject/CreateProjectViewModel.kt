package kz.kasip.rialto.ui.createproject

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.Rialto
import kz.kasip.data.repository.DataStoreRepository
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateProjectViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val validationFlow = MutableStateFlow<Validation>(Validation.OK)
    val projectNameFlow = MutableStateFlow(TextFieldValue(""))
    val projectDescriptionFlow = MutableStateFlow(TextFieldValue(""))
    val rubricFlow = MutableStateFlow(TextFieldValue(""))
    val priceFlow = MutableStateFlow(TextFieldValue(""))
    val isCreatedFlow = MutableStateFlow(false)

    fun onProjectNameChange(newValue: TextFieldValue) {
        projectNameFlow.update { newValue }
    }

    fun onProjectDescriptionChange(newValue: TextFieldValue) {
        projectDescriptionFlow.update { newValue }
    }

    fun onPriceChange(newValue: TextFieldValue) {
        priceFlow.update { newValue }
    }

    fun onCreateProject() {
        if (projectNameFlow.value.text.isBlank()) {
            validationFlow.update { Validation.NoProjectName }
            return
        }
        if (projectDescriptionFlow.value.text.isBlank()) {
            validationFlow.update { Validation.NoProjectDescription }
            return
        }
        if (priceFlow.value.text.isBlank()) {
            validationFlow.update { Validation.NoPrice }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("rialto").add(
                Rialto(
                    id = "",
                    buyerUserId = dataStoreRepository.getUserId() ?: "",
                    rubricId = "Ws1f5HM0JXxP4oqhyh8N",
                    subrubricId = "B6CZmmaoruhaa44W4U7y",
                    isActive = true,
                    name = projectNameFlow.value.text,
                    description = projectDescriptionFlow.value.text,
                    price = "${priceFlow.value.text}Tg",
                    time = Timestamp(Date()),
                )
            ).await().let {
                it.update("id", it.id).await()
                isCreatedFlow.update { true }
            }
        }
    }

    fun invalidateStates() {
        validationFlow.update { Validation.OK }
    }
}

sealed class Validation {
    data object NoProjectName : Validation()
    data object NoProjectDescription : Validation()
    data object NoPrice : Validation()
    data object OK : Validation()
}
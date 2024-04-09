package kz.kasip.order.ui.createproject

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
import kz.kasip.data.entities.Order
import kz.kasip.data.entities.Rubric
import kz.kasip.data.entities.Subrubric
import kz.kasip.data.mappers.toRubric
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
    val priceFlow = MutableStateFlow(TextFieldValue(""))
    val isCreatedFlow = MutableStateFlow(false)

    val selectSubrubricFlow = MutableStateFlow(false)
    val rubricsFlow = MutableStateFlow(emptyList<Rubric>())
    val selectedSubrubricFlow = MutableStateFlow<Subrubric?>(null)

    init {
        Firebase.firestore.collection("rubrics")
            .addSnapshotListener { value, error ->
                viewModelScope.launch(Dispatchers.IO) {
                    val rubrics = value?.documents?.map {
                        it.toRubric()
                    }
                    rubricsFlow.update { rubrics ?: emptyList() }
                }
            }
    }

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
        if (selectedSubrubricFlow.value == null) {
            validationFlow.update { Validation.NoPrice }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("order").add(
                Order(
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

    fun onSubrubricSelected(subrubric: Subrubric) {
        selectedSubrubricFlow.update { subrubric }
    }

    fun invalidateStates() {
        selectSubrubricFlow.update { false }
        validationFlow.update { Validation.OK }
        isCreatedFlow.update { false }
    }
}

sealed class Validation {
    data object NoProjectName : Validation()
    data object NoProjectDescription : Validation()
    data object NoPrice : Validation()
    data object OK : Validation()
}
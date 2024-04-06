package kz.kasip.rialto.ui.salesperson

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.rialto.RialtoUi
import kz.kasip.rialto.RialtoUi.Companion.default
import kz.kasip.rialto.ui.main.salesperson.toRilatoUi
import javax.inject.Inject

@HiltViewModel
class SalespersonViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val rialtosFlow = MutableStateFlow(emptyList<RialtoUi>())
    val searchTextFlow = MutableStateFlow(TextFieldValue(""))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            rialtosFlow.update {
                Firebase.firestore.collection("rialto")
                    .whereNotEqualTo("buyerUserId", dataStoreRepository.getUserId())
                    .get()
                    .await()
                    .documents.map {
                        it.toRilatoUi()
                    }
            }
        }
    }

    fun onSearchTextChange(textFieldValue: TextFieldValue) {
        searchTextFlow.update { textFieldValue }
    }
}
package kz.kasip.rialto.ui.salesperson

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.kasip.data.entities.RialtoOffer
import kz.kasip.data.entities.Rubric
import kz.kasip.data.mappers.toRubric
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.rialto.RialtoUi
import kz.kasip.rialto.ui.main.salesperson.toRilatoUi
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SalespersonViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    private val _rialtosFlow = MutableStateFlow<List<RialtoUi>>(emptyList())
    val searchTextFlow = MutableStateFlow(TextFieldValue(""))
    val rubricsFlow = MutableStateFlow(emptyList<Rubric>())
    val selectedSubrubricIdsFlow = MutableStateFlow<List<String>>(emptyList())
    val rialtoOffersFlow = MutableStateFlow(emptyList<RialtoOffer>())
    var selectedRialtoUi: RialtoUi? = null

    val rialtosFlow = combine(
        _rialtosFlow,
        searchTextFlow,
        selectedSubrubricIdsFlow,
        rialtoOffersFlow.map { entry -> entry.map { it.rialtoId } }
    ) { rialtos, searchText, selectedSubrubricIds, rialtoOffers ->
        rialtos.filter {
            (searchText.text.isEmpty() ||
                    it.contains(searchText.text.lowercase(Locale.getDefault()))) &&
                    ((selectedSubrubricIds.isEmpty()) ||
                            it.subrubric?.id in selectedSubrubricIds) &&
                    it.id !in rialtoOffers
        }
    }.flowOn(Dispatchers.Default)

    init {
        Firebase.firestore.collection("rialto")
            .whereNotEqualTo("buyerUserId", dataStoreRepository.getUserId())
            .addSnapshotListener { value, error ->
                viewModelScope.launch(Dispatchers.IO) {
                    value?.documents?.map { it.toRilatoUi() }?.let { rialtos ->
                        _rialtosFlow.update { rialtos }
                    }
                }
            }


        Firebase.firestore.collection("rubrics")
            .addSnapshotListener { value, error ->
                viewModelScope.launch(Dispatchers.IO) {
                    val rubrics = value?.documents?.map {
                        it.toRubric()
                    }
                    rubricsFlow.update { rubrics ?: emptyList() }
                }
            }
        Firebase.firestore.collection("rialtoOffers")
            .addSnapshotListener { value, error ->
                rialtoOffersFlow.update {
                    value?.documents?.map { it.toRialtoOffers() }
                        ?.filter { it.offererUserId == dataStoreRepository.getUserId() }
                        ?: emptyList()
                }
            }
    }

    fun onSearchTextChange(textFieldValue: TextFieldValue) {
        searchTextFlow.update { textFieldValue }
    }

    fun onSubrubricSelected(subrubricId: String) {
        selectedSubrubricIdsFlow.update { prevVal ->
            if (prevVal.any { it == subrubricId }) {
                prevVal.filter { it != subrubricId }
            } else {
                prevVal + subrubricId
            }
        }
    }
}

private fun DocumentSnapshot.toRialtoOffers(): RialtoOffer = RialtoOffer(
    id = id,
    rialtoId = getString("rialtoId") ?: "",
    offererUserId = getString("offererUserId") ?: "",
    price = getString("price") ?: "",
    sentAt = getDate("sentAt") ?: Date()
)

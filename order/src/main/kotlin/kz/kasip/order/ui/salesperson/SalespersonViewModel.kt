package kz.kasip.order.ui.salesperson

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
import kz.kasip.data.entities.OrderOffer
import kz.kasip.data.entities.Rubric
import kz.kasip.data.mappers.toRubric
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.order.OrderUi
import kz.kasip.order.ui.main.salesperson.toRilatoUi
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SalespersonViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    private val _ordersFlow = MutableStateFlow<List<OrderUi>>(emptyList())
    val searchTextFlow = MutableStateFlow(TextFieldValue(""))
    val rubricsFlow = MutableStateFlow(emptyList<Rubric>())
    val selectedSubrubricIdsFlow = MutableStateFlow<List<String>>(emptyList())
    val orderOffersFlow = MutableStateFlow(emptyList<OrderOffer>())
    var selectedOrderUi: OrderUi? = null

    val ordersFlow = combine(
        _ordersFlow,
        searchTextFlow,
        selectedSubrubricIdsFlow,
        orderOffersFlow.map { entry -> entry.map { it.orderId } }
    ) { orders, searchText, selectedSubrubricIds, orderOffers ->
        orders.filter {
            (searchText.text.isEmpty() ||
                    it.contains(searchText.text.lowercase(Locale.getDefault()))) &&
                    ((selectedSubrubricIds.isEmpty()) ||
                            it.subrubric?.id in selectedSubrubricIds) &&
                    it.id !in orderOffers
        }
    }.flowOn(Dispatchers.Default)

    init {
        Firebase.firestore.collection("order")
            .whereNotEqualTo("buyerUserId", dataStoreRepository.getUserId())
            .addSnapshotListener { value, error ->
                viewModelScope.launch(Dispatchers.IO) {
                    value?.documents?.map { it.toRilatoUi() }?.let { orders ->
                        _ordersFlow.update { orders }
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
        Firebase.firestore.collection("orderOffers")
            .addSnapshotListener { value, error ->
                orderOffersFlow.update {
                    value?.documents?.map { it.toOrderOffers() }
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

private fun DocumentSnapshot.toOrderOffers(): OrderOffer = OrderOffer(
    id = id,
    orderId = getString("orderId") ?: "",
    offererUserId = getString("offererUserId") ?: "",
    price = getString("price") ?: ""
)

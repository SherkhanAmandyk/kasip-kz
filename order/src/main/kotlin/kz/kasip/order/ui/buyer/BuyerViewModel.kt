package kz.kasip.order.ui.main.salesperson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.Rubric
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.mappers.toRubric
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.order.OrderUi
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val myOrdersFlow = MutableStateFlow(emptyList<OrderUi>())

    init {
        Firebase.firestore.collection("order")
            .where(Filter.equalTo("buyerUserId", dataStoreRepository.getUserId()))
            .addSnapshotListener { value, error ->
                viewModelScope.launch(Dispatchers.IO) {
                    val orders = value?.documents?.map {
                        it.toRilatoUi()
                    }
                    myOrdersFlow.update { orders ?: emptyList() }
                }
            }
    }
}


suspend fun DocumentSnapshot.toRilatoUi(): OrderUi {
    val rubric = Firebase.firestore.collection("rubrics")
        .document(getString("rubricId") ?: "")
        .get()
        .await()
        .toRubric()
    val subrubric = rubric.subrubrics.firstOrNull { it.id == getString("subrubricId") }
    val buyer = Firebase.firestore.collection("profiles")
        .whereEqualTo("userId", getString("buyerUserId") ?: "")
        .get()
        .await()
        .documents.first()
        .toProfile()
    return OrderUi(
        id = getString("id") ?: "",
        rubric = rubric,
        subrubric = subrubric,
        isActive = getBoolean("isActive") == true,
        name = getString("name") ?: "",
        description = getString("description") ?: "",
        price = getString("price") ?: "",
        buyer = buyer,
        time = getTimestamp("time") ?: Timestamp(Date()),
    )
}

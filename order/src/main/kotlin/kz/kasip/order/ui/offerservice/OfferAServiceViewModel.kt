package kz.kasip.order.ui.offerservice

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.OrderOffer
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.order.OrderUi

@HiltViewModel(assistedFactory = OfferAServiceViewModelFactory::class)
class OfferAServiceViewModel @AssistedInject constructor(
    @Assisted private val orderUi: OrderUi,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val orderUiFlow = MutableStateFlow(OrderUi.default)
    val offerTextFlow = MutableStateFlow(TextFieldValue(""))
    val priceFlow = MutableStateFlow(TextFieldValue(""))
    val isPriceAbsent = MutableStateFlow(false)
    val isCreated = MutableStateFlow(false)

    fun onOfferTextChange(newValue: TextFieldValue) {
        offerTextFlow.update { newValue }
    }

    fun onPriceChange(newValue: TextFieldValue) {
        priceFlow.update { newValue }
    }

    fun onOffer() {
        if (priceFlow.value.text.isBlank() || priceFlow.value.text.toFloat() < 1) {
            isPriceAbsent.update { true }
        }
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("orderOffers")
                .add(
                    OrderOffer(
                        id = "",
                        orderId = orderUi.id,
                        offererUserId = dataStoreRepository.getUserId() ?: "",
                        priceFlow.value.text
                    )
                )
                .await()
                .let {
                    it.update("id", it.id).await()
                    isCreated.update { true }
                }
        }
    }

    fun invalidate() {
        isPriceAbsent.update { false }
        isCreated.update { false }
    }
}
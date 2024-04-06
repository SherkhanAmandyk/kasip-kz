package kz.kasip.rialto.ui.offerservice

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kz.kasip.rialto.RialtoUi
import javax.inject.Inject

@HiltViewModel
class OfferAServiceViewModel @Inject constructor() : ViewModel() {
    val rialtoUiFlow = MutableStateFlow(RialtoUi.default)
    val offerTextFlow = MutableStateFlow(TextFieldValue(""))
    val priceFlow = MutableStateFlow(TextFieldValue(""))

    fun onOfferTextChange(newValue: TextFieldValue) {
        offerTextFlow.update { newValue }
    }

    fun onPriceChange(newValue: TextFieldValue) {
        priceFlow.update { newValue }
    }

    fun onOffer() {

    }
}
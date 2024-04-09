package kz.kasip.order.ui.offerservice

import dagger.assisted.AssistedFactory
import kz.kasip.order.OrderUi

@AssistedFactory
interface OfferAServiceViewModelFactory {
    fun create(orderUi: OrderUi): OfferAServiceViewModel
}
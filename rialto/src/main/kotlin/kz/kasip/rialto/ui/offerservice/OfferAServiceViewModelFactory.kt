package kz.kasip.rialto.ui.offerservice

import dagger.assisted.AssistedFactory
import kz.kasip.rialto.RialtoUi

@AssistedFactory
interface OfferAServiceViewModelFactory {
    fun create(rialtoUi: RialtoUi): OfferAServiceViewModel
}
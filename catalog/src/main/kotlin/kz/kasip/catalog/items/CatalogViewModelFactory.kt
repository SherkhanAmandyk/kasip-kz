package kz.kasip.catalog.items

import dagger.assisted.AssistedFactory

@AssistedFactory
interface CatalogViewModelFactory {
    fun create(rubricId: String): CatalogViewModel
}
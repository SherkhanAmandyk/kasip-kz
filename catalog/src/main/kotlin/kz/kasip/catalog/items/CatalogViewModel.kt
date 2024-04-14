package kz.kasip.catalog.items

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.CatalogItem
import kz.kasip.data.mappers.toCatalogItem
import kz.kasip.data.repository.DataStoreRepository

@HiltViewModel(assistedFactory = CatalogViewModelFactory::class)
class CatalogViewModel @AssistedInject constructor(
    @Assisted val rubricId: String,
    val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val myUserId = dataStoreRepository.getUserId()

    fun changeFavorite(catalogItem: CatalogItem) {
        Firebase.firestore.collection("catalog-item").document(catalogItem.id).update(
            "favoredBy",
            if (catalogItem.favoredBy.contains(myUserId)) {
                FieldValue.arrayRemove(myUserId)
            } else {
                FieldValue.arrayUnion(myUserId)
            }
        )
    }

    val catalogItems = MutableStateFlow<List<CatalogItem>>(emptyList())
    val catalogItemsWithImage: Flow<List<Triple<CatalogItem, Uri, Boolean>>>

    init {
        val myUserId = dataStoreRepository.getUserId()
        Firebase.firestore.collection("catalog-item").addSnapshotListener { value, error ->
            value?.map(QueryDocumentSnapshot::toCatalogItem)?.filter {
                it.catalogRubricsId == rubricId
            }?.let { catalogRubrics ->
                catalogItems.update {
                    catalogRubrics
                }
            }
        }
        catalogItemsWithImage = catalogItems.map {
            it.map {
                Triple(
                    it,
                    Firebase.storage.getReferenceFromUrl(it.image).downloadUrl.await(),
                    it.favoredBy.contains(myUserId)
                )
            }
        }
    }
}
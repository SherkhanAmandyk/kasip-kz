package kz.kasip.catalog.favorite

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.CatalogItem
import kz.kasip.data.mappers.toCatalogItem
import kz.kasip.data.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val myUserId = dataStoreRepository.getUserId()
    val itemsFlow = MutableStateFlow<List<CatalogItem>>(emptyList())
    val itemsWithImageFlow: Flow<List<Triple<CatalogItem, Uri, Boolean>>> = itemsFlow.map {
        it.map {
            Triple(
                it,
                Firebase.storage.getReferenceFromUrl(it.image).downloadUrl.await(),
                it.favoredBy.contains(myUserId)
            )
        }
    }

    init {
        Firebase.firestore.collection("catalog-item").addSnapshotListener { value, error ->
            value?.map(QueryDocumentSnapshot::toCatalogItem)?.filter {
                it.favoredBy.contains(myUserId)
            }?.let { catalogRubrics ->
                itemsFlow.update {
                    catalogRubrics
                }
            }
        }
    }

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
}
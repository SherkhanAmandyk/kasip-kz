package kz.kasip.catalog.rubrics

import android.net.Uri
import androidx.lifecycle.ViewModel
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
import kz.kasip.data.entities.CatalogRubric
import kz.kasip.data.mappers.toCatalogRubric
import javax.inject.Inject

@HiltViewModel
class CatalogRubricsViewModel @Inject constructor() : ViewModel() {

    val catalogRubricsFlow = MutableStateFlow<List<CatalogRubric>>(emptyList())
    val catalogImagesMapFlow: Flow<List<Pair<CatalogRubric, Uri>>>

    init {
        Firebase.firestore.collection("catalog-rubrics").addSnapshotListener { value, error ->
            value?.map(QueryDocumentSnapshot::toCatalogRubric)?.let { catalogRubrics ->
                catalogRubricsFlow.update {
                    catalogRubrics
                }
            }
        }
        catalogImagesMapFlow = catalogRubricsFlow.map { catalogRubrics ->
            catalogRubrics.map {
                it to Firebase.storage.getReferenceFromUrl(it.image).downloadUrl.await()
            }
        }
    }
}
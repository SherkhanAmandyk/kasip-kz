package kz.kasip.response.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.Rialto
import kz.kasip.data.entities.RialtoOffer
import kz.kasip.data.mappers.toRialto
import kz.kasip.data.mappers.toRialtoOffer
import kz.kasip.data.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class MyResponsesViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val responsesFlow = MutableStateFlow<List<Pair<Rialto, RialtoOffer>>>(emptyList())

    init {
        Firebase.firestore.collection("rialtoOffers")
            .whereEqualTo("offererUserId", dataStoreRepository.getUserId())
            .addSnapshotListener { value, error ->
                viewModelScope.launch {
                    responsesFlow.update {
                        value?.documents?.map {
                            it.toRialtoOffer()
                        }?.map {
                            Firebase.firestore.collection("rialto").document(it.rialtoId).get()
                                .await().toRialto() to it
                        } ?: emptyList()
                    }
                }
            }
    }

    fun onDelete(it: RialtoOffer) {
        Firebase.firestore.collection("rialtoOffers").document(it.id).delete()
    }
}
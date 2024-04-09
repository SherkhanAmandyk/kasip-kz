package kz.kasip.works.ui.hidden

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kz.kasip.data.entities.Work
import kz.kasip.data.mappers.toWork
import kz.kasip.data.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class HiddenWorksViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val works = MutableStateFlow<List<Work>>(emptyList())

    init {
        Firebase.firestore.collection("works")
            .whereEqualTo("hidden", true)
            .whereEqualTo("userId", dataStoreRepository.getUserId())
            .addSnapshotListener { value, error ->
                works.update {
                    value?.documents?.map {
                        it.toWork()
                    } ?: emptyList()
                }
            }
    }
}
package kz.kasip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.RootActivity.Companion.main
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.onboarding.navigation.onboarding
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    var start: String = if (dataStoreRepository.getUserId() == null) {
        onboarding
    } else {
        main
    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val docs =
                Firebase.firestore.collection("rubrics").get().await().documents
            println(docs)
        }
    }
}


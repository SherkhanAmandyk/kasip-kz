package kz.kasip.rialto.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.Rubric
import kz.kasip.data.mappers.toRubrics
import javax.inject.Inject

@HiltViewModel
class SalespersonFilterViewModel @Inject constructor() : ViewModel() {
    val rubricsFlow = MutableStateFlow(emptyList<Rubric>())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            rubricsFlow.emit(
                Firebase.firestore.collection("rubrics")
                    .get()
                    .await()
                    .documents.toRubrics()
            )
        }
    }
}

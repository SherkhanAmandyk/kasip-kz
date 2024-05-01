package kz.kasip.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.LogOutUseCase
import kz.kasip.data.entities.Profile
import kz.kasip.data.entities.User
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    val userFlow = MutableStateFlow<User?>(null)
    val profileFlow = MutableStateFlow<Profile?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val avatarFlow = profileFlow.filterNotNull().mapLatest {
        runCatching {
            Firebase.storage.getReferenceFromUrl("gs://kasip-kz.appspot.com/avatar/${dataStoreRepository.getUserId()}").downloadUrl.await()
        }.onFailure {
            println("ERROR::: ")
            it.printStackTrace()
        }.getOrNull().also {
            println(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    val isLoggedOut = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            profileFlow.update {
                Firebase.firestore.collection("users")
                    .document(dataStoreRepository.getUserId() ?: "")
                    .get()
                    .await()
                    .toProfile()
            }
            profileFlow.update {
                Firebase.firestore.collection("profiles")
                    .document(dataStoreRepository.getProfileId() ?: "")
                    .get()
                    .await()
                    .toProfile()
            }
        }
    }

    fun logOut() {
        logOutUseCase()
        isLoggedOut.update { true }
    }
}
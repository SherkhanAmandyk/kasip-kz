package kz.kasip.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.Profile
import kz.kasip.data.entities.User
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.data.LogOutUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    val userFlow = MutableStateFlow<User?>(null)
    val profileFlow = MutableStateFlow<Profile?>(null)

    val isLoggedOut = MutableStateFlow(false)

    init {

        viewModelScope.launch {
            profileFlow.update {
                Firebase.firestore.collection("profiles")
                    .document(dataStoreRepository.getUserId() ?: "")
                    .get()
                    .await()
                    .toProfile()
            }
            userFlow.collect {
                it?.let { user ->
                    profileFlow.update {
                        Firebase.firestore.collection("profiles")
                            .document(user.id)
                            .get()
                            .await()
                            .toProfile()
                    }
                }
            }
        }
    }

    fun logOut() {
        logOutUseCase()
        isLoggedOut.update { true }
    }
}
package kz.kasip.settings.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.kasip.data.LogOutUseCase
import kz.kasip.data.mappers.toUser
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.designcore.Lang.eng
import kz.kasip.designcore.Lang.kz
import kz.kasip.designcore.Lang.lang
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val appLangFlow = MutableStateFlow(lang)
    val settingsList = MutableStateFlow(
        listOf(
            Setting.Email(value = "mail@mail.com"),
            Setting.Login(value = "Username"),
            Setting.Phone(value = "+7**********"),
            Setting.Password(value = ""),
        )
    )
    val showDeleteDialog = MutableStateFlow(false)
    val isDeleted = MutableStateFlow(false)

    init {
        Firebase.firestore.document("users/${dataStoreRepository.getUserId()}")
            .addSnapshotListener { value, error ->
                value?.toUser()?.let { user ->
                    settingsList.update {
                        listOf(
                            Setting.Email(value = user.email),
                            Setting.Login(value = user.login),
                            Setting.Phone(value = user.phone),
                            Setting.Password(value = user.password),
                        )
                    }
                }
            }
        viewModelScope.launch {
            appLangFlow.collect {
                lang = it
                dataStoreRepository.saveLang(
                    if (it == kz) {
                        "kz"
                    } else {
                        "eng"
                    }
                )
            }
        }
    }

    fun deleteAccount() {
        val userId = dataStoreRepository.getUserId()
        logOutUseCase()
        Firebase.firestore.collection("users")
            .document(userId ?: "")
            .delete()
        isDeleted.update { true }
    }

    fun changeLang() {
        if (lang == kz) {
            appLangFlow.update { eng }
        } else {
            appLangFlow.update { kz }
        }
    }
}

sealed class Setting(
    open val name: String,
    open val value: String,
) {
    data class Email(
        override val value: String,
    ) : Setting("Email", value)

    data class Login(
        override val value: String,
    ) : Setting("Login", value)

    data class Phone(
        override val value: String,
    ) : Setting("Phone", value)

    data class Password(
        override val value: String,
    ) : Setting("Change Password", value)
}
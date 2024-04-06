package kz.kasip.settings.ui.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    val settingsList = MutableStateFlow(
        listOf(
            Setting.Email(value = "mail@mail.com", route = "editEmail"),
            Setting.Login(value = "Username", route = "editLogin"),
            Setting.Phone(value = "+7**********", route = "editPhone"),
            Setting.ChangePassword(value = "", route = "editPassword"),
        )
    )
}

sealed class Setting(
    open val name: String,
    open val value: String,
    open val route: String,
) {
    data class Email(
        override val value: String,
        override val route: String,
    ) : Setting("Email", value, route)

    data class Login(
        override val value: String,
        override val route: String,
    ) : Setting("Login", value, route)

    data class Phone(
        override val value: String,
        override val route: String,
    ) : Setting("Phone", value, route)

    data class ChangePassword(
        override val value: String,
        override val route: String,
    ) : Setting("Change Password", value, route)
}
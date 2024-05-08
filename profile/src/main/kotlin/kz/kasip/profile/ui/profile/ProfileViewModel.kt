package kz.kasip.profile.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.change_city
import kz.kasip.designcore.change_country
import kz.kasip.designcore.change_info_about_me
import kz.kasip.designcore.change_name
import kz.kasip.designcore.change_speciality
import kz.kasip.designcore.city
import kz.kasip.designcore.country
import kz.kasip.designcore.info_about_me
import kz.kasip.designcore.name
import kz.kasip.designcore.speciality
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val profileList = MutableStateFlow(
        listOf(
            ProfileInfo.Name(value = "mail@mail.com"),
            ProfileInfo.Bio(value = "Username"),
            ProfileInfo.Speciality(value = "+7**********"),
            ProfileInfo.City(value = ""),
            ProfileInfo.Country(value = ""),
        )
    )
    val showDeleteDialog = MutableStateFlow(false)
    val isDeleted = MutableStateFlow(false)

    init {
        Firebase.firestore.document("profiles/${dataStoreRepository.getProfileId()}")
            .addSnapshotListener { value, error ->
                value?.toProfile()?.let { profile ->
                    profileList.update {
                        listOf(
                            ProfileInfo.Name(value = profile.name),
                            ProfileInfo.Bio(value = profile.info),
                            ProfileInfo.Speciality(value = profile.speciality),
                            ProfileInfo.City(value = profile.city),
                            ProfileInfo.Country(value = profile.country),

                            )
                    }
                }
            }
    }
}

sealed class ProfileInfo(
    open val name: String,
    open val value: String,
) {
    data class Name(
        override val value: String,
    ) : ProfileInfo(lang[change_name] ?: "", value)

    data class Bio(
        override val value: String,
    ) : ProfileInfo(lang[change_info_about_me] ?: "", value)

    data class Speciality(
        override val value: String,
    ) : ProfileInfo(lang[change_speciality] ?: "", value)

    data class City(
        override val value: String,
    ) : ProfileInfo(lang[change_city] ?: "", value)

    data class Country(
        override val value: String,
    ) : ProfileInfo(lang[change_country] ?: "", value)
}
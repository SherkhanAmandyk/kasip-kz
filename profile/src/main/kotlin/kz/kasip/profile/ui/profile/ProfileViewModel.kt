package kz.kasip.profile.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.repository.DataStoreRepository
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
    ) : ProfileInfo("Name", value)

    data class Bio(
        override val value: String,
    ) : ProfileInfo("Bio", value)

    data class Speciality(
        override val value: String,
    ) : ProfileInfo("Speciality", value)

    data class City(
        override val value: String,
    ) : ProfileInfo("City", value)

    data class Country(
        override val value: String,
    ) : ProfileInfo("Country", value)
}
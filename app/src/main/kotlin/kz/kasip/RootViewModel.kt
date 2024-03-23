package kz.kasip

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
}


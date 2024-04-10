package kz.kasip.data

import kz.kasip.data.repository.DataStoreRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke() {
        dataStoreRepository.clear()
    }
}
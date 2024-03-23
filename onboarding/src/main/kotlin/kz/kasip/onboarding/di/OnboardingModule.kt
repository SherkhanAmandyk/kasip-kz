package kz.kasip.onboarding.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.data.repository.UserRepository
import kz.kasip.onboarding.usecase.RegistrationUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OnboardingModule {

    @Singleton
    @Provides
    fun registerUseCase(
        userRepository: UserRepository,
        dataStoreRepository: DataStoreRepository,
    ): RegistrationUseCase {
        return RegistrationUseCase(userRepository, dataStoreRepository)
    }
}
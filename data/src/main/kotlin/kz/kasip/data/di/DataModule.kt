package kz.kasip.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.data.repository.UserRepository


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun dataStoreRepository(sharedPreferences: SharedPreferences): DataStoreRepository {
        return DataStoreRepository(sharedPreferences)
    }

    @Provides
    fun userRepository(): UserRepository {
        return UserRepository()
    }

    @Provides
    fun dataStore(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences("kasip", Context.MODE_PRIVATE)
    }
}
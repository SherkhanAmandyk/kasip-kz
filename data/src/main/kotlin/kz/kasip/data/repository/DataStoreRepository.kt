package kz.kasip.data.repository

import android.content.SharedPreferences


class DataStoreRepository(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val userId = "userId"
        private const val profileId = "profileId"
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(userId, null)
    }

    fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(Companion.userId, userId).apply()
    }

    fun getProfileId(): String? {
        return sharedPreferences.getString(profileId, null)
    }

    fun saveProfileId(profileId: String) {
        sharedPreferences.edit().putString(Companion.profileId, profileId).apply()
    }

    fun clear() {
        return sharedPreferences.edit().clear().apply()
    }

    fun isNotified(id: String): Boolean {
        return sharedPreferences.getBoolean(id, false).also {
            sharedPreferences.edit().putBoolean(id, true).apply()
        }
    }
}
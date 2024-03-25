package kz.kasip.data.repository

import android.content.SharedPreferences


class DataStoreRepository(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val userId = "userId"
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(userId, null)
    }

    fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(Companion.userId, userId).apply()
    }

    fun clear() {
        return sharedPreferences.edit().clear().apply()
    }
}
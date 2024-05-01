package kz.kasip.data

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FCM {
    @POST("/fcm/send")
    suspend fun sendPush(
        @Header("Authorization") auth: String = "key=AAAAWg6Axrw:APA91bGCcgChJWIUN3x0pt8NVjT5sE65pSpEFBHvfm__nomjIfV8jiMRsERkDn-gBnIV0En8L7d9eeTF2wigIKm3e94F06EXKC-sDcu6J4VukF55KUMjGoUMulQH47kj1D76GWrH8aVt",
        @Body
        push: Push
    )
}
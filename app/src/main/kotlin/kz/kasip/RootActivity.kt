package kz.kasip

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.AndroidEntryPoint
import kz.kasip.catalog.catalogNavGraph
import kz.kasip.chat.navigation.chatGraph
import kz.kasip.designcore.Lang.eng
import kz.kasip.designcore.Lang.kz
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.theme.KasipkzTheme
import kz.kasip.onboarding.navigation.onboarding
import kz.kasip.onboarding.navigation.onboardingGraph
import kz.kasip.order.navigation.orderNavGraph
import kz.kasip.profile.navigation.profileNavGraph
import kz.kasip.response.ui.MyResponsesScreen
import kz.kasip.rialto.navigation.rialtoNavGraph
import kz.kasip.settings.navigation.settingsNavGraph
import kz.kasip.ui.main.MainScreen
import kz.kasip.ui.notifications.NotificationsScreen
import kz.kasip.works.navigation.worksNavGraph

val myResposesScreen = "myResposesScreen"
val notificationsScreen = "notificationsScreen"

@AndroidEntryPoint
class RootActivity : ComponentActivity() {
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            viewModel.savePhoto(uri)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    companion object {
        const val main = "main"
    }

    private val viewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lang =  if (viewModel.lang == "kz") {
            kz
        } else {
            eng
        }
        setContent {
            KasipkzTheme {
                val navController = rememberNavController()
                val navGraph = remember(navController) {
                    navController.createGraph(startDestination = viewModel.start) {
                        composable(main) {
                            MainScreen(
                                navigateTo = { navController.navigate(it) },
                                onSelectPhoto = {
                                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                            )
                        }
                        settingsNavGraph(
                            navigateToOnboarding = {
                                navController.navigate(route = onboarding) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                                navController.clearBackStack(onboarding)
                            },
                            navigateTo = { navController.navigate(it) },
                            onBack = { navController.popBackStack() }
                        )
                        onboardingGraph(
                            navigateTo = { navController.navigate(it) },
                            navigateToMain = {
                                navController.popBackStack()
                                navController.popBackStack()
                                navController.navigate(main)
                            }
                        )
                        rialtoNavGraph(
                            navigateTo = { navController.navigate(it) },
                            onBack = { navController.popBackStack() }
                        )
                        orderNavGraph(
                            navigateTo = { navController.navigate(it) },
                            onBack = { navController.popBackStack() }
                        )
                        chatGraph(
                            onGoToChat = {
                                navController.navigate(
                                    route = "chatScreen/$it"
                                )
                            },
                            onBack = { navController.popBackStack() }
                        )
                        worksNavGraph { navController.popBackStack() }
                        composable(myResposesScreen) {
                            MyResponsesScreen {
                                navController.popBackStack()
                            }
                        }
                        catalogNavGraph(
                            navigateToCatalog = {
                                navController.navigate(
                                    route = "catalogScreen/$it"
                                )
                            }
                        ) {
                            navController.popBackStack()
                        }
                        profileNavGraph(navigateTo = {
                            navController.navigate(it)
                        }
                        ) {
                            navController.popBackStack()
                        }
                        composable(notificationsScreen) {
                            NotificationsScreen(
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
                NavHost(navController = navController, graph = navGraph)
                val chatId by viewModel.chatIdFlow.collectAsState()
                if (chatId != null) {
                    navController.navigate("chatScreen/$chatId")
                }
            }
        }
        tryChat(intent)
    }

    override fun onStart() {
        super.onStart()
        Firebase.messaging.token.addOnCompleteListener { task ->
            viewModel.dataStoreRepository.getUserId()?.let {
                Firebase.firestore.document("users/$it").update("fcmToken", task.result)
            }
        }
        requestPermissionLauncher.launch(POST_NOTIFICATIONS)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        tryChat(intent)
    }

    private fun tryChat(intent: Intent?) {
        val chatId = intent?.extras?.getString("chatId", "")
        if (!chatId.isNullOrEmpty()) {
            viewModel.goToChat(chatId)
        }
    }
}
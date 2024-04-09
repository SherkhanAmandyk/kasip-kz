package kz.kasip.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kz.kasip.data.entities.Profile
import kz.kasip.data.entities.User
import kz.kasip.data.entities.Work
import kz.kasip.data.mappers.toProfile
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.usecase.LogOutUseCase
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    val userFlow = MutableStateFlow<User?>(null)
    val profileFlow = MutableStateFlow<Profile?>(null)

    val isLoggedOut = MutableStateFlow(false)

    init {
        val random = Random(34521345)
        val userIds = listOf(
            "5Cw5Fmt2gQsOJqFJk2ZV",
            "CtObHY3gvwhx9ju8FkKu",
            "FA53rtAOhr3o5oYGnaVA",
            "nRKDwarRWAkRUQDBihgr",
        )
        val works = listOf(
            Work(
                id = "",
                name = "Odio tempor orci dapibus ultrices",
                userId = userIds.get(random.nextInt(0, 3)),
                description = "Urna id volutpat lacus laoreet non. Amet porttitor eget dolor morbi non arcu risus. Morbi non arcu risus quis",
                price = "${random.nextInt(9000, 100000)}Tg",
                rate = random.nextInt(0, 4) + (random.nextInt(0, 9) * 0.1),
                reviewCount = 7,
                favoredBy = emptyList(),
                viewedBy = emptyList(),
                isHidden = random.nextBoolean(),
                isArchived = random.nextBoolean(),
            ),
            Work(
                id = "",
                name = "Consectetur libero id faucibus",
                userId = userIds.get(random.nextInt(0, 3)),
                description = "Imperdiet massa tincidunt nunc pulvinar sapien et ligula. Et malesuada fames ac turpis.",
                price = "${random.nextInt(9000, 100000)}Tg",
                rate = random.nextInt(0, 4) + (random.nextInt(0, 9) * 0.1),
                reviewCount = 7,
                favoredBy = emptyList(),
                viewedBy = emptyList(),
                isHidden = random.nextBoolean(),
                isArchived = random.nextBoolean(),
            ),
            Work(
                id = "",
                name = "Tincidunt praesent sempe",
                userId = userIds.get(random.nextInt(0, 3)),
                description = "Gravida cum sociis natoque penatibus et magnis dis parturient. Integer eget aliquet nibh praesent",
                price = "${random.nextInt(9000, 100000)}Tg",
                rate = random.nextInt(0, 4) + (random.nextInt(0, 9) * 0.1),
                reviewCount = 7,
                favoredBy = emptyList(),
                viewedBy = emptyList(),
                isHidden = random.nextBoolean(),
                isArchived = random.nextBoolean(),
            ),
            Work(
                id = "",
                name = "Convallis convallis tellus id interdum velit",
                userId = userIds.get(random.nextInt(0, 3)),
                description = "a;sldinfjfqwp[einf[qowuenfqwlef",
                price = "${random.nextInt(9000, 100000)}Tg",
                rate = random.nextInt(0, 4) + (random.nextInt(0, 9) * 0.1),
                reviewCount = 7,
                favoredBy = emptyList(),
                viewedBy = emptyList(),
                isHidden = random.nextBoolean(),
                isArchived = random.nextBoolean(),
            ),
            Work(
                id = "",
                name = "Tellus rutrum tellus pellen",
                userId = userIds.get(random.nextInt(0, 3)),
                description = "Pellentesque habitant morbi tristique senectus. Velit sed ullamcorper morbi tincidunt ornare massa eget egestas purus. ",
                price = "${random.nextInt(9000, 100000)}Tg",
                rate = random.nextInt(0, 4).toFloat() + (random.nextInt(0, 9).toFloat() * 0.1),
                reviewCount = 7,
                favoredBy = emptyList(),
                viewedBy = emptyList(),
                isHidden = random.nextBoolean(),
                isArchived = random.nextBoolean(),
            ),
            Work(
                id = "",
                name = "Ullamcorper sit amet risus",
                userId = userIds.get(random.nextInt(0, 3)),
                description = "Cum sociis natoque penatibus et magnis. Amet luctus venenatis lectus magna fringilla urna porttitor rhoncus. ",
                price = "${random.nextInt(9000, 100000)}Tg",
                rate = random.nextInt(0, 4) + (random.nextInt(0, 9) * 0.1),
                reviewCount = 7,
                favoredBy = emptyList(),
                viewedBy = emptyList(),
                isHidden = random.nextBoolean(),
                isArchived = random.nextBoolean(),
            ),
            Work(
                id = "",
                name = "Tellus at urna condimentum ",
                userId = userIds.get(random.nextInt(0, 3)),
                description = "Proin sagittis nisl rhoncus mattis rhoncus. At consectetur lorem donec massa sapien faucibus. Pulvinar etiam non quam lacus suspendisse faucibus interdum posuere.",
                price = "${random.nextInt(9000, 100000)}Tg",
                rate = random.nextInt(0, 4) + (random.nextInt(0, 9) * 0.1),
                reviewCount = 7,
                favoredBy = emptyList(),
                viewedBy = emptyList(),
                isHidden = random.nextBoolean(),
                isArchived = random.nextBoolean(),
            ),
            Work(
                id = "",
                name = "Porttitor lacus luctus",
                userId = userIds.get(random.nextInt(0, 3)),
                description = " Viverra nam libero justo laoreet sit amet cursus sit amet. Luctus venenatis lectus magna fringilla urna porttitor rhoncus dolor. Feugiat nisl pretium fusce id. Eget felis eget nunc lobortis mattis",
                price = "${random.nextInt(9000, 100000)}Tg",
                rate = random.nextInt(0, 4) + (random.nextInt(0, 9) * 0.1),
                reviewCount = 7,
                favoredBy = emptyList(),
                viewedBy = emptyList(),
                isHidden = random.nextBoolean(),
                isArchived = random.nextBoolean(),
            ),
            Work(
                id = "",
                name = "Viverra nam libero justo",
                userId = userIds.get(random.nextInt(0, 3)),
                description = "Sed tempus urna et pharetra pharetra massa massa ultricies mi. Porttitor lacus luctus accumsan tortor posuere ac ut. Tellus at urna condimentum mattis pellentesque id nibh tortor. Egestas purus viverra accumsan in nisl nisi scelerisque",
                price = "${random.nextInt(9000, 100000)}Tg",
                rate = random.nextInt(0, 4) + (random.nextInt(0, 9) * 0.1),
                reviewCount = 7,
                favoredBy = emptyList(),
                viewedBy = emptyList(),
                isHidden = random.nextBoolean(),
                isArchived = random.nextBoolean(),
            ),
        )
        val worksCollections = Firebase.firestore.collection("works")
        viewModelScope.launch(Dispatchers.IO) {
            works.forEach {
                worksCollections.add(it).await().let {
                    it.update("id", it.id)
                        .await()
                }
            }
        }

        viewModelScope.launch {
            profileFlow.update {
                Firebase.firestore.collection("profiles")
                    .document(dataStoreRepository.getUserId() ?: "")
                    .get()
                    .await()
                    .toProfile()
            }
            userFlow.collect {
                it?.let { user ->
                    profileFlow.update {
                        Firebase.firestore.collection("profiles")
                            .document(user.id)
                            .get()
                            .await()
                            .toProfile()
                    }
                }
            }
        }
    }

    fun logOut() {
        logOutUseCase()
        isLoggedOut.update { true }
    }
}
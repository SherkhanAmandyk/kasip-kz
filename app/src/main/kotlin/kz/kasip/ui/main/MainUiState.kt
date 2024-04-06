package kz.kasip.ui.main

class MainUiState {
    val sections = listOf<Pair<String, List<String>>>(
        "Work" to listOf(
            "My Works",
            "Favourites",
            "Hidden",
            "Viewed",
            "Teammates",
            "My Response",
            "Archive",
            "Rialto",
        ),
        "General" to listOf(
            "Settings",
            "Profile",
            "Black List",
        ),
        "Notifications" to listOf(
            "Push - Notice",
            "Message sound",
        )
    )
}
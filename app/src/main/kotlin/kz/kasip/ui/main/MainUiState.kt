package kz.kasip.ui.main

import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.general
import kz.kasip.designcore.hidden
import kz.kasip.designcore.my_response
import kz.kasip.designcore.my_works
import kz.kasip.designcore.profile
import kz.kasip.designcore.rialto
import kz.kasip.designcore.settings
import kz.kasip.designcore.viewed
import kz.kasip.designcore.work

class MainUiState {
    val sections = listOf(
        (lang[work] ?: "") to listOf(
            (lang[my_works]),
            lang[hidden] ?: "",
            lang[my_response] ?: "",
            "Favorite",
            lang[viewed] ?: "",
            lang[rialto] ?: "",
        ),
        (lang[general] ?: "") to listOf(
            lang[settings] ?: "",
            lang[profile] ?: "",
            "Block list"
        )
    )
}
package kz.kasip.data.entities

data class Subrubric(val id: String? = null, val name: String, val rubricId: String? = null) {
    companion object {
        val empty = Subrubric(
            name = "Industrial Design"
        )
    }
}
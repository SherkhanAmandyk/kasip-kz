package kz.kasip.data.entities

data class Rubric(val name: String, val subrubrics: List<Subrubric>) {
    companion object {
        val empty = Rubric(
            name = "Design",
            subrubrics = listOf(Subrubric.empty)
        )
    }
}
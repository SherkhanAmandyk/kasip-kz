package kz.kasip.designcore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.kasip.data.entities.Rubric
import kz.kasip.data.entities.Subrubric
import kz.kasip.designcore.theme.CardBackground

@Composable
fun RubricsList(
    rubrics: List<Rubric>,
    selectedSubrubricIds: List<String>,
    onSubrubricToggle: (Subrubric) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        rubrics.forEach { rubric ->
            Column(
                modifier = Modifier
                    .background(color = CardBackground)
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                    text = rubric.name,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    Spacer(modifier = Modifier.width(38.dp))
                    Column {
                        rubric.subrubrics.forEach { subrubric ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(text = subrubric.name, fontSize = 20.sp)
                                Checkbox(
                                    checked = selectedSubrubricIds.contains(subrubric.id),
                                    onCheckedChange = { onSubrubricToggle(subrubric) }
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = Color.Black)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRialtoList() {
    RubricsList(
        listOf(
            Rubric(
                name = "Rubric",
                listOf(
                    Subrubric(id = "", name = "Subrubric"),
                    Subrubric(name = "Subrubric")
                ),
            ),
            Rubric(
                name = "Rubric",
                listOf(Subrubric(name = "Subrubric"))
            ),
            Rubric(
                name = "Rubric",
                listOf(Subrubric(name = "Subrubric"))
            )
        ),
        listOf(""),
    ) {}
}
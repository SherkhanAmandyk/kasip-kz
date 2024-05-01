package kz.kasip.order.ui.filter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.RubricsList
import kz.kasip.designcore.save
import kz.kasip.designcore.theme.PrimaryBackgroundGreen
import kz.kasip.order.ui.salesperson.SalespersonViewModel

@Composable
fun SalespersonFilterScreen(
    viewModel: SalespersonViewModel,
    onBack: () -> Unit,
) {
    val rubrics by viewModel.rubricsFlow.collectAsState()
    val selectedSubrubricIds by viewModel.selectedSubrubricIdsFlow.collectAsState()

    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = lang[kz.kasip.designcore.rubrics] ?: "",
                    onBack = onBack
                )
            },
            floatingActionButton = {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth(),
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = PrimaryBackgroundGreen)
                ) {
                    Text(text = lang[save] ?: "")
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            Box(modifier = Modifier.padding(it)) {
                RubricsList(
                    rubrics,
                    selectedSubrubricIds,
                ) {
                    it.let {
                        viewModel.onSubrubricSelected(it.id ?: "")
                    }
                }
            }
        }
    }

}


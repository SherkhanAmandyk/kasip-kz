package kz.kasip.catalog.rubrics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kz.kasip.catalog.R
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.catalog_rubrics

@Composable
fun CatalogRubricsScreen(
    viewModel: CatalogRubricsViewModel = hiltViewModel(),
    navigateToCatalog: (String) -> Unit,
    onBack: () -> Unit,
) {
    val catalogRubrics by viewModel.catalogImagesMapFlow.collectAsState(initial = emptyList())
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = lang[catalog_rubrics] ?: "",
                    onBack = onBack
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    catalogRubrics.forEach {
                        item {
                            Surface(
                                onClick = {
                                    navigateToCatalog(it.first.id)
                                }
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(32.dp))
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(32.dp))
                                    ) {
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(128.dp)
                                                .clip(RoundedCornerShape(32.dp)),
                                            painter = rememberAsyncImagePainter(model = it.second),
                                            contentDescription = "",
                                            contentScale = ContentScale.Crop
                                        )
                                        Text(
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            text = it.first.name
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
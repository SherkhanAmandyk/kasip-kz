package kz.kasip.catalog.favorite

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kz.kasip.catalog.R
import kz.kasip.data.entities.CatalogItem
import kz.kasip.designcore.KasipTopAppBar

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val catalogItems by viewModel.itemsWithImageFlow.collectAsState(emptyList())
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = "Favorite",
                    onBack = onBack
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    catalogItems.forEach {
                        CatalogItemCard(it, viewModel::changeFavorite)
                    }
                }
            }
        }
    }
}

@Composable
fun CatalogItemCard(
    catalogItem: Triple<CatalogItem, Uri, Boolean>,
    isFavoriteChanged: (CatalogItem) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row {
            Image(
                modifier = Modifier
                    .height(128.dp)
                    .weight(1F),
                painter = rememberAsyncImagePainter(model = catalogItem.second),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1F)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = catalogItem.first.name,
                        color = Color.Black,
                        fontWeight = FontWeight(700),
                        fontSize = 16.sp
                    )
                    IconButton(onClick = {
                        isFavoriteChanged(catalogItem.first)
                    }) {
                        val icon = if (catalogItem.third) {
                            R.drawable.icon_favourite
                        } else {
                            R.drawable.icon_favourite_not
                        }
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = ""
                        )
                    }
                }
                Text(
                    text = catalogItem.first.description,
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = catalogItem.first.price,
                    color = Color.Black,
                    fontWeight = FontWeight(700),
                    fontSize = 16.sp
                )
            }
        }
    }
}

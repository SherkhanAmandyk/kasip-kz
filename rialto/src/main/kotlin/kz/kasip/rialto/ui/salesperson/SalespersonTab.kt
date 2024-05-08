package kz.kasip.rialto.ui.salesperson

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.history
import kz.kasip.designcore.offer
import kz.kasip.designcore.rate
import kz.kasip.designcore.search
import kz.kasip.designcore.theme.CardBackground
import kz.kasip.designcore.theme.Divider
import kz.kasip.designcore.theme.PrimaryBackgroundGreen
import kz.kasip.rialto.R
import kz.kasip.rialto.RialtoUi

@Composable
fun SalespersonTab(
    viewModel: SalespersonViewModel = hiltViewModel(),
    navigateToOfferService: (RialtoUi) -> Unit,
) {
    val searchText by viewModel.searchTextFlow.collectAsState()

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            label = {
                Text(text = lang[search] ?: "")
            }
        )

        val rialtos by viewModel.rialtosFlow.collectAsState(emptyList())
        rialtos.forEach { rialto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                colors = elevatedCardColors().copy(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = rialto.name,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = rialto.description)
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = rialto.price,
                        fontSize = 18.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Divider)
                )
                Column(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    Row {
                        if (rialto.avatar != null) {
                            Image(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.Black, CircleShape),
                                painter = rememberAsyncImagePainter(
                                    rialto.avatar,
                                    contentScale = ContentScale.FillBounds
                                ),
                                contentDescription = ""
                            )
                        } else {
                            Image(
                                modifier = Modifier
                                    .width(56.dp)
                                    .height(50.dp),
                                painter = painterResource(id = kz.kasip.designcore.R.drawable.icon_profile_pic),
                                contentDescription = ""
                            )
                        }
                        Column(
                            modifier = Modifier.height(50.dp),
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1F)
                                    .wrapContentSize(),
                                text = rialto.buyer?.name ?: "",
                                fontSize = 10.sp
                            )
                            Text(
                                modifier = Modifier
                                    .weight(1F)
                                    .wrapContentSize(),
                                text = "${lang[rate]} ${rialto.buyer?.rate}",
                                fontSize = 10.sp,
                            )
                            Text(
                                modifier = Modifier
                                    .weight(1F)
                                    .wrapContentSize(),
                                text = lang[history] ?: "",
                                fontSize = 10.sp,
                            )
                        }
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navigateToOfferService(rialto) },
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = PrimaryBackgroundGreen)
                    ) {
                        Text(text = lang[offer] ?: "")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

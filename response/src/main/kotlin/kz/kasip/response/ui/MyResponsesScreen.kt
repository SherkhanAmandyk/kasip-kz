package kz.kasip.response.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.data.entities.Rialto
import kz.kasip.data.entities.RialtoOffer
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.created
import kz.kasip.designcore.my_responses
import kz.kasip.designcore.send
import kz.kasip.designcore.theme.CB
import kz.kasip.designcore.theme.CardBackground
import kz.kasip.designcore.theme.PrimaryBackgroundGreen
import kz.kasip.response.R
import java.util.Date

@Composable
fun MyResponsesScreen(
    viewModel: MyResponsesViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            KasipTopAppBar(
                title = lang[my_responses] ?: "",
                onBack = onBack
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val items by viewModel.responsesFlow.collectAsState()
                items.forEach {
                    MyResponseItem(response = it) {
                        viewModel.onDelete(it)
                    }
                }
            }
        }
    }
}

@Composable
fun MyResponseItem(
    response: Pair<Rialto, RialtoOffer>,
    onDelete: (RialtoOffer) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors().copy(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = PrimaryBackgroundGreen,
                                shape = RoundedCornerShape(topEnd = 14.dp, bottomEnd = 14.dp)
                            )
                            .padding(vertical = 8.dp, horizontal = 28.dp)
                    ) {
                        Text(text = lang[send] ?: "", color = Color.White)
                    }
                }
                Row {
                    Surface(
                        color = CB,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                        onClick = {
                            onDelete(response.second)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 10.dp, bottom = 28.dp),
                text = response.first.name,
                fontWeight = FontWeight(700),
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${lang[created]} ${response.second.sentAt.toDate()}"
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.figure_wave_circle),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "0")
                    }
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = response.second.price
                    )
                }
            }
        }
    }
}

private fun Date.toDate(): String {
    return "$day.$month.${year + 1900}"
}

@Preview
@Composable
fun PreviewMyResponseItem() {
    MyResponseItem(
        Rialto.default to RialtoOffer.default

    ) {

    }
}

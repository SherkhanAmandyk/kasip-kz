package kz.kasip.order.ui.main.salesperson

import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.data.repository.DataStoreRepository
import kz.kasip.designcore.theme.CB
import kz.kasip.designcore.theme.CardBackground
import kz.kasip.designcore.theme.PrimaryBackgroundGreen
import kz.kasip.designcore.theme.YB
import kz.kasip.designcore.theme.YT
import kz.kasip.order.R
import kz.kasip.order.OrderUi
import java.util.Date

@Composable
fun BuyerTab(
    viewModel: BuyerViewModel = hiltViewModel(),
    navigateToCreateProject: () -> Unit,
) {
    val myOrders by viewModel.myOrdersFlow.collectAsState()
    if (myOrders.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 100.dp),
                    text = "You have no active projects",
                    fontSize = 21.sp,
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Create a project and find performers",
                    textAlign = TextAlign.Center
                )
                Button(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth(),
                    onClick = navigateToCreateProject,
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = PrimaryBackgroundGreen)
                ) {
                    Text(text = "Create project")
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            myOrders.forEach { order ->
                MyOrderItem(order)
            }
        }
    }
}

@Composable
fun MyOrderItem(orderUi: OrderUi) {
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
                        Text(text = "Active", color = Color.White)
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        color = YB,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    ) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            painter = painterResource(id = R.drawable.pause),
                            contentDescription = "",
                            tint = YT
                        )
                    }
                    Surface(
                        color = CB,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    ) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = ""
                        )
                    }
                    Surface(
                        color = CB,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
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
                text = orderUi.name,
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
                    text = "created ${orderUi.time.toDate().toDate()}"
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.eye_circle),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "0")
                    }
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
                        text = orderUi.price
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
fun PreviewBuyerTab() {
    BuyerTab(
        BuyerViewModel(
            DataStoreRepository(
                LocalContext.current.getSharedPreferences("", MODE_PRIVATE)
            )
        )
    ) {

    }
}
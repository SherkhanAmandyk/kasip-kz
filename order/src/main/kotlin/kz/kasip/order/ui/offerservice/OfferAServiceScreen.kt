package kz.kasip.order.ui.offerservice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.designcore.KasipTopAppBar
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.cost
import kz.kasip.designcore.desired_budget
import kz.kasip.designcore.detail_description_of_project
import kz.kasip.designcore.history
import kz.kasip.designcore.offer
import kz.kasip.designcore.offer_a_service
import kz.kasip.designcore.rate
import kz.kasip.designcore.theme.DialogBackground
import kz.kasip.designcore.theme.PrimaryBackgroundGreen
import kz.kasip.designcore.write_how_you_will_solve_the_client_s_problem
import kz.kasip.order.OrderUi
import kz.kasip.order.R

@Composable
fun OfferAServiceScreen(
    orderUi: OrderUi,
    viewModel: OfferAServiceViewModel = hiltViewModel<OfferAServiceViewModel, OfferAServiceViewModelFactory> {
        it.create(orderUi)
    },
    onBack: () -> Unit,
) {
    val order by viewModel.orderUiFlow.collectAsState()
    val isCreated by viewModel.isCreated.collectAsState()
    if (isCreated) {
        onBack()
        viewModel.invalidate()
    }
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(
                    title = lang[offer_a_service] ?: "",
                    color = Color.White,
                    onBack = onBack
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .background(color = DialogBackground)
                    .fillMaxSize()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .padding(end = 28.dp)
                            .background(
                                color = PrimaryBackgroundGreen,
                                shape = RoundedCornerShape(
                                    topEnd = 50.dp,
                                    bottomEnd = 50.dp
                                )
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 18.dp, vertical = 8.dp),
                            text = order.name,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                    Box(modifier = Modifier.padding(horizontal = 18.dp)) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = lang[detail_description_of_project] ?: "",
                                fontSize = 16.sp
                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = CardDefaults.elevatedCardColors().copy(
                                    containerColor = TextFieldDefaults.colors().unfocusedContainerColor
                                )
                            ) {
                                Text(
                                    modifier = Modifier.padding(6.dp),
                                    text = order.description
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "${order.rubric?.name}: ",
                                    fontSize = 16.sp
                                )
                                Text(text = order.subrubric?.name ?: "")
                            }
                            Row(
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "${lang[desired_budget]}: ",
                                    fontSize = 16.sp
                                )
                                Text("To")
                                Text(
                                    text = order.price,
                                    color = PrimaryBackgroundGreen,
                                    fontSize = 20.sp
                                )
                            }
                            Row {
                                Image(
                                    modifier = Modifier
                                        .width(56.dp)
                                        .height(50.dp),
                                    painter = painterResource(id = R.drawable.filter),
                                    contentDescription = ""
                                )
                                Column(
                                    modifier = Modifier.height(50.dp),
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .weight(1F)
                                            .wrapContentSize(),
                                        text = order.buyer?.name ?: "",
                                        fontSize = 10.sp
                                    )
                                    Text(
                                        modifier = Modifier
                                            .weight(1F)
                                            .wrapContentSize(),
                                        text = "${lang[rate]} ${order.buyer?.rate}",
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
                            Spacer(modifier = Modifier.height(36.dp))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = CardDefaults.elevatedCardColors().copy(
                                    containerColor = Color.White
                                )
                            ) {
                                val offerText by viewModel.offerTextFlow.collectAsState()
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = offerText,
                                    onValueChange = viewModel::onOfferTextChange,
                                    label = {
                                        Text(
                                            text = lang[write_how_you_will_solve_the_client_s_problem]
                                                ?: "",
                                            fontSize = 10.sp
                                        )
                                    },
                                    textStyle = LocalTextStyle.current.copy(fontSize = 10.sp)
                                )
                            }
                            val price by viewModel.priceFlow.collectAsState()
                            Text(
                                text = lang[cost] ?: "",
                                fontSize = 16.sp
                            )
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = price,
                                onValueChange = viewModel::onPriceChange,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Decimal
                                )
                            )
                            Button(
                                modifier = Modifier
                                    .padding(horizontal = 32.dp)
                                    .fillMaxWidth(),
                                onClick = viewModel::onOffer,
                                colors = ButtonDefaults.buttonColors()
                                    .copy(containerColor = PrimaryBackgroundGreen)
                            ) {
                                Text(text = lang[offer] ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}
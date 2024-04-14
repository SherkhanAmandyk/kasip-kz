package kz.kasip.chat.ui.blocklist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.kasip.chat.R
import kz.kasip.designcore.KasipTopAppBar

@Composable
fun BlockListScreen(
    viewModel: BlockListViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                KasipTopAppBar(title = "Block list", onBack = onBack)
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column {
                    val blocked by viewModel.blockedChats.collectAsState(initial = emptyList())
                    blocked.forEach {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Image(
                                    modifier = Modifier
                                        .height(64.dp)
                                        .width(64.dp)
                                        .padding(12.dp),
                                    painter = painterResource(id = R.drawable.skull),
                                    contentDescription = ""
                                )
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = it.first?.email ?: ""
                                )
                            }
                            TextButton(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                onClick = { viewModel.unblock(it) }) {
                                Text(text = "Unblock")
                            }
                        }
                    }
                }
            }
        }
    }
}
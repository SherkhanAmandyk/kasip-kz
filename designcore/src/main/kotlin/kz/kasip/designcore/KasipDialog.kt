package kz.kasip.designcore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.filledTonalButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kz.kasip.designcore.Lang.lang
import kz.kasip.designcore.theme.DialogBackground
import kz.kasip.designcore.theme.PrimaryBackgroundGreen

@Composable
fun KasipDialog(
    title: String = "",
    buttons: List<ButtonUiState> = emptyList(),
    onDismissRequest: () -> Unit,
    onClick: (ButtonUiState) -> Unit = {},
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            color = DialogBackground,
            shape = RoundedCornerShape(25.dp)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    buttons.forEachIndexed { index, button ->
                        Button(
                            modifier = Modifier
                                .widthIn(min = 128.dp)
                                .heightIn(max = 28.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = filledTonalButtonColors(containerColor = button.color),
                            onClick = {
                                onClick(button)
                                onDismissRequest()
                            }
                        ) {
                            Text(text = button.text, color = button.textColor)
                        }
                        if (buttons.lastIndex != index) {
                            Spacer(modifier = Modifier.width(19.dp))
                        }
                    }
                }
            }
        }
    }
}

data class ButtonUiState(
    val text: String,
    val color: Color = PrimaryBackgroundGreen,
    val textColor: Color = Color.White,
)

@Preview
@Composable
fun PreviewKasipDialog() {
    KasipDialog(
        title = "This is Error",
        buttons = listOf(
            ButtonUiState(
                lang[ok] ?: "",
                color = PrimaryBackgroundGreen,
                textColor = Color.White
            ),
            ButtonUiState(
                lang[ok] ?: "",
                color = PrimaryBackgroundGreen,
                textColor = Color.White
            )
        ),
        {}
    ) {

    }
}
package kz.kasip.designcore

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import kz.kasip.designcore.Lang.lang

@Composable
fun EmailTextField(
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        placeholder = {
            Text(text = lang[email] ?: "")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
        )
    )
}

@Composable
fun PasswordTextField(
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(text = lang[password] ?: "") },
        placeholder = { Text(text = lang[password] ?: "") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else
                Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = stringResource(
                id = if (passwordVisible)
                    R.string.hide_password
                else
                    R.string.show_password
            )

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        }
    )
}
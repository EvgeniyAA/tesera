package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.tesera.designsystem.theme.TeseraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeseraInputField(
    modifier: Modifier = Modifier,
    labelText: String = "",
    text: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = labelText) },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Preview("Input Field")
@Composable
fun TeseraInputField_Preview() {
    TeseraTheme {
        TeseraInputField(text = "text", onValueChange = {})
    }
}
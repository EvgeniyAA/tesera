package com.tesera.feature.login.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tesera.core.designsystem.theme.AppTheme
import com.tesera.core.designsystem.theme.TeseraTheme
import com.tesera.core.designsystem.theme.components.TeseraButton
import com.tesera.core.designsystem.theme.components.TeseraInputField
import com.tesera.core.designsystem.theme.components.TeseraLinkButton
import com.tesera.core.model.CredentialsField
import com.tesera.core.model.CredentialsField.Companion.toCredentialsField
import com.tesera.feature.login.R

@Composable
fun LoginViewDisplay(
    onLoginClick: (username: CredentialsField, password: CredentialsField) -> Unit,
    onSkipLoginClick: () -> Unit,
) {
    var username by remember { mutableStateOf(CredentialsField(charArrayOf())) }
    var password by remember { mutableStateOf(CredentialsField(charArrayOf())) }
    ConstraintLayout(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(),
    ) {
        val (logo, title, usernameInput, passwordInput, skip, login) = createRefs()
        Image(
            modifier = Modifier
                .size(68.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_dice),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .constrainAs(title) { top.linkTo(logo.bottom) },
            text = "Login",
            style = AppTheme.typography.heading,
            color = AppTheme.colors.primaryTextColor,
            textAlign = TextAlign.Center
        )
        TeseraInputField(
            text = username.concatToString(),
            onValueChange = { username = it.toCredentialsField() },
            labelText = "Username",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .constrainAs(usernameInput) { top.linkTo(title.bottom) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        TeseraInputField(
            text = password.concatToString(),
            onValueChange = { password = it.toCredentialsField() },
            labelText = "Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .constrainAs(passwordInput) { top.linkTo(usernameInput.bottom) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onLoginClick(username, password) })
        )
        TeseraLinkButton(
            modifier = Modifier
                .padding(top = 32.dp)
                .constrainAs(skip) { top.linkTo(passwordInput.bottom) },
            text = "Skip",
            onClick = onSkipLoginClick
        )
        TeseraButton(
            modifier = Modifier.constrainAs(login) {
                bottom.linkTo(parent.bottom)
            },
            text = "Login",
            onClick = { onLoginClick(username, password) })
    }
}

@Preview("Login Screen")
@Composable
fun LoginViewDisplay_Preview() {
    TeseraTheme {
        LoginViewDisplay({ _, _ -> }, {})
    }
}
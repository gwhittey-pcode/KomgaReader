package org.maddiesoftware.komagareader.server_select.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun ServerAddScreen(
    serverId: Int = -1,
    navigator: DestinationsNavigator,
    viewModel: ServerAddViewModel = hiltViewModel()
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        val state = viewModel.state
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is ServerAddViewModel.ValidationEvent.Success -> {
                        Toast.makeText(
                            context,
                            "Server Added successful",
                            Toast.LENGTH_LONG
                        ).show()
                        navigator.navigateUp()
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = state.serverName,
                onValueChange = {
                    viewModel.onEvent(AddServerFormEvent.ServerNameChanged(it))
                },
                isError = state.serverNameError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Server Name")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            if (state.serverNameError != null) {
                Text(
                    text = state.serverNameError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.username,
                onValueChange = {
                    viewModel.onEvent(AddServerFormEvent.UserNameChanged(it))
                },
                isError = state.usernameError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "User Name")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            if (state.usernameError != null) {
                Text(
                    text = state.usernameError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(AddServerFormEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.url,
                onValueChange = {
                    viewModel.onEvent(AddServerFormEvent.UrlChanged(it))
                },
                isError = state.urlError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Url")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri
                )
            )
            if (state.urlError != null) {
                Text(
                    text = state.urlError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement  =  Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        viewModel.onEvent(AddServerFormEvent.Submit)

                    },
                ) {
                    Text(text = "Add Server")
                }
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = {
                        navigator.navigateUp()
                    },
                ) {
                    Text(text = "Cancel")
                }


            }

        }
    }
}



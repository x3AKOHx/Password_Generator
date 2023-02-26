package com.example.passwordgenerator.compose.add_pass

import android.os.SystemClock
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passwordgenerator.R
import com.example.passwordgenerator.data.PassEntity
import com.example.passwordgenerator.compose.RandomPassButton
import com.example.passwordgenerator.view_models.PassViewModel

@Composable
fun AddPasswordScreen(
    viewModel: PassViewModel = hiltViewModel()
) {
    var title by remember {
        mutableStateOf("")
    }
    var login by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.focusRequester(focusRequester),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(28.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(R.color.dark_main),
                focusedLabelColor = colorResource(R.color.dark_main),
                unfocusedLabelColor = colorResource(R.color.main),
                unfocusedBorderColor = colorResource(R.color.main),
                cursorColor = colorResource(R.color.dark_main),
                textColor = colorResource(R.color.dark_main),
            ),
            label = { Text(stringResource(R.string.title)) },
            value = title,
            onValueChange = { newText ->
            title = newText
        })
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(R.color.dark_main),
                focusedLabelColor = colorResource(R.color.dark_main),
                unfocusedLabelColor = colorResource(R.color.main),
                unfocusedBorderColor = colorResource(R.color.main),
                cursorColor = colorResource(R.color.dark_main),
                textColor = colorResource(R.color.dark_main),
            ),
            label = { Text(stringResource(R.string.login)) },
            value = login,
            onValueChange = { newText ->
            login = newText
        })
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(R.color.dark_main),
                focusedLabelColor = colorResource(R.color.dark_main),
                unfocusedLabelColor = colorResource(R.color.main),
                unfocusedBorderColor = colorResource(R.color.main),
                cursorColor = colorResource(R.color.dark_main),
                textColor = colorResource(R.color.dark_main),
            ),
            label = { Text(stringResource(R.string.pass)) },
            value = password,
            onValueChange = { newText ->
                password = newText
            },
            trailingIcon = {
                RandomPassButton(
                    onClick = {
                        password = viewModel.generatePassword()
                    }
                )
            }
        )
        Spacer(Modifier.height(18.dp))

        val isValidInput = title.trim().isNotEmpty() || login.trim().isNotEmpty() || password.trim().isNotEmpty()
        val context = LocalContext.current

        Button(
            onClick = {
                if (isValidInput) {
                    val passEntity = PassEntity(
                        passId = SystemClock.elapsedRealtime(),
                        title = title,
                        login = login,
                        pass = password,
                    )
                    viewModel.insert(passEntity)
                    password = ""
                    login = ""
                    title = ""
                    focusManager.clearFocus()

                    Toast.makeText(context, R.string.add_pass_success, Toast.LENGTH_SHORT).show()
                } else {
                    password = ""
                    login = ""
                    title = ""
                    focusManager.clearFocus()
                    Toast.makeText(context, R.string.add_pass_fail, Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.dark_main))
        ) {
            Text(
                text = stringResource(id = R.string.add_button_text),
                color = colorResource(R.color.background)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddPassScreenPreview() {
    AddPasswordScreen()
}

package com.example.passwordgenerator.compose.passwords_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passwordgenerator.R
import com.example.passwordgenerator.data.PassEntity
import com.example.passwordgenerator.view_models.PassViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SavedPasswordsScreen(
    viewModel: PassViewModel = hiltViewModel()
) {
    val passwords by viewModel.passFlow.collectAsStateWithLifecycle()
    val expandedPassIds by viewModel.expandedPassIdsList.collectAsStateWithLifecycle()

    Scaffold(
        backgroundColor = colorResource(R.color.background)
    ) {
        LazyColumn {
            items(passwords, PassEntity::passId) { pass ->
                PassWordExpandableBlock(
                    modifier = Modifier.padding(
                        start = 6.dp,
                        end = 6.dp,
                        bottom = 6.dp,
                        top = if (pass == passwords.firstOrNull()) 6.dp else 3.dp,
                    ),
                    model = pass,
                    onCardArrowClick = { viewModel.onCardArrowClicked(pass.passId) },
                    expanded = expandedPassIds.contains(pass.passId),
                    headerColor = colorResource(R.color.dark_main),
                    expandedBackgroundColor = colorResource(R.color.card_background),
                    titleColor = colorResource(R.color.background),
                    mainTextColor = colorResource(R.color.dark_main),
                    valueTextColor = colorResource(R.color.dark_main),
                    viewModel = viewModel,
                )
            }
        }
    }
}
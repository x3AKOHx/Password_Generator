package com.example.passwordgenerator.compose.passwords_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.android.animation.SegmentType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordgenerator.R
import com.example.passwordgenerator.data.PassEntity
import com.example.passwordgenerator.compose.CardArrowButton
import com.example.passwordgenerator.compose.CopyTextButton
import com.example.passwordgenerator.compose.DeleteButton
import com.example.passwordgenerator.compose.EXPANSION_TRANSITION_DURATION
import com.example.passwordgenerator.compose.EditButton
import com.example.passwordgenerator.view_models.PassViewModel

enum class FieldType {
    LOGIN,
    PASSWORD,
}

@Composable
fun PassWordExpandableBlock(
    modifier: Modifier = Modifier,
    model: PassEntity,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
    headerColor: Color,
    expandedBackgroundColor: Color,
    titleColor: Color,
    mainTextColor: Color,
    valueTextColor: Color,
    viewModel: PassViewModel,
) {
    Box(
        modifier = modifier.background(
            color = expandedBackgroundColor,
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Column {
            TitleView(
                modifier = Modifier
                    .background(color = headerColor, shape = RoundedCornerShape(10.dp)),
                passId = model.passId,
                title = model.title,
                pass = model.pass,
                titleColor = titleColor,
                secondTitleColor = mainTextColor,
                onCardArrowClick = onCardArrowClick,
                expanded = expanded,
                viewModel = viewModel,
            )
            ExpandableView(
                passId = model.passId,
                login = model.login,
                password = model.pass,
                isExpanded = expanded,
                textColor = mainTextColor,
                valueTextColor = valueTextColor,
                viewModel = viewModel,
            )
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun TitleView(
    modifier: Modifier = Modifier,
    passId: Long,
    title: String,
    pass: String,
    titleColor: Color,
    secondTitleColor: Color,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
    viewModel: PassViewModel,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            var enabled by remember { mutableStateOf(false) }
            val focusRequester = remember { FocusRequester() }
            var dataText by remember {
                mutableStateOf(title)
            }
            val textStyle = TextStyle(
                fontSize = 22.sp,
                color = if (enabled) secondTitleColor else titleColor,
                fontWeight = FontWeight.Bold,
            )
            val textModifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 10.dp, top = 9.dp)
            BasicTextField(
                modifier = if (enabled) {
                    textModifier.background(color = colorResource(R.color.background))
                } else textModifier,
                value = dataText,
                readOnly = !enabled,
                onValueChange = { newText ->
                    dataText = newText
                },
                textStyle = textStyle,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    autoCorrect = false,
                    capitalization = KeyboardCapitalization.None,
                )
            )
            if (!expanded) {
                CopyTextButton(
                    enabled = true,
                    data = pass,
                    color = colorResource(R.color.background),
                    toastText = stringResource(R.string.pass_copy_sucess),
                )
            } else {
                EditButton(
                    enabled = !enabled,
                    color = colorResource(R.color.background),
                ) {
                    if (!enabled) {
                        enabled = true
                    } else {
                        enabled = false
                        viewModel.editTitle(dataText, passId)
                    }
                }
            }

            CardArrowButton(
                expanded = expanded,
                color = colorResource(R.color.background),
                onClick = onCardArrowClick,
            )
        }
    }
}


@Composable
fun ExpandableView(
    passId: Long,
    login: String,
    password: String,
    isExpanded: Boolean,
    textColor: Color,
    valueTextColor: Color,
    viewModel: PassViewModel,
) {
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSION_TRANSITION_DURATION)
        ) + fadeIn(
            animationSpec = tween(EXPANSION_TRANSITION_DURATION)
        )
    }

    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSION_TRANSITION_DURATION)
        ) + fadeOut(
            animationSpec = tween(EXPANSION_TRANSITION_DURATION)
        )
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition,
    ) {
        Column {
            LogPassRow(
                passId = passId,
                text = stringResource(R.string.login),
                textColor = textColor,
                value = login,
                valueColor = valueTextColor,
                viewModel = viewModel,
                fieldType = FieldType.LOGIN,
            )
            LogPassRow(
                passId = passId,
                text = stringResource(R.string.pass),
                textColor = textColor,
                value = password,
                valueColor = valueTextColor,
                viewModel = viewModel,
                fieldType = FieldType.PASSWORD,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                modifier = Modifier.padding(horizontal = 20.dp),
                color = textColor,
                thickness = 1.dp
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                var enabled by remember { mutableStateOf(false) }
                DeleteButton(
                enabled = !enabled,
                ) {
                    if (!enabled) {
                        enabled = true
                    } else {
                        enabled = false
                        viewModel.deletePass(passId)
                    }
                }
            }
        }
    }
}

@Composable
fun LogPassRow(
    passId: Long,
    text: String,
    textColor: Color,
    value: String,
    valueColor: Color,
    viewModel: PassViewModel,
    fieldType: FieldType,
) {
    Row(
        verticalAlignment = CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp, bottom = 2.dp),
            text = "$text: ",
            fontSize = 16.sp,
            color = textColor,
            fontWeight = FontWeight.Normal
        )
        var dataText by remember {
            mutableStateOf(value)
        }
        val textStyle = TextStyle(
            fontSize = 18.sp,
            color = valueColor,
            fontWeight = FontWeight.Bold,
        )
        var enabled by remember { mutableStateOf(false) }
        val textModifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        BasicTextField(
            modifier = if (enabled) {
                textModifier.background(color = colorResource(R.color.background))
            } else textModifier,
            value = dataText,
            readOnly = !enabled,
            onValueChange = { newText ->
                dataText = newText
            },
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrect = false,
                capitalization = KeyboardCapitalization.None,
            )
        )
        EditButton(
            enabled = !enabled,
        ) {
            if (!enabled) {
                enabled = true
            } else {
                enabled = false
                when (fieldType) {
                    FieldType.LOGIN -> viewModel.editLogin(dataText, passId)
                    FieldType.PASSWORD -> viewModel.editPass(dataText, passId)
                }
            }
        }
        CopyTextButton(
            enabled = true,
            data = value,
            toastText = stringResource(R.string.any_copy_sucess, text),
        )
    }
}
package com.example.passwordgenerator.compose

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.passwordgenerator.R

const val EXPANSION_TRANSITION_DURATION = 400

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun CardArrowButton(
    expanded: Boolean,
    color: Color? = null,
    onClick: () -> Unit,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "transition")
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "rotationDegreeTransition") {
        if (expanded) 0f else 180f
    }
    IconButton(
        onClick = onClick,
        content = {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(arrowRotationDegree),
                colorFilter = if (color != null) ColorFilter.tint(color = color) else null
            )
        }
    )
}

@SuppressLint("ServiceCast", "UnusedTransitionTargetStateParameter")
@Composable
fun CopyTextButton(
    enabled: Boolean,
    data: String,
    toastText: String,
    color: Color? = null,
) {
    val context = LocalContext.current
    val transitionState = remember {
        MutableTransitionState(enabled).apply {
            targetState = enabled
        }
    }
    val transition = updateTransition(transitionState, label = "transition")

    val alpha by transition.animateFloat({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "alphaTransition") {
        if (!enabled) 0f else 1f
    }

    IconButton(
        onClick = {
            if (data.trim().isNotEmpty()) {
                val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData: ClipData = ClipData.newPlainText("text", data)
                clipboardManager.setPrimaryClip(clipData)

                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, R.string.text_copy_fail, Toast.LENGTH_SHORT).show()
            }
        },
        enabled = enabled,
        content = {
            Image(
                modifier = Modifier.scale(0.9f),
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = "Copy Paste",
                alpha = alpha,
                colorFilter = if (color != null) ColorFilter.tint(color = color) else null
            )
        }
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DeleteButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val transitionState = remember {
        MutableTransitionState(enabled).apply {
            targetState = enabled
        }
    }
    val transition = updateTransition(transitionState, label = "transition")

    val deleteAlpha by transition.animateFloat({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "alphaTransition") {
        if (!enabled) 0f else 1f
    }
    val okAlpha by transition.animateFloat({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "alphaTransition") {
        if (enabled) 0f else 1f
    }
    IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Image(
                modifier = Modifier.scale(1.05f),
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Delete",
                alpha = deleteAlpha,
            )
            Image(
                painter = painterResource(id = R.drawable.ic_ok),
                contentDescription = "Done",
                alpha = okAlpha,
            )
        }

    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun EditButton(
    enabled: Boolean,
    color: Color? = null,
    onClick: () -> Unit,
) {
    val transitionState = remember {
        MutableTransitionState(enabled).apply {
            targetState = enabled
        }
    }
    val transition = updateTransition(transitionState, label = "transition")

    val editAlpha by transition.animateFloat({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "alphaTransition") {
        if (!enabled) 0f else 1f
    }
    val okAlpha by transition.animateFloat({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "alphaTransition") {
        if (enabled) 0f else 1f
    }

    IconButton(
        modifier = Modifier,
        onClick = onClick,
        content = {
            Image(
                modifier = Modifier.scale(0.9f),
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit",
                alpha = editAlpha,
                colorFilter = if (color != null) ColorFilter.tint(color = color) else null
            )
            Image(
                painter = painterResource(id = R.drawable.ic_ok),
                contentDescription = "Done",
                alpha = okAlpha,
                colorFilter = if (color != null) ColorFilter.tint(color = color) else null
            )
        }
    )
}

@Composable
fun RandomPassButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Image(
                painter = painterResource(id = R.drawable.ic_dice),
                contentDescription = "Generate random pass",
            )
        }
    )
}
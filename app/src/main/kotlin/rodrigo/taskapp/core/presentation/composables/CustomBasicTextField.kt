package rodrigo.taskapp.core.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomBasicTextField(
    addModifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(color = LocalContentColor.current),
    cursorColor: Color = LocalContentColor.current,
    textFieldState: TextFieldState = rememberTextFieldState(),
    placeholder: String = "",
    textFieldColors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent
    ),
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember {MutableInteractionSource()},
    isError: Boolean = false,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    shape: Shape = shapes.small,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    openKeyboardOnComposition: Boolean = false
) {
    val focusRequester = remember {FocusRequester()}

    if(openKeyboardOnComposition){
        // Permite abrir el teclado cuando se compone el composable
        LaunchedEffect(Unit) {focusRequester.requestFocus()}
    }

    BasicTextField(
        modifier = Modifier
            .then(addModifier)
            .focusRequester(focusRequester)
            .clearFocusOnKeyboardDismiss(),
        state = textFieldState,
        textStyle = textStyle,
        cursorBrush = SolidColor(cursorColor),
        interactionSource = interactionSource,
        lineLimits = lineLimits,
        decorator = {innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = textFieldState.text.toString(),
                innerTextField = innerTextField,
                enabled = enabled,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                placeholder = {Text(placeholder)},
                colors = textFieldColors,
                isError = isError,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                prefix = prefix,
                suffix = suffix,
                supportingText = supportingText,
                shape = shape,
                contentPadding = contentPadding,
                singleLine = singleLine
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
private fun Modifier.clearFocusOnKeyboardDismiss(
): Modifier = composed {
    var isFocused by remember {mutableStateOf(false)}
    val focusManager = LocalFocusManager.current
    var keyboardAppearedSinceLastFocused by remember {mutableStateOf(false)}
    if (isFocused) {
        val imeIsVisible = WindowInsets.isImeVisible
        LaunchedEffect(imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}

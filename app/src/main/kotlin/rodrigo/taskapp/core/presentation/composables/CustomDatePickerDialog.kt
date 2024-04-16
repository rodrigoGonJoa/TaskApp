package rodrigo.taskapp.core.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomDatePickerDialog(
    content: @Composable () -> Unit,
    onCancel: () -> Unit,
    onConfirm: (Long) -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    fun onConfirmClicked() {
        val dateInMillis = datePickerState.selectedDateMillis
        dateInMillis.let {onConfirm}
    }

    DatePickerDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = ::onConfirmClicked) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("CANCEL")
            }
        }
    ) {
        DatePicker(
            modifier = Modifier
                .padding(top = 8.dp),
            state = datePickerState,
            title = null,
            headline = null,
            showModeToggle = false
        )
        content()
    }
}


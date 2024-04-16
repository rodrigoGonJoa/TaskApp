package rodrigo.taskapp.core.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rodrigo.taskapp.core.presentation.composables.CustomDatePickerDialog
import rodrigo.taskapp.core.presentation.composables.CustomTimePickerDialog

@Composable
fun DateTimePicker(
    showDateTimePicker: MutableState<Boolean>
) {
    var showTimePickerDialog by remember {mutableStateOf(false)}

    if (showTimePickerDialog) {
        CustomTimePickerDialog(
            onCancel = {showTimePickerDialog = false},
            onConfirm = {}
        )
    }
    if (showDateTimePicker.value) {
        CustomDatePickerDialog(
            onConfirm = {showDateTimePicker.value = false},
            onCancel = {showDateTimePicker.value = false},
            content = {
                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable {showTimePickerDialog = true},
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(id = 0 /*Todo*/),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Set time")
                }
                HorizontalDivider()
            }
        )
    }
}


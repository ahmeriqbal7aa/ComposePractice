package com.example.composeanimations.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties


@Composable
fun AlertDialogsView() {
    Box(contentAlignment = Alignment.Center) {
        // Alert dialog with one field
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            val showDialog = remember { mutableStateOf(false) }
            val textFieldValue = remember { mutableStateOf("") }

            AlertWithOneOutlineTextField(
                showDialog = showDialog.value,
                onShowDialogChange = { showDialog.value = it },
                alertDialogTitle = "Alert with One Fields",
                textFieldValue = textFieldValue.value,
                onConfirm = { newValue ->
                    println("New Value: $newValue")

                    textFieldValue.value = newValue

                    showDialog.value = false
                },
                onCancel = { showDialog.value = false }
            )

            Button(onClick = { showDialog.value = true }) {
                Text("Show One Field Dialog")
            }

        }

        // Alert dialog with two field
        Column(modifier = Modifier.align(Alignment.CenterEnd)) {
            val showDialog = remember { mutableStateOf(false) }
            val textFieldValue1 = remember { mutableStateOf("") }
            val textFieldValue2 = remember { mutableStateOf("") }

            AlertWithTwoOutlineTextFields(
                showDialog = showDialog.value,
                onShowDialogChange = { showDialog.value = it },
                alertDialogTitle = "Alert with Two Fields",
                textFieldValue1 = textFieldValue1.value,
                textFieldValue2 = textFieldValue2.value,
                onConfirm = { newFieldValue1, newFieldValue2 ->
                    println("New Value1 / New Value2: $newFieldValue1 / $newFieldValue2}")

                    textFieldValue1.value = newFieldValue1
                    textFieldValue2.value = newFieldValue2

                    showDialog.value = false
                },
                onCancel = { showDialog.value = false }
            )

            Button(onClick = { showDialog.value = true }) {
                Text("Show Two Fields Dialog")
            }
        }
    }
}

/////////////////////////////////////////////////////////////////////////////////
// ==================== With One OutlineTetField ============================= //
/////////////////////////////////////////////////////////////////////////////////

@Composable
fun AlertWithOneOutlineTextField(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    alertDialogTitle: String,
    textFieldValue: String,
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit
) {
    val textState = remember { mutableStateOf(TextFieldValue(textFieldValue)) }
    val validated = remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            ),
            shape = RoundedCornerShape(4.dp),
            onDismissRequest = { onShowDialogChange(false) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = alertDialogTitle,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    OutlinedTextField(
                        value = textState.value,
                        onValueChange = { textState.value = it },
                        placeholder = { Text(text = "Enter name") },
                        shape = RoundedCornerShape(4.dp),
                        /*isError = !validated.value,*/
                    )
                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(4.dp),
                        onClick = { onCancel() },
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(4.dp),
                        onClick = { onConfirm(textState.value.text) },
                    ) {
                        Text("Save")
                    }
                }
            },
        )
    }

    // Validate the text field
    LaunchedEffect(textState.value.text) {
        val isValid = textState.value.text.isNotBlank()
        validated.value = isValid
    }

    // Reset the text field and validation status when the dialog is dismissed
    LaunchedEffect(showDialog) {
        if (!showDialog) {
            textState.value = TextFieldValue("")
            validated.value = false
        }
    }
}


/////////////////////////////////////////////////////////////////////////////////
// ==================== With Two OutlineTetField ============================= //
/////////////////////////////////////////////////////////////////////////////////


@Composable
fun AlertWithTwoOutlineTextFields(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    alertDialogTitle: String,
    textFieldValue1: String,
    textFieldValue2: String,
    onConfirm: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    val textState1 = remember { mutableStateOf(TextFieldValue(textFieldValue1)) }
    val textState2 = remember { mutableStateOf(TextFieldValue(textFieldValue2)) }
    val validated = remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            ),
            shape = RoundedCornerShape(4.dp),
            onDismissRequest = { onShowDialogChange(false) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = alertDialogTitle,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    OutlinedTextField(
                        value = textState1.value,
                        onValueChange = { textState1.value = it },
                        placeholder = { Text(text = "Enter value 1") },
                        shape = RoundedCornerShape(4.dp),
                        /*isError = !validated.value,*/
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        value = textState2.value,
                        onValueChange = { textState2.value = it },
                        placeholder = { Text(text = "Enter value 2") },
                        shape = RoundedCornerShape(4.dp),
                        /*isError = !validated.value,*/
                    )
                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(4.dp),
                        onClick = { onCancel() },
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(4.dp),
                        onClick = { onConfirm(textState1.value.text, textState2.value.text) },
                    ) {
                        Text("Save")
                    }
                }
            },
        )
    }

    // Validate the text field
    LaunchedEffect(textState1.value.text, textState2.value.text) {
        val isField1Valid = textState1.value.text.isNotBlank()
        val isField2Valid = textState2.value.text.isNotBlank()
        validated.value = isField1Valid && isField2Valid
    }

    // Reset the text field and validation status when the dialog is dismissed
    LaunchedEffect(showDialog) {
        if (!showDialog) {
            textState1.value = TextFieldValue("")
            textState2.value = TextFieldValue("")
            validated.value = false
        }
    }
}
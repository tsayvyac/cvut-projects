package com.nurkhtsay.wastetracker.pages

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.nurkhtsay.wastetracker.R
import com.nurkhtsay.wastetracker.components.CameraView
import com.nurkhtsay.wastetracker.data.ProductEvent
import com.nurkhtsay.wastetracker.data.ProductState
import com.nurkhtsay.wastetracker.ui.theme.BackGround
import com.nurkhtsay.wastetracker.ui.theme.OnSurface
import com.nurkhtsay.wastetracker.ui.theme.Primary
import com.nurkhtsay.wastetracker.ui.theme.Purple80
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.Executors

@SuppressLint("SimpleDateFormat", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemPage(
    state: ProductState,
    onEvent: (ProductEvent) -> Boolean,
    navController: NavController
) {
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    val options = listOf("kg", "l", "ml", "mg", "pieces")
    val categories =
        listOf("Dairy products", "Meat and poultry", "Fruits", "Vegetables")
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var photoUri: Uri? by remember { mutableStateOf(Uri.parse(state.photo)) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
        }
    )
    val editable by derivedStateOf { state.existingInDb }
    val showDialog = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }

    var showCamera by remember { mutableStateOf(false) }

    var expMeasuring by remember { mutableStateOf(false) }

    var expCategories by remember { mutableStateOf(false) }

    var selectedMeasuring by remember { mutableStateOf(state.measuring) }

    var selectedCategory by remember { mutableStateOf(state.category) }

    var isDatePickerOpen by remember { mutableStateOf(false) }

    var expirationDate by remember { mutableStateOf(state.expirationDate) }

    fun handleImageCapture(uri: Uri) {
        photoUri = uri
        showCamera = false
    }

    fun getOutputDirectory(): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if ((mediaDir != null) && mediaDir.exists()) mediaDir else context.filesDir
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (showCamera) {
            CameraView(
                outputDirectory = getOutputDirectory(),
                executor = Executors.newSingleThreadExecutor(),
                onImageCaptured = ::handleImageCapture,
                onError = {},
                navController = navController
            )
        }
        TopAppBar(
            title = {
                val header = if (editable) "Update ${state.name}" else "Add new product"
                Text(text = header)
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    val text: String
                    val duration = Toast.LENGTH_SHORT
                    if (!editable) {
                        if (onEvent(ProductEvent.SaveProduct)) {
                            text = "${state.name} has been successfully added!"
                            navController.popBackStack()
                        } else {
                            text = "Please, complete all fields!"
                        }
                    } else {
                        if (onEvent(ProductEvent.UpdateProduct)) {
                            text = "${state.name} has been successfully updated!"
                            navController.popBackStack()
                        } else {
                            text = "Please, complete all fields!"
                        }
                    }
                    Toast.makeText(context, text, duration).show()
                }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = ""
                    )
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Primary)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.name,
                singleLine = true,
                onValueChange = { onEvent(ProductEvent.SetName(it)) },
                label = { Text("Product name") },
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                OutlinedTextField(
                    value = state.quantity,
                    singleLine = true,
                    label = { Text("Quantity/Volume/Weight") },
                    onValueChange = { onEvent(ProductEvent.SetQuantity(it)) },
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(256.dp)
                        .padding(end = 16.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expMeasuring,
                    onExpandedChange = {
                        expMeasuring = !expMeasuring
                    },

                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = if (editable) state.measuring else selectedMeasuring,
                        onValueChange = { },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expMeasuring)
                        },
                        modifier = Modifier.menuAnchor(),
                        label = { Text(text = "Qt")}
                    )
                    ExposedDropdownMenu(
                        expanded = expMeasuring,
                        onDismissRequest = {
                            expMeasuring = false
                        },
                    ) {
                        options.forEach { selected ->
                            DropdownMenuItem(
                                text = { Text(text = selected) },
                                onClick = {
                                    selectedMeasuring = selected
                                    onEvent(ProductEvent.SetMeasuring(selectedMeasuring))
                                    expMeasuring = false
                                }
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(photoUri),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .height(240.dp)
                            .width(240.dp)
                            .border(
                                width = 1.dp,
                                color = OnSurface,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .background(color = BackGround, shape = RoundedCornerShape(4.dp))
                            .clip(RoundedCornerShape(4.dp))
                    )
                    onEvent(ProductEvent.SetPhoto(photoUri.toString()))
                    IconButton(
                        modifier = Modifier
                            .size(240.dp)
                            .align(Alignment.Center),
                        onClick = {
                            if (!hasCameraPermission) {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            } else {
                                showCamera = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(72.dp),
                        )
                    }
                }
            }
            ExposedDropdownMenuBox(
                expanded = expCategories,
                onExpandedChange = {
                    expCategories = !expCategories
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.Start)
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = if (editable) state.category else selectedCategory,
                    onValueChange = { },
                    label = { Text(text = "Category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expCategories)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .width(240.dp)
                )
                ExposedDropdownMenu(
                    expanded = expCategories,
                    onDismissRequest = {
                        expCategories = false
                    }
                ) {
                    categories.forEach { selected ->
                        DropdownMenuItem(
                            text = { Text(text = selected) },
                            onClick = {
                                selectedCategory = selected
                                onEvent(ProductEvent.SetCategory(selectedCategory))
                                expCategories = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                readOnly = true,
                label = { Text(text = "Expiration date") },
                value = if (editable) {
                    if (state.expirationDate.contains(".")) {
                        state.expirationDate
                    } else {
                        SimpleDateFormat("dd.MM.yyyy").format(Date(state.expirationDate.toLong() * 1000))
                            .toString()
                    }
                } else expirationDate,
                onValueChange = { },
                trailingIcon = {
                    IconButton(onClick = {
                        isDatePickerOpen = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = OnSurface
                        )
                    }
                },
                supportingText = { Text(text = "dd.MM.yyyy") },
                singleLine = true,
                modifier = Modifier
                    .width(240.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.Start)
            )
            if (isDatePickerOpen) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = { isDatePickerOpen = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                isDatePickerOpen = false
                                if (datePickerState.selectedDateMillis != null) {
                                    expirationDate =
                                        formatter.format(datePickerState.selectedDateMillis)
                                    onEvent(
                                        ProductEvent.SetExpirationDate(
                                            (datePickerState.selectedDateMillis!! / 1000).toString()
                                        )
                                    )
                                }
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { isDatePickerOpen = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            if (editable) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            showDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text(
                            text = "Eaten",
                            color = Purple80
                        )
                    }

                    Button(
                        onClick = {
                            showDeleteDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text(
                            text = "Delete",
                            color = Color.White
                        )
                    }
                }
            }

        }
    }

    DisposableEffect(Unit) {
        onDispose {
            onEvent(ProductEvent.ResetProductState(Unit))
            onEvent(ProductEvent.SetExistingInDb(false))
        }
    }

    if (showDialog.value) {
        var quantityRem by remember { mutableStateOf("") }
        var diff = 0
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = "How much was eaten?")
            },
            text = {
                Row {
                    OutlinedTextField(
                        value = quantityRem,
                        onValueChange = {
                            quantityRem = it
                            if (quantityRem.isNotBlank()) {
                                diff = state.quantity.toInt() - quantityRem.toInt()
                            }
                        },
                        label = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .width(164.dp)
                            .padding(end = 16.dp),
                        trailingIcon = {
                            IconButton(
                                onClick = { quantityRem = state.quantity }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowUpward,
                                    contentDescription = "All"
                                )
                            }
                        }
                    )
                    Text(
                        text = state.measuring,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 6.dp),
                        fontSize = 16.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (diff > 0) {
                            onEvent(ProductEvent.UpdateQuantity(state.productId, diff))
                        } else {
                            onEvent(ProductEvent.IncrementEaten)
                            onEvent(ProductEvent.DeleteProductById(state.productId))
                        }
                        showDialog.value = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text(text = "Are you sure you want to delete?") },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(
                        onClick = {
                            onEvent(ProductEvent.DeleteProductById(state.productId))
                            showDeleteDialog.value = false
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete")
                    }
                    TextButton(
                        onClick = {
                            onEvent(ProductEvent.DeleteProductById(state.productId))
                            onEvent(ProductEvent.IncrementThrown)
                            showDeleteDialog.value = false
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Thrown Away")
                    }
                    TextButton(onClick = { showDeleteDialog.value = false }) {
                        Text("Cancel")
                    }
                }
            },
            confirmButton = { }
        )
    }
}
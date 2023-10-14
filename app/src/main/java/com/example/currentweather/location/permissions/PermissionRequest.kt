package com.example.currentweather.location.permissions

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import com.google.accompanist.permissions.*


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionRequest(context: Context, onGranted: () -> Unit) {
    val fineLocationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )
    var rationaleState by remember {
        mutableStateOf<RationaleState?>(null)
    }
    rationaleState?.run { PermissionRationaleDialog(rationaleState = this) }

    if (fineLocationPermissionState.shouldShowRationale) {
        rationaleState = RationaleState(
            "Request Precise Location",
            "In order to use this feature please grant access by accepting " + "the location permission dialog." + "\n\nWould you like to continue?",
        ) { proceed ->
            if (proceed) {
                fineLocationPermissionState.launchMultiplePermissionRequest()
            } else {
                Toast.makeText(
                    context,
                    "In order to use current weather location please restart the app and allow to use location",
                    Toast.LENGTH_LONG
                ).show()

            }
            rationaleState = null
        }
    } else {
        SideEffect {
            fineLocationPermissionState.launchMultiplePermissionRequest()
            onGranted.invoke()
        }
    }
}


@Composable
fun PermissionRationaleDialog(rationaleState: RationaleState) {
    AlertDialog(onDismissRequest = { rationaleState.onRationaleReply(false) }, title = {
        Text(text = rationaleState.title)
    }, text = {
        Text(text = rationaleState.rationale)
    }, confirmButton = {
        TextButton(onClick = {
            rationaleState.onRationaleReply(true)
        }) {
            Text("Continue")
        }
    }, dismissButton = {
        TextButton(onClick = {
            rationaleState.onRationaleReply(false)
        }) {
            Text("Dismiss")
        }
    })
}

data class RationaleState(
    val title: String,
    val rationale: String,
    val onRationaleReply: (proceed: Boolean) -> Unit,
)
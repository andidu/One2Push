package com.altessa.testpush

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.altessa.testpush.ui.theme.One2PushTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

private val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            grantedPermission.value = isGranted
        }

    private val grantedPermission: MutableState<Boolean> = mutableStateOf(false)

    private val permission = android.Manifest.permission.POST_NOTIFICATIONS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        grantedPermission.value = ContextCompat.checkSelfPermission(
            this, permission,
        ) == PackageManager.PERMISSION_GRANTED

        setContent {
            One2PushTheme {
                PushScreen(
                    requestPermission = {
                        requestNotificationPermission.launch(permission)
                    },
                    granted = grantedPermission.value,
                    getToken = {
                        getToken(it)
                    },
                )
            }
        }
    }
}

fun getToken(
    update: (String) -> Unit,
) {
    FirebaseMessaging
        .getInstance()
        .token
        .addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                update(task.result)
            }
        )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    One2PushTheme {
        Greeting("Android")
    }
}
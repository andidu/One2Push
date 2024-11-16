package com.altessa.testpush

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun PushScreen(
    requestPermission: () -> Unit,
    granted: Boolean,
    getToken: ((String) -> Unit) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = 32.dp,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = requestPermission) {
            Text(text = "REQUEST PERMISSION")
        }

        Text(text = if (granted) "GRANTED" else "NOT GRANTED")

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .width(80.dp)
                .background(MaterialTheme.colorScheme.primary)
        )

        val token = remember { mutableStateOf("") }

        Button(onClick = { getToken { token.value = it } }) {
            Text(text = "REFRESH TOKEN")
        }

        val clipboard = LocalClipboardManager.current

        Text(
            text = token.value,
            modifier = Modifier.clickable {
                clipboard.setText(AnnotatedString(token.value))
            }
        )
    }
}

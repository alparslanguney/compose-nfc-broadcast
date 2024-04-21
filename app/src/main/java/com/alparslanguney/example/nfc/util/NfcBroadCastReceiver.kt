package com.alparslanguney.example.nfc.util

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext

/**
 * Created by Alparslan Güney - 21.04.2024
 * Contact : seminihi@gmail.com
 */

const val INTENT_ACTION_NFC_READ = "com.alparslanguney.example.nfc.util.INTENT_ACTION_NFC_READ"

@SuppressLint("UnspecifiedRegisterReceiverFlag")
@Composable
fun NfcBroadcastReceiver(
    onSuccess: (Tag) -> Unit
) {
    val context = LocalContext.current

    val currentOnSystemEvent by rememberUpdatedState(onSuccess)

    DisposableEffect(context) {
        val intentFilter = IntentFilter(INTENT_ACTION_NFC_READ)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {
                intent?.getParcelableCompatibility(NfcAdapter.EXTRA_TAG, Tag::class.java)?.let { tag ->
                    currentOnSystemEvent(tag)
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(
                broadcast, intentFilter,
                Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            context.registerReceiver(broadcast, intentFilter)
        }

        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}
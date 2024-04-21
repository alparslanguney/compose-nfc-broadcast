package com.alparslanguney.example.nfc.util

import android.content.Intent
import android.os.Parcelable

/**
 * Created by Alparslan Güney - 21.04.2024
 * Contact : seminihi@gmail.com
 */
internal fun <T : Parcelable> Intent.getParcelableCompatibility(key: String, type: Class<T>): T? {
   return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
      getParcelableExtra(key,type)
   } else {
      getParcelableExtra(key)
   }
}
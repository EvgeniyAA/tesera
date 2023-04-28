package com.tesera.core.utils

import android.text.format.DateUtils
import java.util.*

fun Date.niceDateStr() = DateUtils.getRelativeTimeSpanString(
    this.time,
    Calendar.getInstance().timeInMillis,
    DateUtils.MINUTE_IN_MILLIS
).toString()
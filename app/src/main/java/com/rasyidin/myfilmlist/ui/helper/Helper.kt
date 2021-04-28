package com.rasyidin.myfilmlist.ui.helper

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.bumptech.glide.Glide
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.ui.helper.Constants.SUFFIX_CHARS
import com.rasyidin.myfilmlist.ui.helper.Constants.YEAR_PATTERN
import com.rasyidin.myfilmlist.ui.helper.Constants.Y_M_D_PATTERN
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import kotlin.math.ln
import kotlin.math.pow

fun ImageView.loadImage(url: String?, placeHolder: Int, errorImage: Int) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeHolder)
        .error(errorImage)
        .centerCrop()
        .into(this)
}

fun View.slideUp(animTime: Long, startOffset: Long) {
    val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideUp)
}

fun Int.toHoursAndMin(): String {
    val hours = TimeUnit.MINUTES.toHours(this.toLong())
    val min = this - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%d h %d min", hours, min)
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("SimpleDateFormat")
fun String.toYearFormat(): String {
    val inputFormat = SimpleDateFormat(Y_M_D_PATTERN)
    val outputFormat = SimpleDateFormat(YEAR_PATTERN)
    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}

fun Int.toSuffixCharsKMBPTE(): String {
    val doubleNumber = this.toDouble()
    val formatter = DecimalFormat("###.#")

    val isNumberLessThanThousand = this < 1000.0
    formatter.roundingMode = RoundingMode.DOWN

    return if (isNumberLessThanThousand) {
        formatter.format(doubleNumber)
    } else {
        val exp = (ln(doubleNumber) / ln(1000.0)).toInt()
        formatter.format(this / 1000.0.pow(exp.toDouble())) + SUFFIX_CHARS[exp - 1]
    }
}

fun Double.toSuffixCharsKMBPTE(): String {
    val formatter = DecimalFormat("###.#")

    val isNumberLessThanThousand = this < 1000.0
    formatter.roundingMode = RoundingMode.DOWN

    return if (isNumberLessThanThousand) {
        formatter.format(this)
    } else {
        val exp = (ln(this) / ln(1000.0)).toInt()
        formatter.format(this / 1000.0.pow(exp.toDouble())) + SUFFIX_CHARS[exp - 1]
    }
}
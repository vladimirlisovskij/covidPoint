package com.example.corona.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.corona.databinding.DialogProgressBinding

@Throws(Exception::class)
fun Context.getBitmapFromVectorDrawable(drawableId: Int, color: Int): Bitmap {
    return ContextCompat.getDrawable(this, drawableId)?.let {
        with(DrawableCompat.wrap(it).mutate()) {
            Bitmap.createBitmap(
                intrinsicWidth,
                intrinsicHeight,
                Bitmap.Config.ARGB_8888
            ).apply {
                val canvas = Canvas(this)
                setBounds(0, 0, canvas.width, canvas.height)
//                colorFilter =
//                    PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                draw(canvas)
            }
        }
    } ?: throw  Exception("Resource not found")
}


fun Context.createRoundBitmap(
    diameter: Int,
    bgColor: Int,
    textSize: Float,
    text: String,
    textColor: Int
): Bitmap {
    val bitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val circlePaint = Paint().apply {
        color = bgColor
    }
    canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, circlePaint)

    val textPainter = Paint().apply {
        color = textColor
        setTextSize(textSize)
        textAlign = Paint.Align.CENTER
    }
    canvas.drawText(
        text,
        (diameter) / 2f,
        (diameter + textSize / 2) / 2f,
        textPainter
    )


    return bitmap
}

fun Context.createMarker(
    text: String,
    markerImg: Int,
    fontSize: Float,
    markerWidth: Float,
    markerHeight: Float,
    labelHeight: Float,
    labelCornerRadius: Float,
    imgWidth: Float,
    imgHeight: Float,
): Bitmap {
    val dp = resources.displayMetrics.density
    val bitmap = Bitmap.createBitmap((markerWidth * dp).toInt(), (markerHeight * dp).toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val img = ResourcesCompat.getDrawable(resources, markerImg, null)
    img?.setBounds(
        ((canvas.width-imgWidth*dp)/2).toInt(),
        ((canvas.height-imgHeight*dp)/2).toInt(),
        ((canvas.width - (canvas.width-imgWidth*dp)/2)).toInt(),
        ((canvas.height - (canvas.height-imgHeight*dp)/2)).toInt(),
    )
    img?.draw(canvas)
    val paint = Paint().apply {
        color = Color.rgb(255, 255, 255)
    }
    canvas.drawRoundRect(
        RectF(0f, 0f, markerWidth * dp, labelHeight * dp),
        labelCornerRadius * dp,
        labelCornerRadius * dp,
        paint
    )
    val textPainter = Paint().apply {
        color = Color.rgb(0, 0, 0)
        textSize = fontSize * dp
        textAlign = Paint.Align.CENTER
    }
    canvas.drawText(
        text,
        (markerWidth / 2) * dp,
        (labelHeight - 10) * dp,
        textPainter
    )
    return bitmap
}


fun Context.createProgressDialog(): AlertDialog {
    return AlertDialog
        .Builder(this)
        .setCancelable(false)
        .setView(DialogProgressBinding.inflate(LayoutInflater.from(this)).root)
        .create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
}
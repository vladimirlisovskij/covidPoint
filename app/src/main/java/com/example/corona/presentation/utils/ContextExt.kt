package com.example.corona.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
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
        (diameter + textSize/2) / 2f,
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
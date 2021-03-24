package com.example.birectmidxview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.Color
import android.content.Context
import android.app.Activity

val colors : Array<Int> = arrayOf(
    "#f44336",
    "#9C27B0",
    "#2196F3",
    "#00C853",
    "#DD2C00"
).map {
    Color.parseColor(it)
}.toTypedArray()
val sizeFactor : Float = 2.9f
val strokeFactor : Float = 90f
val delay : Long = 20
val parts : Int = 5
val scGap : Float = 0.02f / parts
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawBiRectMidX(scale : Float, w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val size : Float = Math.min(w, h) / sizeFactor
    save()
    translate(w / 2, h / 2)
    paint.style = Paint.Style.STROKE
    drawRect(RectF(-size, -size, size, -size + 2 * size * sf1), paint)
    paint.style = Paint.Style.FILL
    for (j in 0..1) {
        save()
        scale(1f - 2 * j, 1f)
        drawRect(RectF(size * sf2, -size, size, -size + 2 * size * sf1), paint)
        drawLine(size, -size, size - 2 * size * sf3, -size + 2 * size * sf3, paint)
        restore()
    }
    restore()
}

fun Canvas.drawBRMXNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawBiRectMidX(scale, w, h, paint)
}
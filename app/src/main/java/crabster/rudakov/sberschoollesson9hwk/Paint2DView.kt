package crabster.rudakov.sberschoollesson9hwk

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import crabster.rudakov.sberschoollesson9hwk.figures.Curve
import crabster.rudakov.sberschoollesson9hwk.figures.Figure
import crabster.rudakov.sberschoollesson9hwk.figures.Line
import crabster.rudakov.sberschoollesson9hwk.figures.Rectangle
import java.util.ArrayList
import kotlin.math.max
import kotlin.math.min

class Paint2DView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var figure: Figure? = null
    private var path = Path()
    private val paint = Paint()
    private val figures: MutableList<Figure> = ArrayList()
    private var isCheckedRectangle = false
    private var isCheckedLine = false
    private var isCheckedCurve = false
    private var isCheckedWhite = false
    private var isCheckedRed = false
    private var isCheckedYellow = false

    /**
     * Метод отрисовывает фигуры из списка
     */
    override fun onDraw(canvas: Canvas) {
        for (x in figures) {
            when (x) {
                is Rectangle -> {
                    val left = min(x.getOrigin().x, x.getCurrent().x)
                    val right = max(x.getOrigin().x, x.getCurrent().x)
                    val top = min(x.getOrigin().y, x.getCurrent().y)
                    val bottom = max(x.getOrigin().y, x.getCurrent().y)
                    val color = x.getColor()
                    paint.color = color
                    canvas.drawRect(left, top, right, bottom, paint)
                }
                is Line -> {
                    val startX = x.getOrigin().x
                    val startY = x.getOrigin().y
                    val stopX = x.getCurrent().x
                    val stopY = x.getCurrent().y
                    val color = x.getColor()
                    paint.color = color
                    canvas.drawLine(startX, startY, stopX, stopY, paint)
                }
                is Curve -> {
                    val color = x.getColor()
                    paint.color = color
                    val newPath = x.getPath()
                    canvas.drawPath(newPath, paint)
                }
            }
        }
    }

    /**
     * Метод обрабатывает события касания
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val x1 = event.x
        val y1 = event.y
        val current = PointF(x1, y1)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                figure = createChosenFigure()
                if (figure != null) {
                    setUpPaint(figure!!.getColor())
                    if (figure is Rectangle || figure is Line) {
                        figure!!.setOrigin(current)
                    }
                    if (figure is Curve) {
                        path = Path()
                        path.moveTo(x1, y1)
                    }
                    figures.add(figure!!)
                }
            }
            MotionEvent.ACTION_MOVE -> if (figure is Curve) {
                path.lineTo(x1, y1)
                (figure as Curve).setPath(path)
                invalidate()
            } else if (figure is Rectangle || figure is Line) {
                figure!!.setCurrent(current)
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
            }
        }
        return true
    }

    /**
     * Метод устанавливает параметры рисования, устанавливая переданный цвет
     */
    private fun setUpPaint(color: Int) {
        paint.isAntiAlias = true
        paint.color = color
        paint.strokeWidth = STROKE_WIDTH
        paint.style = Paint.Style.STROKE
    }

    /**
     * Метод очищает список фигур и перерисовывает View
     */
    fun reset() {
        figures.clear()
        invalidate()
    }

    /**
     * Метод проверяет значения от CheckBox и создаёт фигуру по
     * заданным параметрам
     */
    private fun createChosenFigure(): Figure? {
        var figure: Figure? = null
        if (isCheckedRectangle) {
            if (isCheckedWhite) figure = Rectangle(Color.WHITE)
            if (isCheckedRed) figure = Rectangle(Color.RED)
            if (isCheckedYellow) figure = Rectangle(Color.YELLOW)
        } else if (isCheckedLine) {
            if (isCheckedWhite) figure = Line(Color.WHITE)
            if (isCheckedRed) figure = Line(Color.RED)
            if (isCheckedYellow) figure = Line(Color.YELLOW)
        } else if (isCheckedCurve) {
            if (isCheckedWhite) figure = Curve(Color.WHITE)
            if (isCheckedRed) figure = Curve(Color.RED)
            if (isCheckedYellow) figure = Curve(Color.YELLOW)
        }
        return figure
    }

    /**
     * Создаём сеттеры для получения значений от CheckBox
     */
    fun setCheckedRectangle(checkedRectangle: Boolean) {
        isCheckedRectangle = checkedRectangle
    }

    fun setCheckedLine(checkedLine: Boolean) {
        isCheckedLine = checkedLine
    }

    fun setCheckedCurve(checkedCurve: Boolean) {
        isCheckedCurve = checkedCurve
    }

    fun setCheckedWhite(checkedWhite: Boolean) {
        isCheckedWhite = checkedWhite
    }

    fun setCheckedRed(checkedRed: Boolean) {
        isCheckedRed = checkedRed
    }

    fun setCheckedYellow(checkedYellow: Boolean) {
        isCheckedYellow = checkedYellow
    }

    companion object {
        private const val STROKE_WIDTH = 10f
    }

}
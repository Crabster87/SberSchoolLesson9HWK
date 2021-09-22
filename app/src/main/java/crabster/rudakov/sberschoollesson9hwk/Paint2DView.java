//package crabster.rudakov.sberschoollesson9hwk;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.PointF;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import crabster.rudakov.sberschoollesson9hwk.figures.Curve;
//import crabster.rudakov.sberschoollesson9hwk.figures.Figure;
//import crabster.rudakov.sberschoollesson9hwk.figures.Line;
//import crabster.rudakov.sberschoollesson9hwk.figures.Rectangle;
//
//public class Paint2DView extends View {
//
//    private static final float STROKE_WIDTH = 10f;
//
//    private Figure figure;
//
//    private Path path = new Path();
//    private final Paint paint = new Paint();
//    private final List<Figure> figures = new ArrayList<>();
//
//    private boolean isCheckedRectangle = false;
//    private boolean isCheckedLine = false;
//    private boolean isCheckedCurve = false;
//    private boolean isCheckedWhite = false;
//    private boolean isCheckedRed = false;
//    private boolean isCheckedYellow = false;
//
//    public Paint2DView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    /**
//     * Метод отрисовывает фигуры из списка
//     */
//    @Override
//    protected void onDraw(Canvas canvas) {
//        for (Figure x : figures
//        ) {
//            if (x instanceof Rectangle) {
//                float left = Math.min(x.getOrigin().x, x.getCurrent().x);
//                float right = Math.max(x.getOrigin().x, x.getCurrent().x);
//                float top = Math.min(x.getOrigin().y, x.getCurrent().y);
//                float bottom = Math.max(x.getOrigin().y, x.getCurrent().y);
//                int color = x.getColor();
//                paint.setColor(color);
//                canvas.drawRect(left, top, right, bottom, paint);
//            } else if (x instanceof Line) {
//                float startX = x.getOrigin().x;
//                float startY = x.getOrigin().y;
//                float stopX = x.getCurrent().x;
//                float stopY = x.getCurrent().y;
//                int color = x.getColor();
//                paint.setColor(color);
//                canvas.drawLine(startX, startY, stopX, stopY, paint);
//            } else if (x instanceof Curve) {
//                int color = x.getColor();
//                paint.setColor(color);
//                Path newPath = ((Curve) x).getPath();
//                canvas.drawPath(newPath, paint);
//            }
//        }
//    }
//
//    /**
//     * Метод обрабатывает события касания
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//
//        float x1 = event.getX();
//        float y1 = event.getY();
//        PointF current = new PointF(x1, y1);
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                figure = createChosenFigure();
//                if (figure != null) {
//                    setUpPaint(figure.getColor());
//
//                    if (figure instanceof Rectangle || figure instanceof Line) {
//                        figure.setOrigin(current);
//                    }
//
//                    if (figure instanceof Curve) {
//                        path = new Path();
//                        path.moveTo(x1, y1);
//                    }
//                    figures.add(figure);
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (figure instanceof Curve) {
//                    path.lineTo(x1, y1);
//                    ((Curve) figure).setPath(path);
//                    invalidate();
//                } else if (figure instanceof Rectangle || figure instanceof Line) {
//                    figure.setCurrent(current);
//                    invalidate();
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//        return true;
//    }
//
//    /**
//     * Метод устанавливает параметры рисования, устанавливая переданный цвет
//     */
//    private void setUpPaint(int color) {
//        paint.setAntiAlias(true);
//        paint.setColor(color);
//        paint.setStrokeWidth(STROKE_WIDTH);
//        paint.setStyle(Paint.Style.STROKE);
//    }
//
//    /**
//     * Метод очищает список фигур и перерисовывает View
//     */
//    public void reset() {
//        figures.clear();
//        invalidate();
//    }
//
//    /**
//     * Метод проверяет значения от CheckBox и создаёт фигуру по
//     * заданным параметрам
//     */
//    private Figure createChosenFigure() {
//        Figure figure = null;
//        if (isCheckedRectangle) {
//            if (isCheckedWhite) figure = new Rectangle(Color.WHITE);
//            if (isCheckedRed) figure = new Rectangle(Color.RED);
//            if (isCheckedYellow) figure = new Rectangle(Color.YELLOW);
//        } else if (isCheckedLine) {
//            if (isCheckedWhite) figure = new Line(Color.WHITE);
//            if (isCheckedRed) figure = new Line(Color.RED);
//            if (isCheckedYellow) figure = new Line(Color.YELLOW);
//        } else if (isCheckedCurve) {
//            if (isCheckedWhite) figure = new Curve(Color.WHITE);
//            if (isCheckedRed) figure = new Curve(Color.RED);
//            if (isCheckedYellow) figure = new Curve(Color.YELLOW);
//        }
//        return figure;
//    }
//
//    /**
//     * Создаём сеттеры для получения значений от CheckBox
//     */
//    public void setCheckedRectangle(boolean checkedRectangle) {
//        isCheckedRectangle = checkedRectangle;
//    }
//
//    public void setCheckedLine(boolean checkedLine) {
//        isCheckedLine = checkedLine;
//    }
//
//    public void setCheckedCurve(boolean checkedCurve) {
//        isCheckedCurve = checkedCurve;
//    }
//
//    public void setCheckedWhite(boolean checkedWhite) {
//        isCheckedWhite = checkedWhite;
//    }
//
//    public void setCheckedRed(boolean checkedRed) {
//        isCheckedRed = checkedRed;
//    }
//
//    public void setCheckedYellow(boolean checkedYellow) {
//        isCheckedYellow = checkedYellow;
//    }
//
//}

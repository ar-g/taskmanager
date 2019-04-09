package ar_g.taskmanager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GraphView extends View {
  private final List<Integer> tasksPerDay = Arrays.asList(10, 6, 5, 4, 5, 1, 33);
  private int measuredHeight = 0;
  private int measuredWidth = 0;
  private Path graphPath;
  private Paint paint;

  public GraphView(Context context) {
    this(context, null);
  }

  public GraphView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    int graphColor = 0;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GraphView, 0, 0);
    try {
      int defaultColor = getContext().getResources().getColor(R.color.graph_blue);
      graphColor = a.getColor(R.styleable.GraphView_graphColor, defaultColor);
    } finally {
      a.recycle();
    }


    paint = new Paint();
    paint.setColor(graphColor);
    paint.setStrokeWidth(5);
  }


  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
    measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
    init();
    setMeasuredDimension(measuredWidth, measuredHeight);
  }

  private void init() {
    Integer max = Collections.max(tasksPerDay);

    int heightChartDivision = measuredHeight / max;
    int widthChartDivision = measuredWidth / tasksPerDay.size();


    graphPath = new Path();
    graphPath.reset();

    graphPath.moveTo(0, measuredHeight);

    for (int i = 0; i < tasksPerDay.size(); i++) {
      Integer tasksToday = tasksPerDay.get(i);

      graphPath.lineTo((i + 1) * widthChartDivision, measuredHeight - (tasksToday * heightChartDivision));
    }

    graphPath.lineTo(measuredWidth, measuredHeight);
    graphPath.lineTo(0, measuredHeight);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.drawPath(graphPath, paint);
  }
}

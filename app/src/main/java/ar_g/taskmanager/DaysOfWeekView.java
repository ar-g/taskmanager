package ar_g.taskmanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class DaysOfWeekView extends ViewGroup {
  private final List<String> days = Arrays.asList("m", "t", "w", "th", "f", "sa", "su");
  private int width = 0;

  public DaysOfWeekView(Context context) {
    super(context);
    init();
  }

  private void init() {
    for (String day : days) {
      TextView tv = new TextView(getContext());
      tv.setText(day);
      tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
      addView(tv);
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
    int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

    int height = 0;

    if (getChildCount() > 0) {
      View childView = getChildAt(0);

      LayoutParams layoutParams = childView.getLayoutParams();
      height = layoutParams.height;


      childView.measure(
        MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.AT_MOST),
        MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.AT_MOST)
      );
    }

    width = measuredWidth;

    setMeasuredDimension(measuredWidth, height);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int space = width / getChildCount();

    for (int i = 0; i < getChildCount(); i++) {
      View view = getChildAt(i);
      view.layout(i * space /*x*/, t/*y*/, i * space + space/*x+width*/, b/*y+height*/);
    }
  }
}

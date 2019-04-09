package ar_g.taskmanager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tasks);


    ViewPager vp = findViewById(R.id.vp);
    TabLayout tl = findViewById(R.id.tl);

    PagerAdapter adapter = new PageAdapter(getSupportFragmentManager());
    vp.setAdapter(adapter);
    tl.setupWithViewPager(vp);

  }


}

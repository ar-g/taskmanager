package ar_g.taskmanager.features.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ar_g.taskmanager.features.productivity.ProductivityFragment;
import ar_g.taskmanager.features.tasks.TasksFragment;

public class PageAdapter extends FragmentPagerAdapter {
  public PageAdapter(FragmentManager fm) {
    super(fm);
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    return "Tasks";
  }

  @Override public Fragment getItem(int position) {
    if (position == 0){
      return new TasksFragment();
    } else {
      return new ProductivityFragment();
    }
  }

  @Override public int getCount() {
    return 2;
  }
}

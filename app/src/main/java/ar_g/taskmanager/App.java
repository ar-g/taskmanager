package ar_g.taskmanager;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import ar_g.db.AppDatabase;

public class App extends Application {

  private AppDatabase db;

  @Override public void onCreate() {
    super.onCreate();
  }

  public AppDatabase getDb() {
    if (db == null){
      db = Room
        .databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name")
        .build();
    }
    return db;
  }

  public static App getApp(Context context){
    return ((App) context.getApplicationContext());
  }
}

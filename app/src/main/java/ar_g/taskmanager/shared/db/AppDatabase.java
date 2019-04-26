package ar_g.taskmanager.shared.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ar_g.taskmanager.shared.model.Task;

@Database(entities = {Task.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
  public abstract TaskDao taskDao();
}

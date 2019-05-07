package ar_g.taskmanager.shared.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ar_g.taskmanager.shared.model.Task;
import io.reactivex.Flowable;

@Dao
public interface TaskDao {

  @Query("SELECT * FROM Task")
  Flowable<List<Task>> getAll();

  @Insert
  void insert(Task task);
}

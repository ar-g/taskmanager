package ar_g.taskmanager.features.tasks;

import java.util.List;

import ar_g.taskmanager.shared.db.TaskDao;
import ar_g.taskmanager.shared.model.Task;
import io.reactivex.Flowable;

public class TasksRepositoryImpl implements TasksRepository {
  private final TaskDao taskDao;

  public TasksRepositoryImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override public Flowable<List<Task>> getAll() {
    return taskDao.getAll();
  }

  @Override public void insert(Task task) {
    taskDao.insert(task);
  }
}

package ar_g.taskmanager.features.tasks;

import java.util.List;

import ar_g.taskmanager.shared.model.Task;
import io.reactivex.Flowable;

public interface TasksRepository {

  Flowable<List<Task>> getAll();

  void insert(Task task);
}

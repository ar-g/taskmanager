package ar_g.taskmanager.features.tasks;

import java.util.ArrayList;
import java.util.List;

import ar_g.taskmanager.shared.model.Task;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.BehaviorSubject;

public class TasksRepositoryInMemoryImpl implements TasksRepository {
  private final List<Task> tasks = new ArrayList<>();
  private final BehaviorSubject<List<Task>> subject = BehaviorSubject.create();

  @Override public Flowable<List<Task>> getAll() {
    return subject.toFlowable(BackpressureStrategy.LATEST);
  }

  @Override public void insert(Task task) {
    tasks.add(task);
    subject.onNext(tasks);
  }
}

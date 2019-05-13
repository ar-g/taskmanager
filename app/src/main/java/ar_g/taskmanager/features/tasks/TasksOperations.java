package ar_g.taskmanager.features.tasks;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ar_g.taskmanager.shared.model.Task;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class TasksOperations {
  private final TasksRepository tasksRepository;

  @Inject
  public TasksOperations(TasksRepository tasksRepository) {this.tasksRepository = tasksRepository;}

  public Flowable<List<Task>> getAllTasksCapsLock() {
    return tasksRepository.getAll()
      .map(tasks -> {
        List<Task> resultTasks = new ArrayList<>();
        for (Task task : tasks) {
          Task resultTask = new Task(task.name.toUpperCase(), task.priority);
          resultTasks.add(resultTask);
        }
        return resultTasks;
      })
      .subscribeOn(Schedulers.io())
      /*.observeOn(AndroidSchedulers.mainThread())*/;
  }

  public void insertTask(Task task) {
    new Thread(() -> {
      tasksRepository.insert(task);
    }).start();
  }
}

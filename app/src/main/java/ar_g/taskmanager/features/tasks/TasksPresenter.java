package ar_g.taskmanager.features.tasks;

import ar_g.taskmanager.shared.model.Task;
import io.reactivex.disposables.Disposable;

public class TasksPresenter extends Presenter<TasksView> {
  private final TasksOperations tasksOperations;
  private Disposable getTasksDisposable;

  public TasksPresenter(TasksOperations tasksOperations) {this.tasksOperations = tasksOperations;}

  @Override
  public void onAttach(TasksView tasksView) {
    getTasksDisposable = tasksOperations.getAllTasksCapsLock()
      .subscribe(tasks -> {
        tasksView.setTasks(tasks);
      }, throwable -> { /*IGNORED*/});
  }

  @Override
  public void onDetach(TasksView tasksView) {
    getTasksDisposable.dispose();
  }

  public void inserTask(Task task){
    tasksOperations.insertTask(task);
  }
}

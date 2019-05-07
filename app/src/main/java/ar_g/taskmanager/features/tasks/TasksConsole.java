package ar_g.taskmanager.features.tasks;

import java.util.List;
import java.util.Scanner;

import ar_g.taskmanager.shared.model.Task;

public class TasksConsole {


  public static void main(String[] args) {

    TasksPresenter tasksPresenter = new TasksPresenter(
      new TasksOperations(
        TasksRepositoryFactory.getRepository()
      )
    );

    tasksPresenter.onAttach(new TasksView() {
      @Override public void setTasks(List<Task> tasks) {
        System.out.println("=========PRINTING===========");
        for (Task task : tasks) {
          System.out.println(task.toString());
        }
        System.out.println("=========PRINTING FINISHED===========");
      }
    });

    Scanner scanner = new Scanner(System.in);
    for (int i = 0; i < 3; i++) {
      String taskName = scanner.nextLine();
      tasksPresenter.inserTask(new Task(taskName, i));
    }
  }
}

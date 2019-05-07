package ar_g.taskmanager.features.tasks;

public class TasksRepositoryFactory {
  public static TasksRepository getRepository(){
    return new TasksRepositoryInMemoryImpl();
  }
}

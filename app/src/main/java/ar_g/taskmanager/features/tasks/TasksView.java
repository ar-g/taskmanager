package ar_g.taskmanager.features.tasks;

import java.util.List;

import ar_g.taskmanager.shared.model.Task;

interface TasksView {
  void setTasks(List<Task> tasks);
}

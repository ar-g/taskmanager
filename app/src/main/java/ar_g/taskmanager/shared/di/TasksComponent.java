package ar_g.taskmanager.shared.di;

import ar_g.taskmanager.features.tasks.TasksFragment;
import dagger.Component;

@Component(modules = TasksAppModule.class)
public interface TasksComponent {

  void inject(TasksFragment tasksFragment);
}

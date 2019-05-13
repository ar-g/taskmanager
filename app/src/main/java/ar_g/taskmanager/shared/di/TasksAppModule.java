package ar_g.taskmanager.shared.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import ar_g.taskmanager.features.tasks.TasksRepository;
import ar_g.taskmanager.features.tasks.TasksRepositoryImpl;
import ar_g.taskmanager.shared.db.AppDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class TasksAppModule {

  private final Context context;

  public TasksAppModule(Context context) {
    this.context = context;
  }

  @Provides AppDatabase provideAppDatabase(){
    return Room
      .databaseBuilder(context, AppDatabase.class, "database-name")
      .build();
  }

  @Provides TasksRepository provideRepository(AppDatabase appDatabase){
    return new TasksRepositoryImpl(appDatabase.taskDao());
  }
}

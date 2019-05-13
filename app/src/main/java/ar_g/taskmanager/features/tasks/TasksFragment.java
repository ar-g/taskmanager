package ar_g.taskmanager.features.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import ar_g.taskmanager.R;
import ar_g.taskmanager.features.addtask.AddTaskActivity;
import ar_g.taskmanager.shared.di.DaggerTasksComponent;
import ar_g.taskmanager.shared.di.TasksAppModule;
import ar_g.taskmanager.shared.di.TasksComponent;
import ar_g.taskmanager.shared.model.Task;

import static android.app.Activity.RESULT_OK;

public class TasksFragment extends Fragment implements TasksView {
  public static final int ADD_TASK_REQUEST_CODE = 101;

  private TasksAdapter adapter;
  @Inject TasksPresenter tasksPresenter;

  @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    return layoutInflater.inflate(R.layout.fragment_tasks, container, false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView rv;
    FloatingActionButton fab;
    final SwipeRefreshLayout swipeRefreshLayout;

    swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));

    fab = view.findViewById(R.id.fab);
    fab.setOnClickListener(v -> startActivityForResult(new Intent(getContext(), AddTaskActivity.class), ADD_TASK_REQUEST_CODE));

    rv = view.findViewById(R.id.rv);
    rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    adapter = new TasksAdapter(task -> Toast.makeText(getContext(), task.name + " in progress..", Toast.LENGTH_SHORT)
      .show());
    rv.setAdapter(adapter);

    TasksComponent tasksComponent = DaggerTasksComponent.builder()
      .tasksAppModule(new TasksAppModule(getContext().getApplicationContext()))
      .build();

    tasksComponent.inject(this);

    tasksPresenter.onAttach(this);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    tasksPresenter.onDetach(this);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
      if (data != null) {
        insertTask(data);
      }
    }
  }

  private void insertTask(Intent data) {
    Task task = ((Task) data.getSerializableExtra(Task.class.getName()));
    tasksPresenter.inserTask(task);
  }

  @Override public void setTasks(List<Task> tasks) {
    adapter.setData(tasks);
  }
}

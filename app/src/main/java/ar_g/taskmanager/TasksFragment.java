package ar_g.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TasksFragment extends Fragment {
  public static final int ADD_TASK_REQUEST_CODE = 101;

  @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    View view = layoutInflater.inflate(R.layout.fragment_tasks, container, false);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView rv;
    FloatingActionButton fab;


    fab = view.findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivityForResult(new Intent(getContext(), AddTaskActivity.class), ADD_TASK_REQUEST_CODE);
      }
    });

    rv = view.findViewById(R.id.rv);
    rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    TasksAdapter adapter = new TasksAdapter(new TaskClickListener() {
      @Override public void onClick(Task task) {
        Toast.makeText(getContext(), task.getName() + " in progress..", Toast.LENGTH_SHORT).show();
      }
    });
    rv.setAdapter(adapter);

    adapter.seData(generateFakeData());
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK){
      if (data != null){
        Task task = ((Task) data.getSerializableExtra(Task.class.getName()));
        Toast.makeText(getContext(), task.getName(), Toast.LENGTH_SHORT).show();
      }
    }
  }

  public List<Task> generateFakeData(){
    List<Task> tasks = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      tasks.add(new Task("Task " + i, 3));
    }
    return tasks;
  }
}

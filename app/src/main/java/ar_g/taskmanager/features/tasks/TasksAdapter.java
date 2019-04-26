package ar_g.taskmanager.features.tasks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ar_g.taskmanager.shared.model.Task;
import ar_g.taskmanager.R;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder>{
  private final List<Task> data = new ArrayList<>();
  private final TaskClickListener taskClickListener;

  public TasksAdapter(TaskClickListener taskClickListener) {
    this.taskClickListener = taskClickListener;
  }

  @NonNull @Override public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int position) {
    LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
    View view = layoutInflater.inflate(R.layout.item_task, viewGroup, false);
    final TaskViewHolder viewHolder = new TaskViewHolder(view);

    view.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (RecyclerView.NO_POSITION != viewHolder.getAdapterPosition()) {
          taskClickListener.onClick(data.get(viewHolder.getAdapterPosition()));
        }
      }
    });
    return viewHolder;
  }

  @Override public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
    Task task = data.get(position);
    taskViewHolder.setData(task);
  }

  @Override public int getItemCount() {
    return data.size();
  }

  public void seData(List<Task> data) {
    this.data.clear();
    this.data.addAll(data);
    notifyDataSetChanged();
  }
}

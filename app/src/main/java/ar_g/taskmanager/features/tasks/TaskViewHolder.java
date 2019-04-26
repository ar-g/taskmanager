package ar_g.taskmanager.features.tasks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ar_g.taskmanager.shared.model.Task;
import ar_g.taskmanager.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
  private final TextView tvTaskName;

  public TaskViewHolder(@NonNull View itemView) {
    super(itemView);
    tvTaskName = itemView.findViewById(R.id.tvTaskName);
  }

  public void setData(Task task) {
    tvTaskName.setText(task.name);
  }
}

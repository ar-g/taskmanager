package ar_g.taskmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

  private RecyclerView rv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tasks);

    rv = findViewById(R.id.rv);
    rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    TasksAdapter adapter = new TasksAdapter(new TaskClickListener() {
      @Override public void onClick(Task task) {
        Toast.makeText(TasksActivity.this, task.getName() + " in progress..", Toast.LENGTH_SHORT).show();
      }
    });
    rv.setAdapter(adapter);

    adapter.seData(generateFakeData());
  }

  public List<Task> generateFakeData(){
    List<Task> tasks = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      tasks.add(new Task("Task " + i, 3));
    }
    return tasks;
  }

  public static class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder>{
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

  public static class TaskViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvTaskName;

    public TaskViewHolder(@NonNull View itemView) {
      super(itemView);
      tvTaskName = itemView.findViewById(R.id.tvTaskName);
    }

    public void setData(Task task) {
      tvTaskName.setText(task.getName());
    }
  }

  public interface TaskClickListener{
    public void onClick(Task task);
  }
}

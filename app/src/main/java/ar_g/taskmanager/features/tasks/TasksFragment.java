package ar_g.taskmanager.features.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ar_g.taskmanager.shared.db.AppDatabase;
import ar_g.taskmanager.shared.model.Task;
import ar_g.taskmanager.shared.db.TaskDao;
import ar_g.taskmanager.shared.App;
import ar_g.taskmanager.R;
import ar_g.taskmanager.features.addtask.AddTaskActivity;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class TasksFragment extends Fragment {
  public static final int ADD_TASK_REQUEST_CODE = 101;

  private TasksAdapter adapter;
  private Handler handler;
  private Runnable postTasksRunnable;

  int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

  ThreadPoolExecutor executor = new ThreadPoolExecutor(
    NUMBER_OF_CORES,
    NUMBER_OF_CORES,
    60L,
    TimeUnit.SECONDS,
    new LinkedBlockingQueue<Runnable>()
  );

  private InsertTask insertTask;
  private Disposable getTasksDisposable;
  private FlowableSubscriber<List<Task>> flowableSubscriber;


  @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    View view = layoutInflater.inflate(R.layout.fragment_tasks, container, false);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    RecyclerView rv;
    FloatingActionButton fab;
    final SwipeRefreshLayout swipeRefreshLayout;

    swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
      }
    });

    fab = view.findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivityForResult(new Intent(getContext(), AddTaskActivity.class), ADD_TASK_REQUEST_CODE);
      }
    });

    rv = view.findViewById(R.id.rv);
    rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    adapter = new TasksAdapter(new TaskClickListener() {
      @Override public void onClick(Task task) {
        Toast.makeText(getContext(), task.name + " in progress..", Toast.LENGTH_SHORT).show();
      }
    });
    rv.setAdapter(adapter);

    getLatestTasksReactively();
  }

  private void getLatestTasksReactively() {
    Context context = getContext();
    if (context != null) {
      AppDatabase db = App.getApp(context).getDb();
      final TaskDao taskDao = db.taskDao();

      flowableSubscriber = taskDao.getAllReactively()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new FlowableSubscriber<List<Task>>() {
          @Override public void onSubscribe(Subscription s) {

          }
          @Override public void onNext(List<Task> tasks) {
            adapter.seData(tasks);

          }
          @Override public void onError(Throwable t) {
            Log.d("34", "msg", t);
          }
          @Override public void onComplete() {

          }
        });
      //        .subscribe(tasks -> {
//          adapter.seData(tasks);
//        }, throwable -> {
//          Log.d("34", "msg", throwable);
//        });
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    getTasksDisposable.dispose();

  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK){
      if (data != null){
        insertTask(data);
      }
    }
  }

  private void getLatestTasks() {
    Context context = getContext();
    if (context != null){
      AppDatabase db = App.getApp(context).getDb();
      final TaskDao taskDao = db.taskDao();
      final Handler handler = new Handler();

      executor.execute(new Runnable() {

        @Override public void run() {
          final List<Task> tasks = taskDao.getAll();

          try {
            Thread.currentThread().sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          Log.d(TasksFragment.class.getSimpleName(), Thread.currentThread().getName());

          postTasksRunnable = new Runnable() {
            @Override public void run() {
              Log.d(TasksFragment.class.getSimpleName(), Thread.currentThread().getName());

              adapter.seData(tasks);
            }
          };

          handler.post(postTasksRunnable);
        }
      });
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (handler != null && postTasksRunnable != null){
      handler.removeCallbacks(postTasksRunnable);
    }
    if (insertTask != null){
      insertTask.cancel(true);
    }
  }


  private void insertTask(Intent data) {
    Task task = ((Task) data.getSerializableExtra(Task.class.getName()));
    Context context = getContext();
    if (context != null){
      AppDatabase db = App.getApp(context).getDb();
      TaskDao taskDao = db.taskDao();

      insertTask = new InsertTask(taskDao, this);
      insertTask.execute(task);
    }
  }

  public static class InsertTask extends AsyncTask<Task, Void, Void> {
    private final TaskDao taskDao;
    private final WeakReference<TasksFragment> tasksFragment;

    public InsertTask(TaskDao taskDao, TasksFragment tasksFragment) {
      this.taskDao = taskDao;
      this.tasksFragment = new WeakReference<>(tasksFragment);
    }

    @Override protected Void doInBackground(Task... tasks) {
      for (Task task : tasks) {
        taskDao.insert(task);
      }
      return null;
    }

    @Override protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      TasksFragment tasksFragment = this.tasksFragment.get();
      if (tasksFragment != null){
        tasksFragment.getLatestTasks();
      }
    }
  }
}

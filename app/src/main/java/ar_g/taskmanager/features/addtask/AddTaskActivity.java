package ar_g.taskmanager.features.addtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ar_g.taskmanager.shared.model.Task;
import ar_g.taskmanager.R;

public class AddTaskActivity extends AppCompatActivity implements PriorityDialogListener {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final EditText etName = findViewById(R.id.etName);
    final Button btnAdd = findViewById(R.id.btnAdd);
    final Button btnPriority = findViewById(R.id.btnPriority);

    btnPriority.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        PriorityDialogFragment priorityDialogFragment = new PriorityDialogFragment();
        priorityDialogFragment.show(getSupportFragmentManager(), PriorityDialogFragment.TAG);
      }
    });

    btnAdd.setEnabled(false);
    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        Task task = new Task(etName.getText().toString(), 0);
        Intent data = new Intent().putExtra(Task.class.getName(), task);
        setResult(RESULT_OK, data);
        finish();
      }
    });

    etName.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        String text = s.toString();
        btnAdd.setEnabled(!text.isEmpty());
      }
    });
  }

  @Override public void onPriorityChosen(int priority) {
    Toast.makeText(this, "Priority is " + priority, Toast.LENGTH_SHORT).show();
  }
}

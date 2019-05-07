package ar_g.taskmanager.features.tasks;

public abstract class Presenter<View> {

  public abstract void onAttach(View view);

  public abstract void onDetach(View view);
}

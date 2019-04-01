package ar_g.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Task implements Serializable {
  private static final long serialVersionUID = 2975820333949550622L;

  @PrimaryKey(autoGenerate = true)
  public int id;
  public final String name;
  public final int priority;
  

  public Task(String name, int priority) {
    this.name = name;
    this.priority = priority;
  }
}

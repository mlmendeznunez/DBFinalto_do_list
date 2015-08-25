import java.util.List;
import org.sql2o.*;
import java.util.Arrays;

public class Category{
  private int id;
  private String name;


  public int getId(){
    return id;
  }

  public String getName() {
    return name;
  }

  public Category(String name){
    this.name = name;
  }

  public static List<Category> all() {
    String sql = "SELECT id, name FROM Categories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }//end List<Category>

  @Override
  public boolean equals(Object otherCategory) {
    if (!(otherCategory instanceof Category)){
      return false;
    } else {
      Category newCategory = (Category) otherCategory;
      return this.getName().equals(newCategory.getName()) &&
             this.getId() == newCategory.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Categories (name) VALUES (:name) ";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Category find(int id) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM Categories where id=:id";
      Category category = con.createQuery(sql)
      .addParameter ("id", id)
      .executeAndFetchFirst(Category.class);
    return category;
    }
  }

  public List<Task> getTasks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Tasks where categoryId=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Task.class);
    }
  }

  public void clearTasks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tasks WHERE categoryId=:id;";
      con.createQuery(sql).addParameter("id",id).executeUpdate();
    }
  }

  public static void removeCategory(int categoryId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM categories WHERE id=:id;";
      con.createQuery(sql).addParameter("id",categoryId).executeUpdate();
    }
  }
  
}//end of Category class

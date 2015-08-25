import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;


public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }
  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame(){
    Task firstTask = new Task("Mow the lawn", 1);
    Task secondTask = new Task("Mow the lawn", 1);
    assertTrue(firstTask.equals(secondTask));
  } //end of test

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame(){
    Task myTask = new Task("Mow the lawn", 1);
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  } //end of test

  //
  @Test
  public void save_assignsIDToObject(){
    Task myTask = new Task("Mow the lawn", 1);//create a task object
    myTask.save(); // to find saved task object
    Task savedTask = Task.all().get(0);// method to find saved task object
    assertEquals(myTask.getId(), savedTask.getId());
  }//end of test

  @Test
  public void all_savesIntoDatabase_true(){
    Task myTask = new Task("Mow the lawn", 1);
    myTask.save();
    assertEquals(Task.all().get(0).getDescription(), "Mow the lawn");
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn", 1);
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getDescription(), "Mow the lawn");
  }


@Test
public void save_savesCategoryIdIntoDB_true() {
  Category myCategory = new Category("Household chores");
  myCategory.save();
  Task myTask = new Task("Mow the lawn", myCategory.getId());
  myTask.save();
  Task savedTask = Task.find(myTask.getId());
  assertEquals(savedTask.getCategoryId(), myCategory.getId());
}

} //end of class TaskTest

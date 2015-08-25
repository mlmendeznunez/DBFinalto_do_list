
import java.util.*;

import java.io.Console;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
      staticFileLocation("/public");
      String layout = "templates/layout.vtl";

      //homepage
      get("/", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();

       List<Category> categories = Category.all(); //allows us to see all categories on the main pg

       model.put("categories", categories);
       model.put("template", "templates/categories.vtl");
       return new ModelAndView(model, layout);
     }, new VelocityTemplateEngine());

     //list of categories, option to create new category
     get("/categories", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();

       List<Category> categories = Category.all();

       model.put("categories", Category.all());
       model.put("template", "templates/categories.vtl");
       return new ModelAndView(model, layout);
     }, new VelocityTemplateEngine());

     //form to add new category
     get("categories/new", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();

       model.put("template", "templates/category-form.vtl");
       return new ModelAndView(model, layout);
     }, new  VelocityTemplateEngine());

     //after adding new category update category list
     post("/categories", (request,response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();

       Category newCategory = new Category(request.queryParams("name"));
       newCategory.save(); //added to link to database

       List<Category> categories = Category.all();

       model.put("category", newCategory);
       model.put("template", "templates/categories.vtl"); //success.vtl
       return new ModelAndView(model, layout);
       }, new VelocityTemplateEngine());

      //page for a specific category
    get("/categories/:id", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();

       Category category = Category.find(Integer.parseInt(request.params(":id")));
       List<Task> tasks = category.getTasks();

       model.put("tasks", tasks);
       model.put("category", category);
       model.put("template", "templates/category.vtl");
       return new ModelAndView(model, layout);
     }, new VelocityTemplateEngine());

     //add tasks to a specific category
     get("/categories/:id/tasks/new", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        Category category = Category.find(Integer.parseInt(request.params(":id")));

        model.put("category", category);
        model.put("template", "templates/category-tasks-form.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      //post to specific category page
        post("/categories/:id", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();

          int categoryId = Integer.parseInt(request.params(":id"));
          Category category = Category.find(categoryId);
          Task newTask = new Task(request.queryParams("description"),categoryId);
          newTask.save();
          List<Task> tasks = category.getTasks();

          model.put("category", category);
          model.put("tasks",tasks);
          model.put("template", "templates/category.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/categories/:id/cleartasks", (request,response) -> {
          HashMap<String,Object> model = new HashMap<String,Object>();

          int categoryId = Integer.parseInt(request.params(":id"));
          Category category = Category.find(categoryId);
          category.clearTasks();

          model.put("category", category);
          // model.put("tasks",tasks);
          model.put("template","templates/category.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/categories/:id/removecategory", (request,response) -> {
          HashMap<String,Object> model = new HashMap<String,Object>();

          int categoryId = Integer.parseInt(request.params(":id"));
          Category.removeCategory(categoryId);
          List<Category> categories = Category.all();

          model.put("categories", categories);
          model.put("template","templates/categories.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/categories/:id/:taskid/done", (request,response) -> {
          HashMap<String,Object> model = new HashMap<String,Object>();

          int categoryId = Integer.parseInt(request.params(":id"));
          int taskId = Integer.parseInt(request.params(":taskid"));
          Task.removeTask(taskId);
          Category category = Category.find(categoryId);
          List<Task> tasks = category.getTasks();

          model.put("tasks",tasks);
          model.put("category", category);
          model.put("template","templates/category.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

    //  get("/tasks", (request, response) -> {
    //    Map<String, Object> model = new HashMap<String, Object>();
    //    model.put("tasks", Task.all());
    //    model.put("template", "templates/tasks.vtl");
    //    return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("tasks/new", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/task-form.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    // post("/tasks", (request,response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Category category = Category.find(Integer.parseInt(request.queryParams("categoryId")));
    //   String description = request.queryParams("description");
    //   //do we need to add a categoryId above?
    //   Task newTask = new Task(description, categoryId);
    //   category.addTask(newTask);
    //   model.put("category", category);
    //   model.put("template", "templates/category.vtl");
    //   return new ModelAndView(model, layout);
    //   }, new VelocityTemplateEngine());
    //
    // get("/tasks/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Task task = Task.find(Integer.parseInt(request.params(":id")));
    //   model.put("task", task);
    //   model.put("template", "templates/task.vtl");
    //   return new ModelAndView(model, layout);
    //   }, new VelocityTemplateEngine());
  }
}//end app

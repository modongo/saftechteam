import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oDepartmentDao;
import dao.SectionDao;
import dao.Sql2oSectionDao;
import models.Department;
import models.Section;
import dao.DB;
//import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        // This tells our app that if Heroku sets a port for us, we need to use that port.
        // Otherwise, if they do not, continue using port 4567.

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);
//         final Sql2o sql2o = null;


        staticFileLocation("/public");
//        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oSectionDao sectionDao = new Sql2oSectionDao();
        Sql2oDepartmentDao departmentDao = new Sql2oDepartmentDao();


        //get: show all tasks in all categories and show all categories
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> allCategories = departmentDao.getAll();
            model.put("departments", allCategories);
            List<Section> tasks = sectionDao.getAll();
            model.put("tasks", tasks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new category
        get("/departments/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = departmentDao.getAll(); //refresh list of links for navbar
            model.put("departments", departments);
            return new ModelAndView(model, "department-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new category
        post("/departments", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Department newDepartment = new Department(name);
            departmentDao.add(newDepartment);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: delete all categories and all tasks
        get("/departments/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            departmentDao.clearAllDepartments();
            sectionDao.clearAllSections();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all tasks
        get("/sections/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            sectionDao.clearAllSections();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific department (and the sections it contains)
        get("/departments/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToFind = Integer.parseInt(req.params("id")); //new
            Department foundDepartment = departmentDao.findById(idOfDepartmentToFind);
            model.put("department", foundDepartment);
            List<Section> allSectionsByDepartment = departmentDao.getAllSectionsByDepartment(idOfDepartmentToFind);
            model.put("sections", allSectionsByDepartment);
            model.put("departments", departmentDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "department-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a category
        get("/departments/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editDepartment", true);
            Department department = departmentDao.findById(Integer.parseInt(req.params("id")));
            model.put("department", department);
            model.put("departments", departmentDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a category
        post("/department/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newCategoryName");
            departmentDao.update(idOfDepartmentToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual task
        get("/departments/:department_id/sections/:section_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSectionToDelete = Integer.parseInt(req.params("section_id"));
            sectionDao.deleteById(idOfSectionToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new task form
        get("/sections/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = departmentDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "section-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new task form
        post("/sections/", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            List<Department> allDepartments = departmentDao.getAll();
            model.put("departments", allDepartments);
            String description = req.queryParams("description");
            int departmentId = Integer.parseInt(req.queryParams("departmentId"));
            Section newSection = new Section(description, departmentId);        //See what we did with the hard coded categoryId?
            sectionDao.add(newSection);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual section that is nested in a department
        get("/departments/:department_id/sections/:section_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSectionToFind = Integer.parseInt(req.params("section_id")); //pull id - must match route segment
            Section foundSection = sectionDao.findById(idOfSectionToFind); //use it to find section
            int idOfDepartmentToFind = Integer.parseInt(req.params("department_id"));
            Department foundDepartment = departmentDao.findById(idOfDepartmentToFind);
            model.put("department", foundDepartment);
            model.put("section", foundSection); //add it to model for template to display
            model.put("departments", departmentDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "section-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a section
        get("/sections/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> allDepartments = departmentDao.getAll();
            model.put("departments", allDepartments);
            Section section = sectionDao.findById(Integer.parseInt(req.params("id")));
            model.put("department", section);
            model.put("editSection", true);
            return new ModelAndView(model, "section-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a task
        post("/sections/:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            int sectionToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("description");
            int newDepartmentId = Integer.parseInt(req.queryParams("departmentId"));
            sectionDao.update(sectionToEditId, newContent, newDepartmentId);  // remember the hardcoded categoryId we placed? See what we've done to/with it?
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }
}

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;


import dao.DB;
import dao.Sql2oDepartmentDao;
import dao.Sql2oSectionDao;
import dao.Sql2oStaffDao;
import models.Department;
import models.Section;
import models.Staff;
//import org.sql2o.Sql2o;

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



        staticFileLocation("/public");
        Sql2oSectionDao sectionDao = new Sql2oSectionDao(DB.sql2o);
        Sql2oDepartmentDao departmentDao = new Sql2oDepartmentDao(DB.sql2o);
        Sql2oStaffDao staffDao = new Sql2oStaffDao(DB.sql2o);


        //get: show all tasks in all department and show all sections
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> allCategories = departmentDao.getAll();
            model.put("departments", allCategories);
            List<Section> tasks = sectionDao.getAll();
            model.put("section", tasks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new department
        get("/departments/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = departmentDao.getAll(); //refresh list of links for navbar
            model.put("departments", departments);
            return new ModelAndView(model, "department-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new department
        post("/departments", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Department newDepartment = new Department(name);
            departmentDao.add(newDepartment);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: delete all departments and all sections
        get("/departments/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            departmentDao.clearAllDepartments();
            sectionDao.clearAllSections();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all sections
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

        //get: show a form to update a department
        get("/departments/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editDepartment", true);
            Department department = departmentDao.findById(Integer.parseInt(req.params("id")));
            model.put("department", department);
            model.put("departments", departmentDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a department
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

       // section: process new section form
        post("/sections", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> allCategories = departmentDao.getAll();
            model.put("categories", allCategories);
            String description = req.queryParams("description");
            int categoryId = Integer.parseInt(req.queryParams("departmentId"));
            Section newTask = new Section(description, categoryId ); //ignore the hardcoded categoryId
            sectionDao.add(newTask);
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
            return new ModelAndView(model, "section-detail.hbs"); //individual section page.
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

        //Employee Section Begins here


        //get: show new staff form
        get("/staff/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Section> sections = sectionDao.getAll();
            model.put("sections", sections);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());


        //Section: process new staff form
        post("/staff", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Section> allsections= sectionDao.getAll();
            model.put("sections", allsections);
            String firstname = req.queryParams("firstname");
            String lastname = req.queryParams("lastname");
            String ekNo = req.queryParams("ekNo");
            String designation = req.queryParams("designation");
            int SectionId = Integer.parseInt(req.queryParams("SectionId"));
            Staff newstaff = new Staff(firstname,lastname, ekNo, designation,SectionId ); //ignore the hardcoded categoryId
            staffDao.add(newstaff);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual staff that is nested in a Section
        get("/sections/:Section_id/staff/:staff_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfstaffToFind = Integer.parseInt(req.params("staff_id"));
            Staff foundstaff = staffDao.findById(idOfstaffToFind);
            int idOfSectionToFind = Integer.parseInt(req.params("Section_id"));
            Section foundSection = sectionDao.findById(idOfSectionToFind);
            model.put("staff", foundstaff);
            model.put("Section", foundSection);
            model.put("sections", sectionDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "staff-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a staff
        get("/staff/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Section> allsections = sectionDao.getAll();
            model.put("sections", allsections);
            Staff staff = staffDao.findById(Integer.parseInt(req.params("id")));
            model.put("staff", staff);
            model.put("editstaff", true);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());


        //post: process a form to update a staff
        post("/staff/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int staffToEditId = Integer.parseInt(req.params("id"));
            String newfirstname = req.queryParams("firstname");
            String newlastname = req.queryParams("lastname");
            String newEKNo = req.queryParams("ekNo");
            String newDesignation = req.queryParams("designation");
            int newScetionID = Integer.parseInt(req.queryParams("SectionId"));
            staffDao.update(staffToEditId, newfirstname, newlastname,newScetionID,newDesignation, newEKNo);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all sections
        get("/staff/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            staffDao.clearAllStaff();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }

    }


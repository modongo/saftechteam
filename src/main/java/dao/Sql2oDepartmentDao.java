package dao;

import models.Department;
import models.Section;
//import models.Staff;
import org.sql2o.Connection;

import org.sql2o.Sql2oException;


import java.util.List;

public class Sql2oDepartmentDao implements DepartmentDao {


    public Sql2oDepartmentDao(){


    }

    @Override
    //public void add(Category category) { ****
    public void add(Department department) {

//        String sql = "INSERT INTO categories (name) VALUES (:name)"; ****
        String sql = "INSERT INTO departments (name) VALUES (:name)";
        try(Connection con = DB.sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    //.bind(category)*****
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            //category.setId(id);****
            department.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAll() {
        try(Connection con = DB.sql2o.open()){
//            return con.createQuery("SELECT * FROM categories")****
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Department.class);
        }
    }

    @Override
    public Department findById(int id) {
        try(Connection con = DB.sql2o.open()){
//            return con.createQuery("SELECT * FROM categories WHERE id = :id")****
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Department.class);
        }
    }

    @Override
    public void update(int id, String newName){
//        String sql = "UPDATE categories SET name = :name WHERE id=:id"; ****
        String sql = "UPDATE departments SET name = :name WHERE id=:id";
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
//        String sql = "DELETE from categories WHERE id=:id"; //raw sql ****
        String sql = "DELETE from departments WHERE id=:id"; //raw sql
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllDepartments() {
//        String sql = "DELETE from categories"; //raw sql ****
        String sql = "DELETE from departments"; //raw sql
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Section> getAllSectionsByDepartment(int departmentId) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM sections WHERE categoryid = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Section.class);
        }
    }

}

package dao;

import models.Section;
import org.sql2o.*;
import java.util.List;

public class Sql2oSectionDao implements SectionDao {

    public Sql2oSectionDao(){
//        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(Section section) {
        String sql = "INSERT INTO sections (description, departmentId) VALUES (:description, :departmentId)"; //raw sql
        try(Connection con = DB.sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(section)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            section.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Section> getAll() {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM sections") //raw sql
                    .executeAndFetch(Section.class); //fetch a list
        }
    }

    @Override
    public Section findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM sections WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Section.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String newDescription, int newCategoryId){
        String sql = "UPDATE sections SET (description, categoryId) = (:description, :categoryId) WHERE id=:id";   //raw sql
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("description", newDescription)
                    .addParameter("categoryId", newCategoryId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from sections WHERE id=:id";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllSections() {
        String sql = "DELETE from sections";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}

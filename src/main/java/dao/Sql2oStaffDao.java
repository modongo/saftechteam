package dao;

import models.Staff;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oStaffDao  implements StaffDao{

    private final Sql2o sql2o;

    public Sql2oStaffDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(Staff staff) {
        String sql = "INSERT INTO staff (firstname, lastname,sectionId, ekNo, role) VALUES (:firstname, :lastname, :sectionId, :ekNo, :role)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(staff)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            staff.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Staff> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM staff") //raw sql
                    .executeAndFetch(Staff.class); //fetch a list
        }
    }


    @Override
    public Staff findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM staff WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Staff.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String newFirstname, String newLastname, int newsectionId, String newEkNo, String newrole){
        String sql = "UPDATE Staff SET (firstname, lastname, sectionId, ekNo, role) = (:firstname, :lastname, :sectionId, :ekNo, :role) WHERE id=:id"; //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("newFirstame", newFirstname)
                    .addParameter("newLastname", newLastname)
                    .addParameter("sectionId", newsectionId)
                    .addParameter("id", id)
                    .addParameter("ekNo", newEkNo)
                    .addParameter("role", newrole)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from Staff WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllStaff() {
        String sql = "DELETE from Staff";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}

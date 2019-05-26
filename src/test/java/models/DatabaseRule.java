package models;

import dao.DB;
import org.junit.rules.ExternalResource;
import org.sql2o.*;


public class DatabaseRule  extends ExternalResource{
    @Override
    protected void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/sfctech", "mike", "1*K2enya3");
    }

    @Override
    protected void after() {
        String deleteSectionsQuery = "DELETE FROM sections *;";
        String deleteDepartmentsQuery = "DELETE FROM departments *;";
        try(Connection con = DB.sql2o.open()) {
            con.createQuery(deleteSectionsQuery).executeUpdate();
            con.createQuery(deleteDepartmentsQuery).executeUpdate();
        }
    }
}

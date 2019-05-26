package dao;

import models.Department;
import models.Section;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class Sql2oDepartmentDaoTest {
    private Sql2oDepartmentDao departmentDao;
    private Sql2oSectionDao sectionDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2oDepartmentDao();
        sectionDao = new Sql2oSectionDao();
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingDepartmentSetsId() throws Exception {
        Department department =setupNewDepartment();
        int originaldepartmentId = department.getId();
        departmentDao.add(department);
        assertNotEquals(originaldepartmentId, department.getId());
    }

    @Test
    public void existingCategoriesCanBeFoundById() throws Exception {
        Department department =setupNewDepartment();
        departmentDao.add(department);
        Department founddepartment = departmentDao.findById(department.getId());
        assertEquals(department, founddepartment);
    }

    @Test
    public void addedCategoriesAreReturnedFromGetAll() throws Exception {
        Department department =setupNewDepartment();
        departmentDao.add(department);
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void noCategoriesReturnsEmptyList() throws Exception {
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void updateChangesDepartmentContent() throws Exception {
        String initialDescription = "Yardwork";
        Department department =new Department (initialDescription);
        departmentDao.add(department);
        departmentDao.update(department.getId(),"Cleaning");
        Department updateddepartment =departmentDao.findById(department.getId());
        assertNotEquals(initialDescription, updateddepartment.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectDepartment() throws Exception {
        Department department =setupNewDepartment();
        departmentDao.add(department);
        departmentDao.deleteById(department.getId());
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void clearAllClearsAllCategories() throws Exception {
        Department department =setupNewDepartment();
        Department otherdepartment =new Department("DIT");
        departmentDao.add(department);
        departmentDao.add(otherdepartment);
        int daoSize = departmentDao.getAll().size();
        departmentDao.clearAllDepartments();
        assertTrue(daoSize > 0 && daoSize > departmentDao.getAll().size());
    }

    @Test
    public void getAllSectionssByDepartmentReturnsSectionsCorrectly() throws Exception {
        Department department =setupNewDepartment();
        departmentDao.add(department);
        int departmentId = department.getId();
        Section newSection = new Section("VAS Support", departmentId);
        Section otherSection = new Section("Revenue Management", departmentId);
        Section thirdSection = new Section("Financial Services", departmentId);
        sectionDao.add(newSection);
        sectionDao.add(otherSection); //we are not adding Section 3 so we can test things precisely.
        assertEquals(2, departmentDao.getAllSectionsByDepartment(departmentId).size());
        assertTrue(departmentDao.getAllSectionsByDepartment(departmentId).contains(newSection));
        assertTrue(departmentDao.getAllSectionsByDepartment(departmentId).contains(otherSection));
        assertFalse(departmentDao.getAllSectionsByDepartment(departmentId).contains(thirdSection)); //things are accurate!
    }

    // helper method
    public Department setupNewDepartment(){
        return new Department("CSO");
    }
}
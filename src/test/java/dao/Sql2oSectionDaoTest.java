package dao;

import models.Section;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oSectionDaoTest {
    private Sql2oSectionDao SectionDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
//        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
        SectionDao = new Sql2oSectionDao(); //ignore me for now
        conn = DB.sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    @Test
    public void addingSectionSetsId() throws Exception {
        Section section = new Section("VAS Supportn",3);
        int originalSectionId = section.getId();
        SectionDao.add(section);
        assertNotEquals(originalSectionId, section.getId()); //how does this work?
    }
    @Test
    public void existingSectionsCanBeFoundById() throws Exception {
        Section section = new Section("VAS Supportn",3);
        SectionDao.add(section); //add to dao (takes care of saving)
        Section foundSection = SectionDao.findById(section.getId()); //retrieve
        assertEquals(section, foundSection); //should be the same
    }
}
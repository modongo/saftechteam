package models;

import org.sql2o.*;
import java.time.LocalDateTime;
import org.junit.*;
import static org.junit.Assert.*;

public class SectionTest {
    Section mySection;

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Before
    public void setUp() {
        mySection = new Section("Mow the lawn", 1);
    }

//    @Test
//    public void Section_instantiatesCorrectly_true() {
//        assertTrue(mySection instanceof Section);
//    }
//
//    @Test
//    public void Section_instantiatesWithDescription_String() {
//        assertEquals("Mow the lawn", mySection.getDescription());
//    }
//
//    @Test
//    public void isCompleted_isFalseAfterInstantiation_false() {
//        assertFalse(mySection.getCompleted());
//    }
//
//    @Test
//    public void getCreatedAt_instantiatesWithCurrentTime_today() {
//        assertEquals(LocalDateTime.now().getDayOfWeek(), mySection.getCreatedAt().getDayOfWeek());
//    }
//
//    @Test
//    public void all_returnsAllInstancesOfSection_true() {
//        Section firstSection = new Section("Mow the lawn", 1);
//        firstSection.save();
//        Section secondSection = new Section("Buy groceries", 1);
//        secondSection.save();
//        assertTrue(Section.all().get(0).equals(firstSection));
//        assertTrue(Section.all().get(1).equals(secondSection));
//    }
//
//    @Test
//    public void getId_SectionsInstantiateWithAnID_1() {
//        mySection.save();
//        assertTrue(mySection.getId() > 0);
//    }
//
//    @Test
//    public void find_returnsSectionWithSameId_secondSection() {
//        Section firstSection = new Section("Mow the lawn", 1);
//        firstSection.save();
//        Section secondSection = new Section("Buy groceries", 1);
//        secondSection.save();
//        assertEquals(Section.find(secondSection.getId()), secondSection);
//    }
//
//    @Test
//    public void equals_returnsTrueIfDescriptionsAreTheSame() {
//        Section firstSection = new Section("Mow the lawn", 1);
//        Section secondSection = new Section("Mow the lawn", 1);
//        assertTrue(firstSection.equals(secondSection));
//    }
//
//    @Test
//    public void save_returnsTrueIfDescriptionsAreTheSame() {
//        mySection.save();
//        assertTrue(Section.all().get(0).equals(mySection));
//    }
//
//    @Test
//    public void save_assignsIdToObject() {
//        mySection.save();
//        Section savedSection = Section.all().get(0);
//        assertEquals(mySection.getId(), savedSection.getId());
//    }
//
//    @Test
//    public void update_updatesSectionDescription_true() {
//        mySection.save();
//        mySection.update("Take a nap");
//        assertEquals("Take a nap", Section.find(mySection.getId()).getDescription());
//    }
//
//    @Test
//    public void delete_deletesSection_true() {
//        mySection.save();
//        int mySectionId = mySection.getId();
//        mySection.delete();
//        assertNull(Section.find(mySectionId));
//    }
}

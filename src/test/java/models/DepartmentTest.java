package models;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class DepartmentTest {

    private Department testDepartment;

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Before
    public void setUp() {
        testDepartment = new Department("Home");
    }

//    @Test
//    public void Department_instantiatesCorrectly_true() {
//        assertTrue(testDepartment instanceof Department);
//    }
//
//    @Test
//    public void getName_DepartmentInstantiatesWithName_Home() {
//        assertEquals("Home", testDepartment.getName());
//    }
//
//    @Test
//    public void all_returnsAllInstancesOfDepartment_true() {
//        Department firstDepartment = new Department("Home");
//        firstDepartment.save();
//        Department secondDepartment = new Department("Work");
//        secondDepartment.save();
//        assertTrue(Department.all().get(0).equals(firstDepartment));
//        assertTrue(Department.all().get(1).equals(secondDepartment));
//    }
//
//    @Test
//    public void getId_categoriesInstantiateWithAnId_1() {
//        testDepartment.save();
//        assertTrue(testDepartment.getId() > 0);
//    }
//
//    @Test
//    public void find_returnsDepartmentWithSameId_secondDepartment() {
//        Department firstDepartment = new Department("Home");
//        firstDepartment.save();
//        Department secondDepartment = new Department("Work");
//        secondDepartment.save();
//        assertEquals(Department.find(secondDepartment.getId()), secondDepartment);
//    }
//
//    @Test
//    public void getSections_initiallyReturnsEmptyList_ArrayList() {
//        assertEquals(0, testDepartment.getSections().size());
//    }
//
//    @Test
//    public void getSections_retrievesAllSectionsFromDatabase_SectionsList() {
//        Department myDepartment = new Department("Household chores");
//        myDepartment.save();
//        Section firstSection = new Section("Mow the lawn", myDepartment.getId());
//        firstSection.save();
//        Section secondSection = new Section("Do the dishes", myDepartment.getId());
//        secondSection.save();
//        Section[] Sections = new Section[] { firstSection, secondSection };
//        assertTrue(myDepartment.getSections().containsAll(Arrays.asList(Sections)));
//    }
//
//    @Test
//    public void find_returnsNullWhenNoSectionFound_null() {
//        assertNull(Department.find(999));
//    }
//
//    @Test
//    public void equals_returnsTrueIfNamesAretheSame() {
//        Department firstDepartment = new Department("Household chores");
//        Department secondDepartment = new Department("Household chores");
//        assertTrue(firstDepartment.equals(secondDepartment));
//    }
//
//    @Test
//    public void save_savesIntoDatabase_true() {
//        Department myDepartment = new Department("Household chores");
//        myDepartment.save();
//        assertTrue(Department.all().get(0).equals(myDepartment));
//    }
//
//    @Test
//    public void save_assignsIdToObject() {
//        Department myDepartment = new Department("Household chores");
////        myDepartment.save();
//        Department savedDepartment = Department.class.get(0);
//        assertEquals(myDepartment.getId(), savedDepartment.getId());
//    }
//
//    @Test
//    public void save_savesDepartmentIdIntoDB_true() {
//        Department myDepartment = new Department("Household chores");
//        myDepartment.save();
//        Section mySection = new Section("Mow the lawn", myDepartment.getId());
//        mySection.save();
//        Section savedSection = Section.find(mySection.getId());
//        assertEquals(savedSection.getDepartmentId(), myDepartment.getId());
//    }
}

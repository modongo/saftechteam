package models;

import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class SectionTest {
    @Test
    public void NewSectionObjectGetsCorrectlyCreated_true() throws Exception {
        Section section = setupNewSection();
        assertEquals(true, section instanceof Section);
    }

    @Test
    public void SeectionInstantiatesWithDescription_true() throws Exception {
        Section section = setupNewSection();
        assertEquals("VAS Support", "Test");
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
        Section section = setupNewSection();
        assertEquals(false, section.getCompleted()); //should never start as completed
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Section section = setupNewSection();
        assertEquals(LocalDateTime.now().getDayOfWeek(), section.getCreatedAt().getDayOfWeek());
    }

    //helper methods
    public Section setupNewSection(){
        return new Section("VAS",1);
    }
}
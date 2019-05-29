package models;

import java.util.Objects;

public class Staff {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEkNo() {
        return ekNo;
    }

    public void setEkNo(String ekNo) {
        this.ekNo = ekNo;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    private String firstname;
    private String lastname;
    private String ekNo;
    private String designation;
    private int sectionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Staff)) return false;
        Staff staff = (Staff) o;
        return id == staff.id &&
                sectionId == staff.sectionId &&
                firstname.equals(staff.firstname) &&
                lastname.equals(staff.lastname) &&
                Objects.equals(ekNo, staff.ekNo) &&
                Objects.equals(designation, staff.designation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, ekNo, designation, sectionId);
    }

    public Staff(String firstname, String lastname, String ekNo, String designation, int sectionId) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.ekNo = ekNo;
        this.designation = designation;
        this.sectionId = sectionId;
    }
}
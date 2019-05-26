package models;

import java.util.Objects;

public class Department {
    private String name;
    private int id;

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department department = (Department) o;
        return id == department.id &&
                Objects.equals(name, department.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

}

package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;


public class Section {

    private String description;
    private int departmentId;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;

    public Section(String description, int departmentId){
        this.description = description;
        this.departmentId = departmentId;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public int getCategoryId() {
        return departmentId;
    }

    public void setCategoryId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section task = (Section) o;
        return completed == task.completed &&
                id == task.id &&
                Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, completed, id);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public boolean getCompleted(){
        return this.completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }
}

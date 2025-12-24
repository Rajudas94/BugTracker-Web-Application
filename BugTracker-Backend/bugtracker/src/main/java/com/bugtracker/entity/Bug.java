package com.bugtracker.entity;

import com.bugtracker.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Bug implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "assigned_UserId")  // Foreign Key Column in Bug Table
    @JsonIgnore // added on 17/12/25
    private User assignedTo;    // Refrerence to the Users Table Object

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String difficulty;

    // Empty Constructor
    public Bug(){}

    // Parameterised Constructor
    public Bug(String title, String description, String status, String difficulty){
        this.title = title;
        this.description = description;
        this.status = status;
        this.difficulty = difficulty;
    }

    // Getter and Setter Functions
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getStatus(){
        return status;
    } // <----

    public void setStatus(String status) {
        this.status = status;
    } // <----

    public String getDifficulty(){ return difficulty; }

    public void setDifficulty(String difficulty){ this.difficulty = difficulty; }

    @JsonIgnore
    public User getAssignedTo(){ return  assignedTo; }

    public void setAssignedTo(User assignedTo){ this.assignedTo = assignedTo; }
}





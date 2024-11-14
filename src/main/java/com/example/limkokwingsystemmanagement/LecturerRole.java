package com.example.limkokwingsystemmanagement;

public class LecturerRole {
    private String name;
    private String role;
    private String faculty;
    private String classes;
    private String module;
    private String qualifications;
    private String contacts;
    private String email;

    // Constructor, getters, and setters

    public LecturerRole(String name, String role, String faculty, String classes, String module, String qualifications, String contacts, String email) {
        this.name = name;
        this.role = role;
        this.faculty = faculty;
        this.classes = classes;
        this.module = module;
        this.qualifications = qualifications;
        this.contacts = contacts;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

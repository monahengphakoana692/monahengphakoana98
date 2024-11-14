package com.example.limkokwingsystemmanagement;

public class Report
{
    private String className;
    private String module;
    private String challenges;
    private String recommendations;
    private String dateSubmitted;

    // Constructor
    public Report(String className, String module, String challenges, String recommendations, String dateSubmitted) {
        this.className = className;
        this.module = module;
        this.challenges = challenges;
        this.recommendations = recommendations;
        this.dateSubmitted = dateSubmitted;
    }

    // Getters and Setters
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getChallenges() {
        return challenges;
    }

    public void setChallenges(String challenges) {
        this.challenges = challenges;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
}
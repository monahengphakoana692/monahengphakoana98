package com.example.limkokwingsystemmanagement;

public class ChapterWithOutcomes {


    private int chapterNumber;
    private String chapterName;
    private String chapterDescription;
    private String learningOutcomes;

    // Constructor
    public ChapterWithOutcomes(int chapterNumber, String chapterName, String chapterDescription, String learningOutcomes) {
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.chapterDescription = chapterDescription;
        this.learningOutcomes = learningOutcomes;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }


    // Getters
    public String getChapterName() {
        return chapterName;
    }

    public String getChapterDescription() {
        return chapterDescription;
    }


    public String getLearningOutcomes() {
        return learningOutcomes;
    }
}

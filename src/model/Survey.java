package model;

public class Survey {
    private Integer surveyId;
    private String title;
    private String description;
    private String location;

    public Survey() {}

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public static String hasValidFields(String title, String description){
        String error = "";

        if (title.length() == 0) {
            error = error + "Name is required. \n";
        }
        if (description.length() == 0) {
            error = error + "Description is required. \n";
        }
        
        return error;
    }
}
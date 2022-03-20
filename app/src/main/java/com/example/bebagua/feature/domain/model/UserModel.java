package com.example.bebagua.feature.domain.model;

public class UserModel {

    private String userUID;
    private String userNickName;
    private String userImageURL = null;
    private String userGoal = "0";
    private String userProgress = "0";

    public UserModel(String userUID, String userNickName){
        this.userUID = userUID;
        this.userNickName = userNickName;
    }

    public UserModel(){}

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {this.userUID = userUID;}

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getUserGoal() {
        return userGoal;
    }

    public void setUserGoal(String userGoal) {
        this.userGoal = userGoal;
    }

    public String getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(String userProgress) {
        this.userProgress = userProgress;
    }
}

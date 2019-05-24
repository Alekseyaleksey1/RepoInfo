package com.example.aleksei.repoinfo.model.pojo;

import com.google.gson.annotations.SerializedName;

public class POJOModelDetailed {//todo delet dis

    int forks;
    @SerializedName("subscribers_count")
    int subscribersCount;
    @SerializedName("stargazers_count")
    int stargazersCount;

    public int getSubscribersCount() {
        return subscribersCount;
    }

    public void setSubscribersCount(int subscribersCount) {
        this.subscribersCount = subscribersCount;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }
}

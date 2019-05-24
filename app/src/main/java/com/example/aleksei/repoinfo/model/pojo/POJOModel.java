package com.example.aleksei.repoinfo.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class POJOModel implements Parcelable {//todo remove Parcelable?
    int id;
    String name;
    int forks;
    String description;
    String url;
    @SerializedName("full_name")
    String fullName;
    @SerializedName("stargazers_count")
    int stargazersCount;
    @SerializedName("watchers_count")
    int watchersCount;

    public POJOModel() {
    }

    protected POJOModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        fullName = in.readString();
        description = in.readString();
        url = in.readString();
        stargazersCount = in.readInt();
        watchersCount = in.readInt();
        forks = in.readInt();
    }

    public static final Creator<POJOModel> CREATOR = new Creator<POJOModel>() {
        @Override
        public POJOModel createFromParcel(Parcel in) {
            return new POJOModel(in);
        }

        @Override
        public POJOModel[] newArray(int size) {
            return new POJOModel[size];
        }
    };

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(int watchersCount) {
        this.watchersCount = watchersCount;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeInt(stargazersCount);
        dest.writeInt(watchersCount);
        dest.writeInt(forks);
    }
}

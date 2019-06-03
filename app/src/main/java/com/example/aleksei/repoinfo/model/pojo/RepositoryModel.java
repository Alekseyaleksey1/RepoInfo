package com.example.aleksei.repoinfo.model.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

@Entity
public class RepositoryModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int idT;
    private String name;
    private int id;
    private int forks;
    private String description;
    private String url;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("stargazers_count")
    private int stargazersCount;
    @SerializedName("watchers_count")
    private int watchersCount;
    @SerializedName("open_issues")
    private int openIssues;

    public RepositoryModel() {
    }

    protected RepositoryModel(Parcel in) {
        name = in.readString();
        id = in.readInt();
        forks = in.readInt();
        description = in.readString();
        url = in.readString();
        fullName = in.readString();
        stargazersCount = in.readInt();
        watchersCount = in.readInt();
        openIssues = in.readInt();
    }

    public static final Creator<RepositoryModel> CREATOR = new Creator<RepositoryModel>() {
        @Override
        public RepositoryModel createFromParcel(Parcel in) {
            return new RepositoryModel(in);
        }

        @Override
        public RepositoryModel[] newArray(int size) {
            return new RepositoryModel[size];
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

    public int getIdT() {
        return idT;
    }

    public void setIdT(int idT) {
        this.idT = idT;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(forks);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(fullName);
        dest.writeInt(stargazersCount);
        dest.writeInt(watchersCount);
        dest.writeInt(openIssues);
    }
}

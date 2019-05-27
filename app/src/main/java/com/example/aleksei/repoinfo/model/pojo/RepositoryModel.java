package com.example.aleksei.repoinfo.model.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity
public class RepositoryModel implements Parcelable {

   // @NonNull
   // @PrimaryKey

    @NonNull
    @PrimaryKey(autoGenerate = true)

    int idT;

    String name;
    int id;

    int forks;
    String description;
    String url;
    @SerializedName("full_name")
    String fullName;
    @SerializedName("stargazers_count")
    int stargazersCount;
    @SerializedName("watchers_count")
    int watchersCount;

    public RepositoryModel(){}
    protected RepositoryModel(Parcel in) {
        name = in.readString();
        id = in.readInt();
        forks = in.readInt();
        description = in.readString();
        url = in.readString();
        fullName = in.readString();
        stargazersCount = in.readInt();
        watchersCount = in.readInt();
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
    }
}

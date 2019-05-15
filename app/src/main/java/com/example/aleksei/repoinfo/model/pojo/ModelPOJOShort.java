package com.example.aleksei.repoinfo.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPOJOShort implements Parcelable{
    int id;
    String name;
    String full_name;//todo rename this fields
    String description;
    String url;
    int stargazers_count;
    int watchers_count;
    int forks;

    public ModelPOJOShort(){}
    protected ModelPOJOShort(Parcel in) {
        id = in.readInt();
        name = in.readString();
        full_name = in.readString();
        description = in.readString();
        url = in.readString();
        stargazers_count = in.readInt();
        watchers_count = in.readInt();
        forks = in.readInt();
    }

    public static final Creator<ModelPOJOShort> CREATOR = new Creator<ModelPOJOShort>() {
        @Override
        public ModelPOJOShort createFromParcel(Parcel in) {
            return new ModelPOJOShort(in);
        }

        @Override
        public ModelPOJOShort[] newArray(int size) {
            return new ModelPOJOShort[size];
        }
    };

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public int getWatchers_count() {
        return watchers_count;
    }

    public void setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
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

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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
        dest.writeString(full_name);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeInt(stargazers_count);
        dest.writeInt(watchers_count);
        dest.writeInt(forks);
    }
}

package com.example.aleksei.repoinfo.model.pojo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity
class RepositoryModel() : Parcelable {

    @PrimaryKey(autoGenerate = true)
     var idT : Int? = null
     var name : String? = null
     var id : Int? = null
     var forks : Int? = null
     var description : String? = null
     var url : String? = null
    @SerializedName("full_name")
     var fullName : String? = null
    @SerializedName("stargazers_count")
    var stargazersCount : String? = null
    @SerializedName("watchers_count")
     var watchersCount : String? = null
    @SerializedName("open_issues")
     var openIssues : String? = null

    constructor(parcel: Parcel) : this() {
        idT = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        forks = parcel.readValue(Int::class.java.classLoader) as? Int
        description = parcel.readString()
        url = parcel.readString()
        fullName = parcel.readString()
        stargazersCount = parcel.readString()
        watchersCount = parcel.readString()
        openIssues = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idT)
        parcel.writeString(name)
        parcel.writeValue(id)
        parcel.writeValue(forks)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(fullName)
        parcel.writeString(stargazersCount)
        parcel.writeString(watchersCount)
        parcel.writeString(openIssues)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepositoryModel> {
        override fun createFromParcel(parcel: Parcel): RepositoryModel {
            return RepositoryModel(parcel)
        }

        override fun newArray(size: Int): Array<RepositoryModel?> {
            return arrayOfNulls(size)
        }
    }


}
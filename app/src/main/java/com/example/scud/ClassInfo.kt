package com.example.scud


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "classInfo")
data class ClassInfo (

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") val id: Int?,
    @SerializedName("_id") val _id: String?,
    @SerializedName("user") val user: String?,
    @SerializedName("classroom") val classroom: String?,
    @SerializedName("classroomName") val classroomName: String?,
    @SerializedName("classroomType") val classroomType: String?,
    @SerializedName("classroomKey") val classroomKey: String?,
    @SerializedName("__v") val __v: String?

)
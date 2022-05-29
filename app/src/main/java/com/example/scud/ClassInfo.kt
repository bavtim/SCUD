package com.example.scud

import com.google.gson.annotations.SerializedName

data class ClassInfo (

    @SerializedName("_id") val _id: String?,
    @SerializedName("user") val user: String?,
    @SerializedName("classroom") val classroom: String?,
    @SerializedName("classroomName") val classroomName: String?,
    @SerializedName("classroomType") val classroomType: String?,
    @SerializedName("classroomKey") val classroomKey: String?,
    @SerializedName("__v") val __v: String?

)
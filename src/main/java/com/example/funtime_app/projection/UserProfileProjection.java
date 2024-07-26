package com.example.funtime_app.projection;

import java.util.UUID;

public interface UserProfileProjection {

//    "fullName": "FirstName12 LastName12",
//            "userId": "89eb3f3f-e763-4edb-b14a-cc7efeea043a",
//            "rate": 0.0,
//            "postCount": 3,
//            "profilePhotoId": "79757846-17cc-47a7-885f-b28886eda281",
//            "bannerId": null,
//            "followers": 0
    String getFullname();
    UUID getUserId();
    Double getRate();
    Integer getPostCount();
    UUID getProfilePhotoId();
    UUID getBannerId();
    Integer getFollowers();
}

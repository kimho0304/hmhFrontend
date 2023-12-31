package kr.example.ttubuckttubuck.dto;

import com.google.gson.annotations.SerializedName;

public class FollowDto {
    @SerializedName("id")
    private Long id;
    @SerializedName("followingId")
    private Long followingId;

    public FollowDto(long member, Long followingId) {
        this.id = member;
        this.followingId = followingId;
    }
}

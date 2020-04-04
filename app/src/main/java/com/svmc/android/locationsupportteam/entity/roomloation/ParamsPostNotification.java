package com.svmc.android.locationsupportteam.entity.roomloation;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;

/**
 * Created by TungTS on 5/13/2019
 */

public class ParamsPostNotification {

    @SerializedName("sender")
    private SenderParams sender;

    @SerializedName("room_location")
    private RoomParams room;

    @SerializedName("message")
    private String message;

    @SerializedName("type")
    private String type;

    @SerializedName("point")
    private LocationPoint point;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("url_image")
    private String urlImage;

    /**
     * for alert and sos
     * @param sender
     * @param room
     * @param message
     * @param type
     */
    public ParamsPostNotification(SenderParams sender, RoomParams room, String message, String type, LocationPoint point) {
        this.sender = sender;
        this.room = room;
        this.message = message;
        this.type = type;
        this.point = point;
    }

    /***
     * For share location
     * @param sender
     * @param room
     * @param message
     * @param type
     * @param point
     */
    public ParamsPostNotification(SenderParams sender, RoomParams room, String message, String type, LocationPoint point, String placeId) {
        this.sender = sender;
        this.room = room;
        this.message = message;
        this.type = type;
        this.point = point;
        this.placeId = placeId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public SenderParams getSender() {
        return sender;
    }

    public void setSender(SenderParams sender) {
        this.sender = sender;
    }

    public RoomParams getRoom() {
        return room;
    }

    public void setRoom(RoomParams room) {
        this.room = room;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocationPoint getPoint() {
        return point;
    }

    public void setPoint(LocationPoint point) {
        this.point = point;
    }

    public static class SenderParams {

        @SerializedName("user_id")
        private String userId;

        @SerializedName("name")
        private String nameUser;

        @SerializedName("phone")
        private String phone;

        public SenderParams(String userId, String nameUser, String phone) {
            this.userId = userId;
            this.nameUser = nameUser;
            this.phone = phone;
        }

        public String getNameUser() {
            return nameUser;
        }

        public void setNameUser(String nameUser) {
            this.nameUser = nameUser;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class RoomParams {

        @SerializedName("room_id")
        private String roomId;

        @SerializedName("name")
        private String nameRoom;

        public RoomParams(String roomId) {
            this.roomId = roomId;
        }

        public RoomParams(String roomId, String nameRoom) {
            this.roomId = roomId;
            this.nameRoom = nameRoom;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getNameRoom() {
            return nameRoom;
        }

        public void setNameRoom(String nameRoom) {
            this.nameRoom = nameRoom;
        }
    }

}

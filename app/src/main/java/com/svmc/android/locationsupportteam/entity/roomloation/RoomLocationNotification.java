package com.svmc.android.locationsupportteam.entity.roomloation;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;

/**
 * Create by TungTS on 5/2/2019
 */

public class RoomLocationNotification {

    public static final String STATUS_SOS = "SOS";
    public static final String STATUS_ALERT = "ALERT";
    public static final String STATUS_INVITED = "INVITED";
    public static final String STATUS_SHARE_PLACE = "SHARE_PLACE";

    @SerializedName("notify_id")
    private String notifyId;

    @SerializedName("created_time")
    private long createdTime;

    @SerializedName("receiver")
    private ReceiverUser receiverUser;

    @SerializedName("sender")
    private SenderUser senderUser;

    @SerializedName("room_location")
    private Room room;

    @SerializedName("type")
    private String type;

    @SerializedName("message")
    private String message;

    @SerializedName("point")
    private LocationPoint point;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("url_image")
    private String image;

    @SerializedName("is_resolve")
    private boolean isResolve;

    public boolean isResolve() {
        return isResolve;
    }

    public void setResolve(boolean resolve) {
        isResolve = resolve;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public LocationPoint getPoint() {
        return point;
    }

    public void setPoint(LocationPoint point) {
        this.point = point;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public ReceiverUser getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(ReceiverUser receiverUser) {
        this.receiverUser = receiverUser;
    }

    public SenderUser getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(SenderUser senderUser) {
        this.senderUser = senderUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public class ReceiverUser {

        @SerializedName("user_id")
        private String userId;

        @SerializedName("display_name")
        private String displayName;

        @SerializedName("isRead")
        private boolean isRead;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }
    }

    public class SenderUser {

        @SerializedName("user_id")
        private String userId;

        @SerializedName("name")
        private String displayName;

        @SerializedName("phone")
        private String phone;

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

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    public class Room {

        @SerializedName("name")
        private String name;

        @SerializedName("room_id")
        private String roomId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }
    }

}

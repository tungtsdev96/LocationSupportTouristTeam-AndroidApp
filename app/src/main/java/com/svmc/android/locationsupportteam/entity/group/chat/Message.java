package com.svmc.android.locationsupportteam.entity.group.chat;

import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;

public class Message {

    private String messageId;

    private String content;

    private long timeCreated;

    private String userId;

    public Message(String messageId, String content, long timeCreated, String userId) {
        this.messageId = messageId;
        this.content = content;
        this.timeCreated = timeCreated;
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static Message fakeUser1() {
        Message message = new Message("1", "aaaa", 111111, "onD82joKoNdnZGIyrLllqXfXSVh2");
        return message;
    }

    public static Message fakeUser2() {
        Message message = new Message("2", "bbbbb", 111111, "2");
        return message;
    }

    public static Message fakeUser() {
        Message message = new Message("2", "bbbbb", 111111, FirebaseUtils.getFirebaseAuth().getCurrentUser().getUid());
        return message;
    }

}

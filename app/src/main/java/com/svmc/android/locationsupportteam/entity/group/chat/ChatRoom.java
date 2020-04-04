package com.svmc.android.locationsupportteam.entity.group.chat;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 4/6/2019
 */

public class ChatRoom {

    private String groupId;

    private String nameGroup;

    private String lastMessage;

    private long timestamp;

    private ArrayList<Message> messages;

    public ChatRoom(){}

    public ChatRoom(String groupId, String nameGroup, String lastMessage, long timestamp) {
        this.groupId = groupId;
        this.nameGroup = nameGroup;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static ChatRoom fake() {
        ChatRoom chatRoom = new ChatRoom("aaa", "tungts", "Tran so ntunf", 11122222);
        return chatRoom;
    }
}

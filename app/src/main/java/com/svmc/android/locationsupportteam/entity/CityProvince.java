package com.svmc.android.locationsupportteam.entity;

/**
 * Created by TungTS on 5/14/2019
 */

public class CityProvince {


    /**
     * id : 1
     * key : Hồ Chí Minh
     * name : Thành phố Hồ Chí Minh
     * code : SG
     */

    private String id;
    private String key;
    private String name;
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

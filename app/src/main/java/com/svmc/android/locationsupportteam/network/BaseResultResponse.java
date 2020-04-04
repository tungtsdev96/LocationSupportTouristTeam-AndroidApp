package com.svmc.android.locationsupportteam.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class BaseResultResponse<T> {

    @SerializedName("status")
    private int status;

    @SerializedName("next_page")
    private boolean isNextPage;

    @SerializedName("result")
    private T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isNextPage() {
        return isNextPage;
    }

    public void setNextPage(boolean nextPage) {
        isNextPage = nextPage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

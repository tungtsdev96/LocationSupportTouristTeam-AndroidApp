package com.svmc.android.locationsupportteam.entity.googlemap.distance;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.BaseResponseGoogle;

import java.util.List;

/**
 * Created by TungTS on 5/7/2019
 */

public class ResultDistance extends BaseResponseGoogle {

    @SerializedName("destination_addresses")
    private List<String> destinationAddresses;

    @SerializedName("origin_addresses")
    private List<String> originAddresses;

    @SerializedName("rows")
    private List<Row> rows;

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

}

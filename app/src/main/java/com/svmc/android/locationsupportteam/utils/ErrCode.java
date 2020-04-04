package com.svmc.android.locationsupportteam.utils;

/**
 * Create by TUNGTS on 4/18/2019
 */

public final class ErrCode {

    public interface TripCode {
        // Add Trip
        int HAVE_NO_START_POINT = 1;
        int HAVE_NO_END_POINT = 2;
        int HAVE_NO_START_DATE = 3;
        int HAVE_NO_RETURN_DATE = 4;
    }

    public interface AuthCode {
        int ERR_INVALID_TOKEN = 6;
    }

    public interface GroupCode {
        int ERR_REQUIRE_NAME_GROUP = 1;
        int ERR_AT_LEAST_MEMBER_GROUP = 2;
        int ERR_CHOOSE_PRIVACY = 3;
        int ERR_USER_CREATED = 4;
        int ERR_SERVER = 5;

    }

    public interface RoomLocationCode {
        int ERR_REQUIRE_NAME = 1;
        int ERR_REQUIRE_MEMBER = 2;
        int ERR_SERVER = 3;
    }

    public interface CommonCode {

        int RESULT_OK = 201;

        int BAD_REQUEST = 400;

        int ERR_SERVER = 500;
    }

    public interface GGMapCode {

        String OK = "OK";

        String ZERO_RESULTS  = "ZERO_RESULTS";

        String INVALID_REQUEST  = "INVALID_REQUEST";

        String UNKNOWN_ERROR = "UNKNOWN_ERROR";

    }
}

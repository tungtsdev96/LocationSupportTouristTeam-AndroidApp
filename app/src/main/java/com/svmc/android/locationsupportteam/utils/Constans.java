package com.svmc.android.locationsupportteam.utils;

/**
 * Created by TUNGTS on 3/17/2019
 */

public final class Constans {

    public interface TagFragment {
        // Home
        String HOME_FRAGMENT = "MainFragment";
        String NEW_FEED_FRAGMENT = "NewFeedFragment";
        String MAP_FRAGMENT = "MapsFragment";

        //Trip
        String TRIP_FRAGMENT = "TripActivity";
        String MY_TRIP_FRAGMENT = "MyTripFragment";
        String RECENTED_VIEW_TRIP_FRAGMENT = "RecentedViewTripFragment";
        String EDIT_INFOR_TRIP_FRAGMENT = "EditInforTripFragment";
        String LIST_DAY_OF_TRIP_FRAGMENT = "ListDayOfTripFragment";
        String EDIT_DETAIL_DAY_OF_TRIP_FRAGMENT = "EditDayOfTripFragment";
        String EDIT_DAY_OF_TRIP_FRAGMENT = "EditDetailDayOfTripFragment";
        String ADD_RECOMMEND_PLACE_BY_TYPE_FRAGMENT = "AddRecommendPlaceByTypeFragment";
        String CHOOSE_RECOMMEND_PLACE_BY_TYPE_FRAGMENT = "ChooseRecommendPlaceByTypeFragment";

        String DETAIL_TRIP_FRAGMENT = "DetailTripFragment";
        String INFOR_DAY_OF_TRIP_FRAGMENT = "InforDayOfTripFragment";
        String DETAIL_DAY_OF_TRIP_FRAGMENT = "DetailTripFragment";

//        // Group
//        String MY_GROUP_FRAGMENT = "MyGroupFragment";
//        String MANAGER_GROUP_FRAGMENT = "ManagerGroupFragment";
//        String GROUP_FRAGMENT = "GroupFragment";
//        String DISCOVER_GROUP_FRAGMENT = "DiscoverGroupFragment";
//        String DETAIL_GROUP_IS_NOT_MEMBER_FRAGMENT = "DetailGroupIsNotMemeberFragment";
//        String DETAIL_GROUP_IS_MEMBER_FRAGMENT = "DetailGroupIsMemeberFragment";
//        String CREATE_POST_IN_GROUP_FRAGMENT = "CreatePostInGroupFragment";
//        String CREATE_GROUP_FRAGMENT = "CreateGroupFragment";
//        String DETAIL_ROOM_CHAT_FRAGMENT = "DetailRoomChatFragment";
//        String LIST_MEMBER_IN_GROUP_FRAGMENT = "ListMemberInGroupFragment";
//        String MEMBER_IN_GROUP_FRAGMENT = "MemberInGroupFragment";
//        String CHAT_FRAGMENT = "ChatFragment";
        ////////////////////////////////////////////////////////////////

        String LIST_TRIP_FRAGMENT = "ListTripFragment";
        String LIST_PLACE_FRAGMENT = "ListPlaceFragment";
        String NEAR_BY_SEARCH_FRAGMENT = "NearBySearchFragment";
        String NEAR_BY_SEARCH_ITEM_FRAGMENT = "NearBySearchItemFragment";
        String EDIT_PROFILE_FRAGMENT = "EditProfileFragment";
        String CREATE_ROOM_LOCATION = "CreateRoomLocationFragment";
        String DETAIL_ROOM_LOCATION_FRAGMENT = "DetailRoomLocationFragment";
        String NAVI_ROOM_LOCATION_FRAGMENT = "NaviRoomLocationFragment";
        String INVITE_MEMBER_TO_ROOM = "InviteMemberToRoomFragment";
        String ITEM_PLACE_FRAGMENT_IN_MAP = "ItemPlaceFragmentInMap";
        String DETAIL_PLACE_FRAGMENT = "DetailPlaceFragment";
        String INVITED_NOTIFICATION_FRAGMENT = "InvitedNotificationFragment";
        String SHARED_NOTIFICATION_FRAGMENT = "SharedNotificationFragment";
        String MEMBERS_IN_GROUP_FRAGMENT = "MembersInRoomFragment";
        String ALERT_NOTIFICATION_FRAGMENT = "AlertNotifyFragment";
    }

    public interface KeyPreference {
        String PREFERENCE_FILE_KEY = "com.svmc.android.locationsupportteam";
        String IS_FIRST_START_APP = "com.svmc.android.locationsupportteam.is_first_start_app";
        String USER = "com.svmc.android.locationsupportteam.user";
        String ROOM_ID_JOINED = "com.svmc.android.locationsupportteam.room_id_joinded";
        String TURN_ON_LOCATION = "com.svmc.android.locationsupportteam.turn_on_location";
        String TURN_VIBRATE = "com.svmc.android.locationsupportteam.turn_vibrate";
        String LIST_PLACE_TYPE = "com.svmc.android.locationsupportteam.list_place_type";
        String TURN_SYNCHRONIZE = "com.svmc.android.locationsupportteam.synchronize";
        String TIME_SYNC = "com.svmc.android.locationsupportteam.time_synchronize";
        String TURN_ON_NOTIFICATION = "com.svmc.android.locationsupportteam.turn_on_notification";
    }

    public interface RequestCode {
        int RC_SIGN_IN = 9001;
        int RC_ADD_RECOMMEND_PLACE = 9999;

        int RC_SEARCH_PLACE = 1001;

        int RC_LOCATION_PERMISSION = 1002;
        int RC_CHECK_SETTINGS_CURRENT_LOCATION = 1003;
        int RC_FILTER_PLACE = 1004;
        int RC_ADD_MEMBER_TO_GROUP = 1005;
        int RC_GALLERY_IMAGE = 1006;

        int RC_CALL_PHONE = 1007;
        int RC_CHOOSE_PLACE_TYPE = 1008;

        int RC_START_POINT = 1009;
        int RC_END_POINT = 1010;
        int RC_IMAGE_CAPTURE = 1011;
    }

    public interface AdapterItem {

        int ITEM_VIEW_LOADING = 0;
        int ITEM_VIEW_LOAD_MORE = 1;

        int ITEM_VIEW_TRIP = 99;
        int ITEM_VIEW_PLACE_RECOMMEND = 100;

        int ITEM_TITLE = 101;

        int ITEM_GROUP = 102;
        int ITEM_POST_IN_GROUP = 103;
        int ITEM_GROUP_CHAT = 104;
        int ITEM_RIGHT_MESSAGE = 105;
        int ITEM_LEFT_MESSAGE = 106;
        int ITEM_VIEW_MEMBER_IN_GROUP = 107;

        int ITEM_HOME_SCREEN = 108;
        int ITEM_VIEW_PLACE = 109;
        int ITEM_VIEW_RECENTED_SEARCH_IN_NEW_FEED = 110;
        int ITEM_VIEW_SEARCH_IN_MAP = 111;
        int ITEM_VIEW_ADD_MEMBER = 112;
        int ITEM_VIEW_MEMBER_ON_OFF = 113;
        int ITEM_VIEW_ROOM_NOTIFY = 114;
        int ITEM_REVIEW_PLACE = 115;
        int ITEM_SEARCH_CITY_PROVINCE = 116;
        int ITEM_VIEW_ROOM_NOTIFY_ALERT = 117;
        int ITEM_VIEW_ROOM_NOTIFY_SHARE = 118;
    }

    public interface KeyBundle {

        // trip
        String TRIP_INFOR = "trip_infor";
        String CURRENT_ITEM_TYPE_PLACE = "Current_item_type_place";

        // group

        // search
        String SEARCH_PLACE = "Search_place";

        String ITEM_HOME_SCREEN_MODEL = "HomeScreenModel";

        // FilterPlace
        String FILTER_PLACE = "FilterPlace";
        String FILTER_PLACE_RANKBY = "FilterPlaceRankBy";
        String FILTER_PLACE_PRICE = "FilterPlacePrice";
        String FILTER_PLACE_OPEN_NOW = "FilterPlaceOpenNow";

        // Room Location
        String CURRENT_FRAG_ON_ROOM_LOCATION = "CurrentFragOnLocation";
        String ROOM_LOCATION = "RoomLocation";
        String ROOM_LOCATION_SHARE_POINT = "share_point";
        String ROOM_ALERT_DATA = "room_alert_data";
        String ROOM_USER_SENDER = "room_sender_id";

        // Profile
        String UPDATE_PROFILE = "UpdateProfile";

        // place
        String ID_PLACE = "IdPlace";
        String CURRENT_FRAG_ON_PLACE = "CurrentFragOnPlace";
        String TRAVEL_MODE = "TraveLMode";

        String CHOOSE_PLACE_TYPE = "ChoosePlaceType";
        String CURRENT_LOCATION = "CurrentLocation";
        String KEY_TYPE_PLACE = "KEY_TYPE_PLACE";
        String ROOM_NOTIFY_URL_IMAGE = "urlmage";

        //show direction
        String SHOW_DIRECTION_ORIGIN = "oringi";
        String SHOW_DIRECTION_DESTINATION = "destination";
    }

    public interface KeyShowListItemTrip {
        int RECENTED_CREATE_TRIP_PLAN = 1;
        int POPULAR_TRIP_PLAN = 2;
        int SUGGESTION_TRIP_PLAN = 3;
    }

    public interface KeyShowListItemPlace {
        int RECENTED_CREATE_TRIP_PLAN = 4;
        int TOP_HOTEL = 5;
        int TOP_RESTAURANT = 6;
        int NEAR_PLACE = 7;
    }

    public interface TypeHomeModel {
        String HOTEL = "hotel";
        String RESTAURANT = "restaurant";

    }

    public interface IntentAction {

       String ACTION_UPLOAD = "action_upload";
       String UPLOAD_COMPLETED = "upload_completed";
       String UPLOAD_ERROR = "upload_error";

    }


}

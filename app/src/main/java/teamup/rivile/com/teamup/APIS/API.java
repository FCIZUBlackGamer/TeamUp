package teamup.rivile.com.teamup.APIS;

public class API {
    //    final public static String BASE_URL = "http://www.teamupapi.rivile.com/";
    final public static String BASE_URL = "http://192.168.1.2/";
    final public static String URL_TOKEN = "7RH2'Y54.M2zt,cC";

    public static final String UPLOAD_URL = BASE_URL + "Upload/Upload";

    final public static String REGISTER_URL = BASE_URL + "Register/Add";
    final public static String LOGIN_URL = BASE_URL + "Register/Login";
    final public static String SOCIAL_LOGIN_URL = BASE_URL + "Register/RegisterAndLogin";
    final public static String HOME_URL = BASE_URL + "Offer/ListOffer/";
    final public static String LIST_USERS_JOIN_REQUESTS = BASE_URL + "Offer/Income/";
    final public static String SelectOffer_URL = BASE_URL + "Offer/SelectOffer/";
    final public static String PROFILE_URL = BASE_URL + "Register/Profile";
    final public static String EDIT_PROFILE_URL = BASE_URL + "Register/ProfileEdit";
    final public static String ADD_OFFER_URL = BASE_URL + "Offer/Add/";
    final public static String EDIT_OFFER_URL = BASE_URL + "Offer/Edit";
    final public static String ListJoinedOffer_URL = BASE_URL + "Offer/ListJoinedOffer";
    final public static String ListSuccessOffer_URL = BASE_URL + "Offer/ListSuccessOffer";
    final public static String DELETE_OFFER_URL = BASE_URL + "Offer/Delete";
    final public static String JOIN_OFFER_URL = BASE_URL + "Offer/JoinOffer";
    final public static String OFFER_DETAILS_URL = BASE_URL + "Offer/GetOfferwithAllData/"; //VIEW_PROJECT_URL
    final public static String NOTIFICATION_URL = BASE_URL + "Offer/Notification";
    final public static String GENERAL_SEARCH_URL = BASE_URL + "Offer/BasicSearch";
    final public static String FILTER_SEARCH_URL = BASE_URL + "Offer/Filter";
    final public static String LOAD_DEPARTMENTS_URL = BASE_URL + "Offer/ListCat/";
    final public static String LOAD_ListOfCapTagCat_URL = BASE_URL + "Offer/ListOfCapTagCat/";
    final public static String LOAD_FAVOURITE_URL = BASE_URL + "Offer/ListbyIds";
    final public static String FAVOURITE_URL = BASE_URL + "Offer/Favorite";
    final public static String LIKE_URL = BASE_URL + "Offer/Like";
    final public static String ForgetPassword_URL = BASE_URL + "Register/ForgetPassword";
    final public static String CheckCode_URL = BASE_URL + "Register/CheakCodePassword";
    final public static String SavePasswordLogin_URL = BASE_URL + "Register/SavePasswordLogin";
    final public static String ACCEPT_JOIN_OFFER_URL = BASE_URL + "Offer/Accept";
    final public static String REFUSE_JOIN_OFFER_URL = BASE_URL + "Offer/Refuse";
    final public static String DeleteRequirement_URL = BASE_URL + "Offer/DeleteRequirement";
    final public static String REPORT_URL = BASE_URL + "Offer/Report";
    public final static String ACCOUNT_SETTINGS_URL = BASE_URL + "Register/AccountSetting";
    public final static String RESET_MAIL_URL = BASE_URL + "Register/MailReset";
    public final static String RESET_MAIL_CHECK_CODE_MAIL = BASE_URL + "Register/CheakCodeMail";
    public final static String OWNER_PANEL_URL = BASE_URL + "Offer/OwnerPanel";
    public final static String LIST_REASONS_URL = BASE_URL + "Offer/ListResons";
    public final static String LIST_JOINED_OFFERS = BASE_URL + "Offer/JoinOfferbyUserId";

    public final static String PARAM_NAME_TOKEN = "token";
    public static final String PARAM_NAME_OFFER = "Offer";
    public static final String PARAM_NAME_ATTACHMENT = "Attachment";
    public static final String PARAM_NAME_DELETED_ATTACHMENT = "AttachmentDeleted";
    public static final String PARAM_NAME_CAPITAL = "State";
    public static final String PARAM_NAME_TAGS = "Tags";
    public static final String PARAM_NAME_OFFER_ID = "OfferId";
    public static final String PARAM_NAME_ID = "Id";
    public static final String PARAM_NAME_USER_ID = "UserId";
    public static final String PARAM_NAME_LOCATION = "Location";
    public static final String PARAM_NAME_CURRENT_PASSWORD = "CurrentPassword";
    public static final String PARAM_NAME_USER = "User";
    public static final String PARAM_NAME_MAIL = "Mail";
    public static final String PARAM_NAME_CODE = "Code";
    public static final String PARAM_NAME_STATUS = "Status";
    public static final String PARAM_NAME_REFUSE_ID = "RefuseId";
    public static final String PARAM_NAME_OTHER_REASON = "OtherReson";
    public static final String PARAM_NAME_DEVICE_TOKEN = "devicetoken";

    public class Constants {
        public static final int STATUS_ON_HOLD = 0;
        public static final int STATUS_ACCEPT = 1;
        public static final int STATUS_REFUSE = 2;
        public static final int STATUS_BLOCK = 3;
    }

    public class NotificationType {
        public static final int TYPE_JOIN = 1;
        public static final int TYPE_EDIT = 2;
        public static final int TYPE_LIKE = 3;
        public static final int TYPE_DELETE = 4;
        public static final int TYPE_ACCEPT = 5;
        public static final int TYPE_REFUSE = 6;
    }

    public class NotificationDataKey {
        public static final String TARGET_USER_ID_KEY = "TargetUserId";
        public static final String NOTIFICATION_TYPE_KEY = "NotificationType";
        public static final String USER_NAME_KEY = "UserName";
        public static final String PROJECT_NAME_KEY = "ProjectName";
    }
}
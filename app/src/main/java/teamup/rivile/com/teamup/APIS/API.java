package teamup.rivile.com.teamup.APIS;

import java.util.HashMap;
import java.util.Map;

public class API {
    final public static String BASE_URL = "http://www.teamupapi.rivile.com/Offer/";
    final public static String URL_TOKEN = "7RH2'Y54.M2zt,cC";

    final public static String REGISTER_URL = BASE_URL + "";
    final public static String LOGIN_URL = BASE_URL + "";
    final public static String HOME_URL = BASE_URL + "ListOffer";
    final public static String GENERAL_MAP_URL = BASE_URL + "";
    final public static String PROFILE_URL = BASE_URL + "";
    final public static String ADD_OFFER_URL = BASE_URL + "Add";
    final public static String EDIT_OFFER_URL = BASE_URL + "";
    final public static String DELETE_OFFER_URL = BASE_URL + "";
    final public static String JOIN_OFFER_URL = BASE_URL + "JoinOffer";
    final public static String LIST_OFFERS_BY_PROJECT_URL = BASE_URL + "";
    final public static String LIST_OFFERS_BY_DEPARTMENT_URL = BASE_URL + "";
    final public static String GENERAL_SEARCH_URL = BASE_URL + "";
    final public static String FILTTER_SEARCH_URL = BASE_URL + "";
    final public static String LOAD_DEPARTMENTS_URL = BASE_URL + "ListCat";
    final public static String LOAD_ListOfCapTagCat_URL = BASE_URL + "ListOfCapTagCat";
    final public static String LOAD_TAGS_URL = BASE_URL + "";
    final public static String LIKE_URL = BASE_URL + "";
    final public static String VIEW_PROJECT_URL = BASE_URL + "";
    final public static String RATE_PROJECT_URL = BASE_URL + "";
    final public static String ACCEPT_JOIN_OFFER_URL = BASE_URL + "";
    final public static String REFUSE_JOIN_OFFER_URL = BASE_URL + "";
    final public static String REPORT_URL = BASE_URL + "";

    public final static String PARAM_NAME_TOKEN = "token";

    public static Map getLoadDepartmentsUrlParams(){
        Map<String, String> params = new HashMap<>();
        params.put("token", URL_TOKEN);

        return params;
    }
}
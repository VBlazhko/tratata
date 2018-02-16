package com.aaa.politindex;

import com.aaa.politindex.connection.RequestLog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11 on 21.01.2018.
 */

public class Const {
    public static final String SERVER = " http://politindex.nowords.ru/";
    public static final String SERVER_LOG = "http://31.41.221.156:8080/log/";
    public static final String VKSERVER = "https://oauth.vk.com/authorize?client_id=6219905&display=page&redirect_uri=http://politindex.nowords.ru/v1/vk/auth.api&response_type=code&v=5.68";
    public static final String FBSERVER = "https://www.facebook.com/v2.10/dialog/oauth?client_id=122584085092280&redirect_uri=http://politindex.nowords.ru/v1/fb/auth.api";


    public static final String FACEBOOK= "FACEBOOK";
    public static final String VKONTAKTE = "VKONTAKTE";

    public static final String TOKEN = "token";
    public static final String ID_USER = "id_user";
    public static final String ID_TOKEN = "id_token";

    public static final String LOCALE = "locale";
    public static final String COUNTRY = "country";

    public static final String FIRST = "first";


}

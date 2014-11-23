package com.nuoshi.console.domain.photo;

import java.sql.Timestamp;

/**
 * Author:
 * CHEN Liang <alinous@gmail.com>
 * Date: 2010-1-8
 * Time: 15:32:40
 */
abstract public class BasePhoto {
    public static final int EDMPHOTO_MAX_HEIGHT    = 675;
    public static final int EDMPHOTO_MAX_WIDTH    = 900;

    public static final int L_MAX_HEIGHT    = 480;
    public static final int L_MAX_WIDTH     = 640;

    public static final int M_MAX_HEIGHT    = 315;
//    public static final int M_MAX_HEIGHT = 420;
    public static final int M_MAX_WIDTH     = 420;

    public static final int S_MAX_HEIGHT    = 75;
    public static final int S_MAX_WIDTH     = 100;

    public static final int AVATAR_S_HEIGHT = 100;
    public static final int AVATAR_S_WIDTH  = 75;

    public static final int AVATAR_M_HEIGHT = 160;
    public static final int AVATAR_M_WIDTH  = 120;

//    public static final int AVATAR_L_HEIGHT = 640;
//    public static final int AVATAR_L_WIDTH  = 480;

    public static final int AVATAR_L_HEIGHT = 600;
    public static final int AVATAR_L_WIDTH  = 450;

//     //表明该photo无用
    public static final byte FLAGS_TEMP     = -1;
    public static final byte FLAGS_NONE     = 0;
    public static final byte FLAGS_HEAD     = 1;
    public static final byte FLAGS_HOUSE_PHOTO = 2;
    public static final byte FLAGS_COMMUNITY_ALBUM = 3;
    public static final byte FLAGS_LAYOUT_ALBUM = 4;
    
    public static final byte FLAGS_IDCARD_PHOTO = 0;
    public static final byte FLAGS_NAMECARD_PHOTO = 1;

    abstract public String getS();
    abstract public String getM();
    abstract public String getL();
    abstract public int getId();
    abstract public Timestamp getCts();
    abstract public int getOwner();

}

package com.jokey.bigo.social.qq.api;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

/**
 * @author Zhengjingfeng
 * @created 2019/5/20 14:58
 * @comment QQ用户信息表
 */
@Data
@Builder
public class QQUserInfo {

    private int ret;

    private String msg;

    private String nickname;

    private String figureurl;

    @SerializedName("figureurl_1")
    private String figureurl1;

    @SerializedName("figureurl_2")
    private String figureurl2;

    @SerializedName("figureurl_qq_1")
    private String figureurlQq1;

    @SerializedName("figureurl_qq_2")
    private String figureurlQq2;

    private String gender;

    private String openId;
}

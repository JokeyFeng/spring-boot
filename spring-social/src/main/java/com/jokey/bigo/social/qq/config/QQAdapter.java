package com.jokey.bigo.social.qq.config;

import com.jokey.bigo.social.qq.api.QQ;
import com.jokey.bigo.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author Zhengjingfeng
 * @created 2019/5/20 15:29
 * @comment
 */
public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();

        values.setProviderUserId(userInfo.getOpenId());
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurlQq1());
        values.setProfileUrl(null);
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

    }
}

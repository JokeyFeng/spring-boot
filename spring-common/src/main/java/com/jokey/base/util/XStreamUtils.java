/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019/1/2</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.base.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;

/**
 * @Project spring-boot
 * @Package com.jokey.base.util
 * @ClassName XStreamUtils
 * @Author JokeyZheng
 * @Date 2019/1/2
 * @Version 1.0
 */
public abstract class XStreamUtils {
    private static String XML_TAG = "<?xml version='1.0' encoding='UTF-8'?>";
    /** 模板属性参数*/
    private static String PROFILE_ID = "profileId";
    private static String LOGO_ID = "logoId";
    private static String CREATE_TIME = "createTime";
    private static String MODIFY_TIME = "modifyTime";
    /** 任务属性参数*/
    private static String TASK_ID = "taskId";
    private static String TASK_DEFAULT_INFO = "taskDefaultInfo";
    private static String INPUT_ID = "inputId";
    private static String VIDEO_ID = "videoId";
    private static String AUDIO_ID = "audioId";
    private static String ENCODE_ID = "encodeId";
    private static String PARAM_ID = "paramId";
    private static String OUTPUT_ID = "outputId";
    private static String PRETREAT_ID = "pretreatId";
    private static String SHIELD_ID = "shieldId";

    /**
     * Description: 私有化构造
     */
    private XStreamUtils() {
        super();
    }

    /**
     * @return
     * @Description 为每次调用生成一个XStream
     * @Title getInstance
     */
    private static XStream getInstance() {
        XStream xStream = new XStream(new DomDriver("UTF-8")) {
            /**
             * 忽略xml中多余字段
             */
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @SuppressWarnings("rawtypes")
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };

        // 设置默认的安全校验
        XStream.setupDefaultSecurity(xStream);
        // 使用本地的类加载器
        xStream.setClassLoader(XStreamUtils.class.getClassLoader());
        // 允许所有的类进行转换
        xStream.addPermission(AnyTypePermission.ANY);
        return xStream;
    }

    /**
     * @param xml
     * @param clazz
     * @return
     * @Description 将xml字符串转化为java对象
     * @Title xmlToBean
     */
    public static <T> T xmlToBean(String xml, Class<T> clazz) {
        XStream xStream = getInstance();
        xStream.processAnnotations(clazz);
        Object object = xStream.fromXML(xml);
        return clazz.cast(object);
    }

    /**
     * @param file
     * @param clazz
     * @return
     * @Description 将xml字符串转化为java对象
     * @Title xmlToBean
     */
    public static Object xmlToBean(File file, Class clazz) {
        XStream xStream = getInstance();
        xStream.processAnnotations(clazz);
        // 忽略未知字段
        xStream.ignoreUnknownElements();

        return xStream.fromXML(file);
    }

    /**
     * @param object
     * @return
     * @Description 将java对象转化为xml字符串
     * @Title beanToXml
     */
    public static String beanToXml(Object object, Class clazz) {
        XStream xStream = getInstance();
        // 忽略对象参数
//        overLookParam(xStream);
        xStream.processAnnotations(clazz);
        // 剔除所有tab、制表符、换行符
//        String xml = xStream.toXML(object).replaceAll("\\s+", " ")
        return xStream.toXML(object);
    }

    /**
     * @param object
     * @return
     * @Description 将java对象转化为xml字符串（包含xml头部信息）
     * @Title beanToXml
     */
    public static String beanToXmlWithTag(Object object, Class clazz) {
        return XML_TAG + "\n" + beanToXml(object, clazz);
    }

    /**
     * 被忽略的字段
     *
     * @param xStream
     */
    private static void overLookParam(XStream xStream) {
       /* // 模板参数
        xStream.omitField(ProfileVO.class, PROFILE_ID);
        xStream.omitField(ProfileLogo.class, LOGO_ID);
        xStream.omitField(ProfileLogo.class, PROFILE_ID);
        xStream.omitField(ProfileLogo.class, CREATE_TIME);
        xStream.omitField(ProfileLogo.class, MODIFY_TIME);

        // 任务参数
        xStream.omitField(TaskVO.class, TASK_ID);
        xStream.omitField(TaskVO.class, TASK_DEFAULT_INFO);

        xStream.omitField(TaskInputVO.class, INPUT_ID);
        xStream.omitField(TaskInputVO.class, TASK_ID);

        xStream.omitField(TaskInputVideo.class, VIDEO_ID);
        xStream.omitField(TaskInputVideo.class, INPUT_ID);
        xStream.omitField(TaskInputAudio.class, AUDIO_ID);
        xStream.omitField(TaskInputAudio.class, INPUT_ID);

        xStream.omitField(TaskEncodeVO.class, ENCODE_ID);
        xStream.omitField(TaskEncodeVO.class, TASK_ID);

        xStream.omitField(TaskEncodeParam.class, PARAM_ID);
        xStream.omitField(TaskEncodeParam.class, ENCODE_ID);
        xStream.omitField(TaskEncodeParam.class, CREATE_TIME);
        xStream.omitField(TaskEncodeParam.class, MODIFY_TIME);

        xStream.omitField(TaskEncodeLogo.class, LOGO_ID);
        xStream.omitField(TaskEncodeLogo.class, ENCODE_ID);
        xStream.omitField(TaskEncodeLogo.class, CREATE_TIME);
        xStream.omitField(TaskEncodeLogo.class, MODIFY_TIME);

        xStream.omitField(TaskOutput.class, OUTPUT_ID);
        xStream.omitField(TaskOutput.class, ENCODE_ID);
        xStream.omitField(TaskOutput.class, CREATE_TIME);
        xStream.omitField(TaskOutput.class, MODIFY_TIME);

        xStream.omitField(TaskPretreat.class, PRETREAT_ID);
        xStream.omitField(TaskPretreat.class, TASK_ID);

        xStream.omitField(TaskShield.class, SHIELD_ID);
        xStream.omitField(TaskShield.class, TASK_ID);
        */

    }
}

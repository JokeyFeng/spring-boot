package com.jokey.holder;

/**
 * @author Zhengjingfeng
 * @created 2020/7/7 18:14
 * @comment 自定义数据结构
 */
public class ProxyBeanHolder {

    private volatile String className;

    private volatile String methodName;

    private volatile String annotationName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }
}

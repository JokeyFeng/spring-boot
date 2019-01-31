/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019/1/31</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.bingo.ldap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.pool.factory.PoolingContextSource;
import org.springframework.ldap.pool.validation.DefaultDirContextValidator;
import org.springframework.ldap.transaction.compensating.manager.TransactionAwareContextSourceProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * LDAP 的自动配置类
 * 完成连接 及LdapTemplate生成
 *
 * @Project spring-boot
 * @Package com.jokey.bingo.ldap.config
 * @ClassName LdapConfiguration
 * @Author JokeyZheng
 * @Date 2019/1/31
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "ldap")
@PropertySource("classpath:/application.yml")
@Configuration
public class LdapConfiguration {

    private LdapTemplate ldapTemplate;

    private int maxActive;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int maxWait;

    @Bean
    public LdapContextSource contextSource() {
        Map<String, Object> config = new HashMap<>();
        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl(LdapConstant.LDAP_URL);
        contextSource.setBase(LdapConstant.BASE_DC);
        contextSource.setUserDn(LdapConstant.USER_NAME);
        contextSource.setPassword(LdapConstant.PASS_WORD);

        //解决乱码的关键一句
        config.put("java.naming.ldap.attributes.binary", "objectGUID");

        contextSource.setPooled(true);
        contextSource.setBaseEnvironmentProperties(config);
        return contextSource;
    }

    /**
     * LDAP pool 配置
     *
     * @return
     */
    @Bean
    public ContextSource poolingLdapContextSource() {
        PoolingContextSource poolingContextSource = new PoolingContextSource();
        poolingContextSource.setDirContextValidator(new DefaultDirContextValidator());
        poolingContextSource.setContextSource(contextSource());
        //在从对象池获取对象时是否检测对象有效
        poolingContextSource.setTestOnBorrow(true);
        //在检测空闲对象线程检测到对象不需要移除时，是否检测对象的有效性
        poolingContextSource.setTestWhileIdle(true);

        poolingContextSource.setMaxActive(maxActive <= 0 ? 20 : maxActive);
        poolingContextSource.setMaxTotal(maxTotal <= 0 ? 40 : maxTotal);
        poolingContextSource.setMaxIdle(maxIdle <= 0 ? 5 : maxIdle);
        poolingContextSource.setMinIdle(minIdle <= 0 ? 5 : minIdle);
        poolingContextSource.setMaxWait(maxWait <= 0 ? 5 : maxWait);

        return new TransactionAwareContextSourceProxy(poolingContextSource);
    }

    @Bean
    public LdapTemplate getLdapTemplate() {
        return this.ldapTemplate != null ? this.ldapTemplate : new LdapTemplate(contextSource());
    }
}

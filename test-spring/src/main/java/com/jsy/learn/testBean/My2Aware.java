package com.jsy.learn.testBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringValueResolver;

/**
 * 为bean对象属性设置容器中对象
 * BeanFactory 中注释有bean生命周期调用过程
 * <p>
 * ServletContextAware 暂时未实现
 */
public class My2Aware implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware
        , EnvironmentAware, EmbeddedValueResolverAware, ResourceLoaderAware
        , ApplicationEventPublisherAware, MessageSourceAware, ApplicationContextAware {

    public String beanName;
    public ClassLoader classLoader;
    public BeanFactory beanFactory;
    public Environment environment;
    public StringValueResolver stringValueResolver;
    public ResourceLoader resourceLoader;
    public ApplicationEventPublisher applicationEventPublisher;
    public MessageSource messageSource;
    public ApplicationContext applicationContext;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println("BeanNameAware setBeanName : " + beanName);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        System.out.println("BeanClassLoaderAware setBeanClassLoader : " + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("BeanFactoryAware setBeanFactory : " + beanFactory);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        System.out.println("EnvironmentAware setEnvironment : " + beanFactory);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
        System.out.println("EmbeddedValueResolverAware setEmbeddedValueResolver : " + stringValueResolver);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        System.out.println("ResourceLoaderAware setResourceLoader : " + resourceLoader);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        System.out.println("ApplicationEventPublisherAware setApplicationEventPublisher : " + applicationEventPublisher);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        System.out.println("MessageSourceAware setMessageSource : " + messageSource);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("ApplicationContextAware setApplicationContext : " + applicationContext);
    }

}

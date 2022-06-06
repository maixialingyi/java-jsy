package com.jsy.learn.testBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * BeanDefinition 动态扩展   如: 占位符配置器支持  PlaceholderConfigurerSupport  <property name = url value = ${jdbc.url}>
 */
public class My1BeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("----动态设置 BeanDefinition");
    }
}

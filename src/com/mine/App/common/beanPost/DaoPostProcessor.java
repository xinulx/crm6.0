package com.mine.App.common.beanPost;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.mine.App.common.Base.BaseDao;

/**
 * bean后处理器
 * 主要负责对初始化mapper时调用处理器对mapper类型进行强转的通用处理类
 * 需要在spring中配置
 *
 * @author db2admin
 * @desc 该处理器给com.mine.App.common.Base.BaseDao使用
 * @desc com.mine.App.common.Base.IbaseDao使用了注解，因此不再需要后处理器进行初始化
 * @warn 另外此处虽然不需要该处理器，但我们依然可以在spring中配置，以监控各个业务bean,学习和开发联系中方便理解和定位BUG
 */
public class DaoPostProcessor implements BeanPostProcessor {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        //只处理BaseDao的子类的对象
        if (bean.getClass().getSuperclass() == BaseDao.class) {
            BaseDao dao = (BaseDao) bean;
            logger.info("初始化" + bean.getClass().getName());
            dao.init();
        }
        //返回原bean实例
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        //直接返回原bean实例，不做任何处理
        return bean;
    }
}
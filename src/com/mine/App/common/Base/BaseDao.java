package com.mine.App.common.Base;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 需要Spring给SqlSessionDaoSupport类的子类的对象（多个DAO对象）注入一个SqlSessionFactory或一个SqlSessionTemplate
 * SqlSessionTemplate是线程安全的，因此，给多个DAO对象注入同一个SqlSessionTemplate是没有问题的
 * 
 * 自mybatis-spring-1.2.0以来，SqlSessionDaoSupport的setSqlSessionTemplate和setSqlSessionFactory两个方法上的
 * @Autowired注解被删除，这就意味着继承于SqlSessionDaoSupport的DAO类它们的对象不能被自动注入SqlSessionFactory
 * 或SqlSessionTemplate对象。如果在Spring的配置文件中一个一个地配置的话，显然太麻烦。
 * 
 * 
 * 比较好的解决办法是在我们的DAO类中覆盖这两个方法之一，并加上@Autowired注解。
 * 那么如果在每个DAO类中都这么做的话，显然很低效。更优雅的做法是，写一个继承于SqlSessionDaoSupport的BaseDao，在BaseDao中
 * 完成这个工作，然后其他的DAO类再都从BaseDao继承
 * @author db2admin
 *
 */
public abstract class BaseDao extends SqlSessionDaoSupport{

	/**
	 * 使用注解注入SqlSessionTemplate对象供每个dao调用
	 * 此处采用注解方式spring会自动注入该对象
	 * 
	 * 也可以采用spring配置化方式，@see SqlSessionTemplate @see SqlSessionFactory
	 */
	@Autowired
	 public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate){
	     super.setSqlSessionTemplate(sqlSessionTemplate);
	 }
	 //抽象方法
	 public abstract void init(); 
}
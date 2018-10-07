package com.mine.App.common.Base;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.mine.App.common.pagenation.Pagination;

public abstract class IbaseDao<T> extends SqlSessionDaoSupport {
	private Logger logger=Logger.getLogger(this.getClass().getName());
	//保护类型，子类可直接访问
	protected T mapper;

	/**
	 * @author wangshibao
	 * sqlSessionTemplate模板
	 */
	@SuppressWarnings("unused")
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	/**
	 * 在init方法被通过子类（如StudentDao）的对象被调用时，this
	 * 指代的是子类的对象，this.getClass()返回代表子类的Class对象，
	 * 再接着调用getGenericSuperclass()方法，可以返回代表子类的直接
	 * 超类（也就是BaseDao类）的Type对象。因为它是泛型，因此可强制类型
	 * 转换为ParameterizedType类型，再调用getActualTypeArguments()方法，
	 * 可获得子类给泛型指定的实际类型的数组。因为这里只有一个泛型
	 * 参数，所以取数组的第0个元素，即为子类的映射器类型。变量mapperType
	 * 代表子类的映射器类型。
	 */
	@PostConstruct
	public void init(){
		@SuppressWarnings("unchecked")
		Class<T> mapperType = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		logger.info("[INFO] -> 初始化" + mapperType.getName());
		//初始化映射器属性
		this.mapper = this.getSqlSession().getMapper(mapperType);
	}

	/**
	 * @Title: getMyBatisMapperNamespace
	 * @Description: 获取Mapper命名空间
	 */
	public String getMyBatisMapperNamespace() {
		if(this.mapper.getClass().equals(Object.class)){
			return "";
		}else{
			return this.mapper.getClass().getName();
		}
	}

	/**
	 * 使用sqlSession直接调用分页查询
	 * @param satement
	 * @param pagination
	 * @param param
	 * @return
	 */
	public List queryForPagenationList(String satement,Pagination pagination,Map param){
		param.put("startNum", (pagination.getCurrentPage()-1)*pagination.getPageSize());
		param.put("pageSize", pagination.getPageSize());
		return this.getSqlSession().selectList(satement,param);
	}
}

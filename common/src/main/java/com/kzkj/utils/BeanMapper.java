package com.kzkj.utils;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOption;
import org.dozer.loader.api.TypeMappingOptions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 简单封装Dozer, 实现深度转换Bean<->Bean的Mapper.实现:
 *
 * 1. 持有Mapper的单例. 2. 返回值类型转换. 3. 批量转换Collection中的所有对象. 4.
 * 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 *
 */
public class BeanMapper
{

	static Mapper mapper = new DozerBeanMapper();
	/**
	 * 将list转换为vo的list
	 * @param source
	 * @param destType
	 * @return
	 */
	public static <T, U> List<U> mapList(final List<T> source, final Class<U> destType) {
		final List<U> dest = new ArrayList<U>();
		for (T element : source) {
			dest.add(mapper.map(element, destType));
		}
		return dest;
	}

	//将Collection<E>
	public static <T, U> Collection<U> mapCollection(final Collection<T> source, final Class<U> destType) {
		final Collection<U> dest = new ArrayList<>();
		for (T element : source) {
			dest.add(mapper.map(element, destType));
		}
		return dest;
	}
	/**
	 * 转换单个vo对象,自动生成目标实例化对象
	 * @param source
	 * @param destType
	 * @return
	 */
	public static <U> U map(final Object source,final Class<U> destType) {
		return mapper.map(source, destType);
	}

	/**
	 * 将source的所有属性拷贝至target,source里没有的字段,target里不覆盖
	 * @param source
	 * @param target
	 * @return
	 */
	public static <U> void map(final Object source, final U target) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(new BeanMappingBuilder() {
			@Override
			protected void configure() {
				mapping(source.getClass(), target.getClass(),
						new TypeMappingOption[] {TypeMappingOptions.mapNull(false) });
			}

		});
		mapper.map(source, target);
	}


	/**
	 * @Title: map2
	 * @Description: (因为在使用map方法时遇到有字段没有复制成功，所以重写了新方法，经试用没问题。)该方法是用于相同对象不同属性值的合并，如果两个相同对象中同一属性都有值，
	 *               那么sourceBean中的值会覆盖tagetBean重点的值
	 * @param sourceBean     *            被提取的对象bean
	 * @param targetBean     *            用于合并的对象bean
	 * @return targetBean 合并后的对象
	 * @return: Object
	 */
	@SuppressWarnings("unused")
	public static Object map2(Object sourceBean, Object targetBean) {
		Class sourceBeanClass = sourceBean.getClass();
		Class targetBeanClass = targetBean.getClass();

		Field[] sourceFields = sourceBeanClass.getDeclaredFields();
		Field[] targetFields = sourceBeanClass.getDeclaredFields();
		for (int i = 0; i < sourceFields.length; i++) {
			Field sourceField = sourceFields[i];
			Field targetField = targetFields[i];
			sourceField.setAccessible(true);
			targetField.setAccessible(true);
			try {
				if (!(sourceField.get(sourceBean) == null)) {
					targetField.set(targetBean, sourceField.get(sourceBean));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return  targetBean;
	}
}

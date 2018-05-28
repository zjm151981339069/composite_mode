package com.zjm.springBoot.spring_boot_mode;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	@Resource
	private RedisTemplate<Serializable, Serializable> template;
	@Resource
	private ValueOperations<Serializable, Serializable> valueOperations;
	@Resource
	private ListOperations<Serializable, Serializable> listOperations;
	@Resource
	private HashOperations<Serializable, Object, Object> hashOperations;
	@Resource
	private SetOperations<Serializable, Serializable> setOperations;
	@Resource
	private ZSetOperations<Serializable, Serializable> zSetOperations;

	@Test
	public void contextLoads() {
		// String读写
		template.delete("name");
		valueOperations.set("name", "拯救世界的Programmer");
		System.out.println(valueOperations.get("name"));
		System.out.println("---------------");

		// List读写
		template.delete("myList");
		listOperations.rightPush("myList", "右1");
		listOperations.rightPush("myList", "右2");
		listOperations.leftPush("myList", "左");
		List<Serializable> listCache = listOperations.range("myList", 0, -1);
		for (Serializable s : listCache) {
			System.out.println((String) s);
		}
		System.out.println("---------------");

		// Set读写
		template.delete("mySet");
		setOperations.add("mySet", "A");
		setOperations.add("mySet", "B");
		setOperations.add("mySet", "C");
		Set<Serializable> setCache = setOperations.members("mySet");
		for (Serializable s : setCache) {
			System.out.println((String) s);
		}
		System.out.println("---------------");

		// Set读写
		template.delete("myZset");
		zSetOperations.add("myZset", "A", 1);
		zSetOperations.add("myZset", "C", 3);
		zSetOperations.add("myZset", "B", 2);
		Set<Serializable> zsetCache = zSetOperations.range("myZset", 0, -1);
		for (Serializable s : zsetCache) {
			System.out.println((String) s);
		}
		System.out.println("---------------");

		// Hash读写
		template.delete("myHash");
		hashOperations.put("myHash", "DEPARTMENT", "部门");
		hashOperations.put("myHash", "COMPANY", "公司");
		hashOperations.put("myHash", "ADDRESS", "深圳");
		Map<Object, Object> hashCache = hashOperations.entries("myHash");
		for (Map.Entry<Object, Object> entry : hashCache.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}

		System.out.println("---------------");
	}
}

package com.mattdamon.memcached;

import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcachedManager {

	// 创建全局的唯一实例
	protected static MemCachedClient mcc = new MemCachedClient();

	protected static MemcachedManager memCached = new MemcachedManager();

	// 设置与缓存服务器的连接池
	static {

		// 服务器列表和其权重
		String[] servers = { "192.168.24.10:11211" };

		Integer[] weights = { 3 };

		// 获取socke连接池的实例对象
		SockIOPool pool = SockIOPool.getInstance();

		// 设置服务器信息
		pool.setServers(servers);

		pool.setWeights(weights);

		// 设置初始连接数、最小和最大连接数以及最大处理时间
		pool.setInitConn(5);

		pool.setMinConn(5);

		pool.setMaxConn(20);

		pool.setMaxIdle(0 * 60 * 60 * 6);

		// 设置主线程的睡眠时间
		pool.setMaintSleep(30);

		// 设置TCP的参数，连接超时等
		pool.setNagle(false);

		pool.setSocketTO(0);

		pool.setSocketConnectTO(0);

		// 初始化连接池
		pool.initialize();

		// 压缩设置，超过指定大小（单位为K）的数据都会被压缩
		// mcc.setCompressEnable(true);

		// mcc.setCompressThreshold(64 * 4);
	}

	/**
	 * 保护型构造方法，不允许实例化！
	 */
	protected MemcachedManager() {

	}

	/**
	 * 获取唯一实例.
	 * 
	 * @return
	 */
	public static MemcachedManager getInstance() {

		return memCached;

	}

	public boolean set(String key, Object value) {
		return mcc.set(key, value);
	}

	/**
	 * 添加一个指定的值到缓存中.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean add(String key, Object value) {
		return mcc.add(key, value);
	}

	public boolean add(String key, Object value, Date expiry) {
		return mcc.add(key, value, expiry);
	}

	public boolean replace(String key, Object value) {
		return mcc.replace(key, value);
	}

	public boolean replace(String key, Object value, Date expiry) {
		return mcc.replace(key, value, expiry);
	}

	/**
	 * 删除一个指定的缓存值
	 * 
	 * @param key
	 * @return
	 */
	public boolean delete(String key) {
		return mcc.delete(key);
	}

	/**
	 * 根据指定的关键字获取对象.
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return mcc.get(key);
	}

	public static void main(String[] args) {

		MemcachedManager cache = MemcachedManager.getInstance();

		long startDate = System.currentTimeMillis();

		for (int i = 0; i < 10; i++) {
			cache.set("test" + i, "中国");
		}

		long endDate = System.currentTimeMillis();

		long nowDate = (endDate - startDate) / 1000;

		System.out.println(nowDate);

		for (int i = 0; i < 10; i++) {
			System.out.println(" get value : " + cache.get("test" + i));
		}

	}

}

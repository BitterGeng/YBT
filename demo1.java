package com.demo.jedis;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class demo1 {
	
	
	public static void main(String[] args) {
		Jedis  jedis = new Jedis("127.0.0.1",6379);
		try {
			jedis.set("name", "我的中文s");
//			jedis.set("name", new String("我的中文".getBytes("utf-8")));
//			System.out.println(new String(jedis.get("name").getBytes("utf-8")));
			System.out.println(jedis.get("name"));
			
//			String hget = jedis.hget("myhash", "username");
//			System.out.println();
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	/**
	 * 使用直连接
	 */
	public void demo1(){
		
		Jedis  jedis = null;
		
		try {
			jedis = new Jedis("127.0.0.1",6379);
			jedis.set("girlFriend", "sunmin");
			Long incr = jedis.incr("girlFriend");
			System.out.println(incr);
			System.out.println(jedis.get("girlFriend"));
			
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		
	}
	
	
	/**
	 *连接池的方式进行存值
	 */
	@Test
	public void demo2(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(30);
		config.setMaxIdle(10);
		JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("age", "89");
			System.out.println(jedis.get("age"));
			
		} finally{
			if(jedis != null){
				jedis.close();
			}
			jedisPool.close();
		}
		
	}
	
	/**
	 *存取中文
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void demo3() throws UnsupportedEncodingException{
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(30);
		config.setMaxIdle(10);
		JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("name", new String("我的中文".getBytes("utf-8")));
			System.out.println(jedis.get("name"));
			
		} finally{
			if(jedis != null){
				jedis.close();
			}
			jedisPool.close();
		}
		
	}
	
	
	
	/**
	 *存取map
	 */
	@Test
	public void demo4() throws UnsupportedEncodingException{
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(30);
		config.setMaxIdle(10);
		JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Map<String,String> user = new HashMap<String,String>();
		    user.put("username", "cd");
	        user.put("password", "123456");
	        jedis.hmset("user", user);
	        System.out.println("长度是"+jedis.hlen("user"));
	        Set<String> hkeys = jedis.hkeys("user");
	        //获取所有key
	        Iterator<String> iterator = hkeys.iterator();
	        while(iterator.hasNext()){
	        	String next = iterator.next();
	        	System.out.println("key："+next);
	        }
	        //获取所有value
	        List<String> hvals = jedis.hvals("user");
	        for(String val:hvals){
	        	System.out.println("value:"+val);
	        }
	        
	       //获取user里面password和name属性的值
	       List<String> hmget = jedis.hmget("user", "password","name");
	       for(String us:hmget){
	    	   System.out.println(us);
	       }
	        
		} finally{
			if(jedis != null){
				jedis.close();
			}
			jedisPool.close();
		}
		
	}
	
	
	
	
	
	
}

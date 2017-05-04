package com.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.model.User;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

public class JedisAdapter implements InitializingBean{
     
	
   private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
	
   private 	JedisPool pool;
   public static void print(int index,Object obj){
       System.out.println(String.format("%d,%s",index,obj.toString())); 
   }		
	public static void main(String[] argv){
		
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis("redis://localhost:6379/9");
		jedis.flushAll();
		jedis.set("hello","world");
		print(1,jedis.get("hello"));
		jedis.rename("hello","newhello");
		print(1,jedis.get("newhello"));
		jedis.setex("helloe2",15,"world");
        jedis.set("pv", "100");
        jedis.incr("pv");
        print(2,jedis.get("pv"));
        jedis.incrBy("pv",5);
        print(2,jedis.get("pv"));
        
        
        //列表操作
        String listName = "listA";
        for(int i = 0;i<10; ++i){
        jedis.lpush(listName, "a" + String.valueOf(i));	
        }
        print(3,jedis.lrange(listName,0,12));
        print(4,jedis.llen(listName));
        print(5,jedis.lpop(listName));
        print(6,jedis.llen(listName));
        print(7,jedis.lindex(listName,3));
        print(8,jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "xx"));
        print(9,jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a4", "yy"));
        print(10,jedis.lrange(listName,0,12));
        
        String userKey = "userxx";
        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "12");
        jedis.hset(userKey, "phone", "11111");
        print(11,jedis.hget(userKey, "name"));
        print(12,jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(13,jedis.hgetAll(userKey));
        print(14,jedis.hvals(userKey));
        jedis.hsetnx(userKey,"school","zzz");
        print(16,jedis.hgetAll(userKey));
        
        //set
        String likeKey1 = "newsLike1";
        String likeKey2 = "newsLike2"; 
        for(int i = 0; i<10;++i){
        	jedis.sadd(likeKey1, String.valueOf(i));
        	jedis.sadd(likeKey2, String.valueOf(i*2));
        }
        print(16,jedis.smembers(likeKey1));
        print(17,jedis.smembers(likeKey2));
        print(18,jedis.sinter(likeKey1,likeKey2));
        print(19,jedis.sunion(likeKey1,likeKey2));
        print(20,jedis.sdiff(likeKey1,likeKey2));
        jedis.srem(likeKey1,"5");
        print(21,jedis.smembers(likeKey1));
        jedis.smove(likeKey2,likeKey1,"14");
        print(21,jedis.smembers(likeKey1));
        
        
        //
        String rankKey = "rankKey";
        jedis.zadd(rankKey,15,"jim");
        jedis.zadd(rankKey,60,"ben");
        jedis.zadd(rankKey,90,"lee");
        jedis.zadd(rankKey,80,"mei");
        jedis.zadd(rankKey,75,"lucy");
        
        print(30,jedis.zcard(rankKey));
        print(31,jedis.zcount(rankKey,60,100)); 
        print(32,jedis.zscore(rankKey,"lucy"));
        jedis.zincrby(rankKey,2,"lucy");
        print(33,jedis.zscore(rankKey,"lucy"));
        jedis.zincrby(rankKey,2,"luck");
        print(34,jedis.zscore(rankKey,"luck"));
        print(35,jedis.zcount(rankKey,0,100));
        print(36,jedis.zrange(rankKey,1,3));
        print(37,jedis.zrevrange(rankKey,1,3));
        
        for(Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "0", "100")){
        	print(37,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
        }
        print(38,jedis.zrank(rankKey,"ben"));
        print(38,jedis.zrevrank(rankKey,"ben"));
       /* 
        JedisPool pool = new JedisPool();
        for(int i = 0;i<100;i++){
        	Jedis j = pool.getResource();
        	j.get("a");
        	System.out.println("POOL"+i);
        	j.close();
        }
        */
        User user = new User();
        user.setName("xx");
        user.setPassword("888");
        user.setInformation("woo");
        print(39,JSONObject.toJSONString(user));
        jedis.set("user1", JSONObject.toJSONString(user));
       
        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value,User.class);
        print(47,user2);
        
	}
	@Override
	public void afterPropertiesSet() throws Exception {
	  pool = new JedisPool("redis://localhost:6379/10");	
	}
	
	public long sadd(String key,String value){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sadd(key, value);
		}catch(Exception e){
		logger.error("发生错误"+e.getMessage());	
		}
		finally{
			if(jedis != null){
			  jedis.close();	
			}
		  }
		return 0;
		}	
	}
	
	


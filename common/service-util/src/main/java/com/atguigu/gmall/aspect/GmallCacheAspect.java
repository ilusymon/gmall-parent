package com.atguigu.gmall.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import sun.nio.cs.SingleByte;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Symon
 * @version 1.0
 * @className GmallCacheAspect
 * @date 2021/1/28 16:53
 */
@Aspect
@Component
public class GmallCacheAspect {

    @Resource
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.atguigu.gmall.aspect.GmallCache)")
    public Object cacheAroundAdvice(ProceedingJoinPoint point) {
        Object proceed = null;

        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        String name = method.getName();
        Class<?> returnType = method.getReturnType();
        GmallCache annotation = method.getAnnotation(GmallCache.class);

        StringBuilder argKey = new StringBuilder();
        if (args != null && args.length > 0) {
            // 严谨些，需要判断arg是否是引用类型
            for (Object arg : args) {
                argKey.append(":").append(arg);
            }
        }
        String type = "str";
        if (name.contains("sku") || name.contains("Sku")) {
            String prefix = annotation.skuCache();
            type = annotation.skuType();
            name = prefix;
        }

        //获取缓存值
        String commonKey = "GmallCache:" + name + argKey;

        proceed = redisTemplate.opsForValue().get(commonKey);
        if (proceed == null) {
            String lockTag = UUID.randomUUID().toString();
            Boolean OK = redisTemplate.opsForValue().setIfAbsent(commonKey + ":lock", lockTag, 3, TimeUnit.SECONDS);
            if (OK) {
                //查db
                try {
                    proceed = point.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                // 同步缓存
                if (proceed != null) {
                    if(type.equals("str")){
                        redisTemplate.opsForValue().set(commonKey, proceed);
                    }else if(type.equals("list")){
                        // redisTemplate.opsForList();
                        redisTemplate.opsForValue().set(commonKey, proceed);
                    }else if (type.equals("hash")){
                        // redisTemplate.opsForHash();
                        redisTemplate.opsForValue().set(commonKey, proceed);
                    }
                } else {
                    redisTemplate.opsForValue().set(commonKey, null,1,TimeUnit.MINUTES);
                }
                // 释放锁
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";// 脚本查询是否存在，存在则删除，否则返回0
                // 设置lua脚本返回的数据类型
                DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
                redisScript.setResultType(Long.class);
                redisScript.setScriptText(script);
                redisTemplate.execute(redisScript, Arrays.asList(commonKey + ":lock"), lockTag);// 执行
            } else {
                //没有查询到缓存数据，并且没有获得分布式锁，自旋
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proceed = redisTemplate.opsForValue().get(commonKey);
                return proceed;
            }
        }


        return proceed;
    }
}

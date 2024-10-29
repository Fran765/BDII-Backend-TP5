package ar.unrn.tp.api;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface RedisOperation {
    void execute(Jedis jedis) throws Exception;
}
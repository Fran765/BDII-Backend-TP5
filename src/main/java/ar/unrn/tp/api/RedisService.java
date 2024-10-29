package ar.unrn.tp.api;

public interface RedisService {

    void executeInRedis(RedisOperation operation);
}

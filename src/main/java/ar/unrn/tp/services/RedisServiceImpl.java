package ar.unrn.tp.services;

import ar.unrn.tp.api.RedisOperation;
import ar.unrn.tp.api.RedisService;
import ar.unrn.tp.exceptions.RedisExeption;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

@Service
public class RedisServiceImpl implements RedisService {

    private final JedisPool jedisPool; // pool para poder manejar conexiones de forma concurrente

    public RedisServiceImpl() {
        this.jedisPool = new JedisPool("localhost", 6379);
    }
    @Override
    public void executeInRedis(RedisOperation operation) {

        try (Jedis jedis = jedisPool.getResource()) {

            operation.execute(jedis);

        } catch (JedisException je){
            throw new RedisExeption("Error al ejecutar operación en Redis: " + je.getMessage(), je);
        } catch (Exception e) {
            throw new RedisExeption("Error al ejecutar operación en Redis: " + e.getMessage(), e);
        }
    }
}

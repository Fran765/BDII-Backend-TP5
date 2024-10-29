package ar.unrn.tp.services;

import ar.unrn.tp.api.RedisOperation;
import ar.unrn.tp.api.RedisService;
import ar.unrn.tp.exceptions.ApplicationException;
import ar.unrn.tp.exceptions.RedisExeption;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisServiceImpl implements RedisService {

    private final JedisPool jedisPool; //Uso un pool para poder manejar conexiones de forma concurrente

    public RedisServiceImpl() {
        this.jedisPool = new JedisPool("localhost", 6379);
    }
    @Override
    public void executeInRedis(RedisOperation operation) {

        try (Jedis jedis = jedisPool.getResource()) {

            operation.execute(jedis);

        } catch (ApplicationException ae){
            throw new RedisExeption("Error al ejecutar operación en Redis./" + ae.getMessage(), ae);
        } catch (Exception e) {
            throw new RedisExeption("Error al ejecutar operación en Redis");
        }
    }
}

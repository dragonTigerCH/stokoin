package com.bsc.stokoin.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.embedded.RedisServer;

@Profile("!prod")
@Configuration
public class EmbeddedRedisConfig {


    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.host}")
    private String host;


    private RedisServer redisServer;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
        return lettuceConnectionFactory;
    }

//
//    @PostConstruct
//    public void redisServer() throws IOException {
//        int port = isRedisRunning() ? findAvailablePort() : this.port;
//        redisServer = new RedisServer(port);
//        redisServer.start();
//    }
//
//
//    private boolean isRedisRunning() throws IOException {
//        return isRunning(executeGrepProcessCommand(port));
//    }
//
//    private boolean isRunning(Process process) {
//        String line;
//        StringBuilder pidInfo = new StringBuilder();
//
//        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//
//            while ((line = input.readLine()) != null) {
//                pidInfo.append(line);
//            }
//        } catch (Exception e) {
//        }
//
//        return !ObjectUtils.isEmpty(pidInfo.toString());
//    }
//
//    public int findAvailablePort() throws IOException {
//
//        for (int port = 10000; port <= 65535; port++) {
//            Process process = executeGrepProcessCommand(port);
//            if (!isRunning(process)) {
//                return port;
//            }
//        }
//        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
//    }
//
//    private Process executeGrepProcessCommand(int port) throws IOException {
//        String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
//        String[] shell = {"/bin/sh", "-c", command};
//        return Runtime.getRuntime().exec(shell);
//    }
//
//
//    @PreDestroy
//    public void stopRedis() {
//        if (redisServer != null && redisServer.isActive()) {
//            redisServer.stop();
//        }
//    }

}

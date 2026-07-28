package com.glyneria.iwrapper.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final StringRedisTemplate redisTemplate;

    // acquire lock
    public boolean acquireLock(UUID idempotencyKey) {
        String redisKey = "idempotency: " + idempotencyKey.toString();

        // setIfAbsent returns
        // true = sets key and locks
        // false = key exists, lock fails
        Boolean success = redisTemplate.opsForValue().setIfAbsent(
            redisKey, 
            "{\"status\":\"IN_PROGRESS\"}", 
            Duration.ofSeconds(30)
        );

        return Boolean.TRUE.equals(success);
    }

    // inspect existing value if lock fails
    public String getExistingRecord(UUID idempotencyKey) {
        return redisTemplate.opsForValue().get("idempotency:" + idempotencyKey.toString());
    }

    // save final result
    public void saveResult(UUID idempotencyKey, String responseJsonPayload, boolean isSuccess) {
        String redisKey = "idempotency: " + idempotencyKey.toString();
        String status = isSuccess ? "SUCCESS" : "FAILED";

        String finalPayload = String.format(
            "{\"status\":\"%s\",\"response\":%s}",
            status,
            responseJsonPayload
        );

        // overwrite with a response and set a longer TTL
        redisTemplate.opsForValue().set(redisKey, finalPayload, Duration.ofHours(24));
    }
}

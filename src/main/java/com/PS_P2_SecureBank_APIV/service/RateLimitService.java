package com.PS_P2_SecureBank_APIV.service;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean permitirTransferencia(String username) {
        Bucket bucket = buckets.computeIfAbsent(username, this::crearBucket);
        return bucket.tryConsume(1);
    }

    private Bucket crearBucket(String username) {
        Bandwidth limite = Bandwidth.classic(
                5,
                Refill.greedy(5, Duration.ofMinutes(1))
        );

        return Bucket.builder()
                .addLimit(limite)
                .build();
    }
}
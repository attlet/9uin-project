package com.inProject.in.domain.Board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ViewCountRepository {
    private final RedisTemplate redisTemplate;

    public List<String> getBoardList(String user_id){
        Long len = redisTemplate.opsForList().size(user_id);
        return redisTemplate.opsForList().range(user_id, 0, len-1);
       // return len == 0 ? new ArrayList<>() : redisTemplate.opsForList().range(key, 0, len-1);
    }

    public void setBoard(String key, String data){
        redisTemplate.opsForList().rightPush(key, data);
        redisTemplate.expire(key, 60 * 60 * 24, TimeUnit.SECONDS);
    }
}

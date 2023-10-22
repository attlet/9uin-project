package com.inProject.in.domain.CommonLogic.Mail.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class MailRepository {
    private final RedisTemplate redisTemplate;
    private long timeout = 60 * 3;    //3분

    public void setValidCode(String mail, String code){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        if(!Objects.isNull(valueOperations.get(mail))){
            redisTemplate.delete(mail);
        }
        valueOperations.set(mail, code);
        redisTemplate.expire(mail, timeout, TimeUnit.SECONDS);
    }

    public Optional<String> getValidCode(String mail){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String code = valueOperations.get(mail);

        if(Objects.isNull(code)){
            return Optional.empty();
        }
        else{
            return Optional.of(code);
        }
    }
}

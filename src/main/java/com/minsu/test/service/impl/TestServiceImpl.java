package com.minsu.test.service.impl;

import com.minsu.test.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Author: Fallen
 * Date: 2019/8/6
 * Time: 0:26
 * Usage:
 */
@Service
public class TestServiceImpl implements TestService {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String test() {
        log.info("service: {}", LocalDateTime.now());
        return LocalDateTime.now().toString();
    }
}

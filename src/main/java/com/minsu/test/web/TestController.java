package com.minsu.test.web;

import com.minsu.structure.entity.ResponseDTO;
import com.minsu.test.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * Author: Fallen
 * Date: 2019/8/5
 * Time: 23:40
 * Usage:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private TestService testService;

    @RequestMapping("/ping")
    public ResponseDTO ping() {
        log.info("request: {}", LocalDateTime.now());
        return ResponseDTO.success(testService.test());
    }
}

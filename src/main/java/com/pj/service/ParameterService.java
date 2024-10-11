package com.pj.service;

import com.pj.dto.PurchaseRequestDTO;
import com.pj.dto.PurchaseResponseDTO;
import com.pj.model.Parameter;
import com.pj.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    // 缓存存储参数
    private final Map<String, Parameter> parameterCache = new ConcurrentHashMap<>();

    // 初始化加载参数
    @PostConstruct
    public void init() {
        loadParameters();
    }

    // 定时每30秒刷新一次缓存
    @Scheduled(fixedRate = 30000)
    public void refreshParameters() {
        loadParameters();
    }

    // 手动刷新缓存
    public void manualRefresh() {
        loadParameters();
    }

    // 加载参数到缓存中，考虑生效时间和启用状态
    private void loadParameters() {
        List<Parameter> allParams = parameterRepository.findAll();
        Map<String, Parameter> tempCache = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        for (Parameter param : allParams) {
            System.out.println("key是"+param.getKey()+"value是"+param.getValue());

            if (Boolean.TRUE.equals(param.getEnabled())) {
/*                if ((param.getStartTime() == null || !now.isBefore(param.getStartTime())) &&
                        (param.getEndTime() == null || !now.isAfter(param.getEndTime()))) {
                    tempCache.put(param.getKey(), param);
                }*/
                tempCache.put(param.getKey(), param);
            }
        }

        // 使用ConcurrentHashMap的putAll方法保证线程安全
        System.out.println(tempCache.get("siteName"));
        parameterCache.clear();
        parameterCache.putAll(tempCache);
        System.out.println(parameterCache.get("siteName"));
        System.out.println("Parameters cache refreshed at " + now);
    }

    // 查询参数
    public Optional<Parameter> getParameter(String key) {
        return Optional.ofNullable(parameterCache.get(key));
    }

    // 设置参数
    public Parameter setParameter(String key, String value, LocalDateTime startTime, LocalDateTime endTime, Boolean enabled) {
        Optional<Parameter> optionalParam = parameterRepository.findByKey(key);
        Parameter param;
        if (optionalParam.isPresent()) {
            param = optionalParam.get();
            param.setValue(value);
            param.setStartTime(startTime);
            param.setEndTime(endTime);
            param.setEnabled(enabled);
        } else {
            param = new Parameter(key, value, startTime, endTime, enabled);
        }
        Parameter savedParam = parameterRepository.save(param);
        loadParameters(); // 立即刷新缓存
        return savedParam;
    }
}
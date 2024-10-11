package com.pj.controller;

import com.pj.dto.ParameterDTO;
import com.pj.model.Parameter;
import com.pj.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/parameters")
public class ParameterController {

    @Autowired
    private ParameterService parameterService;

    // 参数查询接口
    @GetMapping("/{key}")
    public ResponseEntity<?> getParameter(@PathVariable String key) {
        Optional<Parameter> param = parameterService.getParameter(key);
        if (param.isPresent()) {
            return ResponseEntity.ok(param.get());
        } else {
            return ResponseEntity.status(404).body("Parameter not found");
        }
    }

    // 参数设置接口
    @PostMapping
    public ResponseEntity<?> setParameter(@RequestBody ParameterDTO parameterDTO) {
        if (parameterDTO.getKey() == null || parameterDTO.getValue() == null) {
            return ResponseEntity.badRequest().body("Key and Value are required");
        }
        Parameter param = parameterService.setParameter(
                parameterDTO.getKey(),
                parameterDTO.getValue(),
                parameterDTO.getStartTime(),
                parameterDTO.getEndTime(),
                parameterDTO.getEnabled()
        );
        return ResponseEntity.ok(param);
    }

    // 手动刷新缓存接口
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshParameters() {
        parameterService.manualRefresh();
        return ResponseEntity.ok("Parameters cache refreshed successfully");
    }
}
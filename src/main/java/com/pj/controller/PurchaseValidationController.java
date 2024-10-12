package com.pj.controller;

import com.pj.dto.PurchaseRequestDTO;
import com.pj.dto.PurchaseResponseDTO;
import com.pj.service.PurchaseValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 购买校验控制器
 */
@RestController
@RequestMapping("/api/purchase")
public class PurchaseValidationController {

    @Autowired
    private PurchaseValidationService purchaseValidationService;

    /**
     * 购买校验接口
     *
     * @param request 购买请求DTO
     * @return 购买响应DTO
     */
    @PostMapping("/validate")
    public ResponseEntity<PurchaseResponseDTO> validatePurchase(@RequestBody PurchaseRequestDTO request) {
        PurchaseResponseDTO response = purchaseValidationService.validatePurchase(request);
        if (response.isAllowed()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }
}
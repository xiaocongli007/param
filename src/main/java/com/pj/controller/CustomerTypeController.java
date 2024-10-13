package com.pj.controller;

import com.pj.dto.CustomerTypeDTO;
import com.pj.model.CustomerType;
import com.pj.repository.CustomerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerTypeController {

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    /**
     * 新增客户类型
     *
     * @param customerTypeDTO 客户类型 DTO
     * @return 新增的客户类型
     */
    @PostMapping("/add")
    public ResponseEntity<CustomerTypeDTO> addCustomerType(@RequestBody CustomerTypeDTO customerTypeDTO) {
        // 检查是否已存在相同的类型名
        if (customerTypeRepository.findByTypeName(customerTypeDTO.getTypeName()).isPresent()) {
            return ResponseEntity.badRequest().body(null); // 或返回自定义错误信息
        }

        CustomerType customerType = new CustomerType();
        customerType.setTypeName(customerTypeDTO.getTypeName());
        CustomerType savedCustomerType = customerTypeRepository.save(customerType);

        CustomerTypeDTO responseDTO = new CustomerTypeDTO(savedCustomerType.getId(), savedCustomerType.getTypeName());
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * 查询所有客户类型
     *
     * @return 客户类型列表
     */
    @PostMapping("/getAll")
    public ResponseEntity<List<CustomerTypeDTO>> getAllCustomerTypes() {
        List<CustomerType> customerTypes = customerTypeRepository.findAll();
        List<CustomerTypeDTO> responseDTOs = customerTypes.stream()
                .map(ct -> new CustomerTypeDTO(ct.getId(), ct.getTypeName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping("/getByid")
    public ResponseEntity<CustomerTypeDTO> getCustomerTypeById(@RequestBody CustomerTypeDTO customerTypeDTO) {
        Long id = customerTypeDTO.getId();
        CustomerType customerType = customerTypeRepository.findById(id).orElse(null);
        if (customerType != null) {
            CustomerTypeDTO responseDTO = new CustomerTypeDTO(customerType.getId(), customerType.getTypeName());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
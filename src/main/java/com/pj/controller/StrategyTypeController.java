package com.pj.controller;

import com.pj.dto.StrategyTypeDTO;
import com.pj.model.StrategyType;
import com.pj.repository.StrategyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/strategies")
public class StrategyTypeController {

    @Autowired
    private StrategyTypeRepository strategyTypeRepository;

    /**
     * 新增策略类型
     *
     * @param strategyTypeDTO 策略类型 DTO
     * @return 新增的策略类型
     */
    @PostMapping("/add")
    public ResponseEntity<StrategyTypeDTO> addStrategyType(@RequestBody StrategyTypeDTO strategyTypeDTO) {
        // 检查是否已存在相同的策略代码
        if (strategyTypeRepository.findByStrategyCode(strategyTypeDTO.getStrategyCode()).isPresent()) {
            return ResponseEntity.badRequest().body(null); // 或返回自定义错误信息
        }

        StrategyType strategyType = new StrategyType();
        strategyType.setStrategyCode(strategyTypeDTO.getStrategyCode());
        strategyType.setRegularHolidays(strategyTypeDTO.getRegularHolidays());
        strategyType.setSpecialHolidays(strategyTypeDTO.getSpecialHolidays());
        strategyType.setSpecialWorkdays(strategyTypeDTO.getSpecialWorkdays());
        StrategyType savedStrategyType = strategyTypeRepository.save(strategyType);

        StrategyTypeDTO responseDTO = new StrategyTypeDTO(
                savedStrategyType.getId(),
                savedStrategyType.getStrategyCode(),
                savedStrategyType.getRegularHolidays(),
                savedStrategyType.getSpecialHolidays(),
                savedStrategyType.getSpecialWorkdays()
        );
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * 查询所有策略类型
     *
     * @return 策略类型列表
     */
    @PostMapping("/getAll")
    public ResponseEntity<List<StrategyTypeDTO>> getAllStrategyTypes() {
        List<StrategyType> strategyTypes = strategyTypeRepository.findAll();
        List<StrategyTypeDTO> responseDTOs = strategyTypes.stream()
                .map(st -> new StrategyTypeDTO(
                        st.getId(),
                        st.getStrategyCode(),
                        st.getRegularHolidays(),
                        st.getSpecialHolidays(),
                        st.getSpecialWorkdays()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}
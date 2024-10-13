package com.pj.controller;

import com.pj.dto.StrategyTypeDTO;
import com.pj.model.StrategyType;
import com.pj.repository.StrategyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<?> addStrategyType(@RequestBody StrategyTypeDTO strategyTypeDTO) {
        // 检查是否已存在相同的策略代码
        if (strategyTypeRepository.findByStrategyCode(strategyTypeDTO.getStrategyCode()).isPresent()) {
            return ResponseEntity.badRequest().body("Strategy code already exists");
        }

        // 检查 startTime 和 endTime 的逻辑
        if (strategyTypeDTO.getStartTime() != null && strategyTypeDTO.getEndTime() != null) {
            if (strategyTypeDTO.getStartTime().isAfter(strategyTypeDTO.getEndTime())) {
                return ResponseEntity.badRequest().body("Start time cannot be after end time");
            }
        }

        StrategyType strategyType = new StrategyType();
        strategyType.setStrategyCode(strategyTypeDTO.getStrategyCode());
        strategyType.setRegularHolidays(strategyTypeDTO.getRegularHolidays());
        strategyType.setSpecialHolidays(strategyTypeDTO.getSpecialHolidays());
        strategyType.setSpecialWorkdays(strategyTypeDTO.getSpecialWorkdays());
        strategyType.setStartTime(strategyTypeDTO.getStartTime());
        strategyType.setEndTime(strategyTypeDTO.getEndTime());

        StrategyType savedStrategyType = strategyTypeRepository.save(strategyType);

        StrategyTypeDTO responseDTO = new StrategyTypeDTO(
                savedStrategyType.getId(),
                savedStrategyType.getStrategyCode(),
                savedStrategyType.getRegularHolidays(),
                savedStrategyType.getSpecialHolidays(),
                savedStrategyType.getSpecialWorkdays(),
                savedStrategyType.getStartTime(),
                savedStrategyType.getEndTime()
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
                        st.getSpecialWorkdays(),
                        st.getStartTime(),
                        st.getEndTime()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    // TODO: 2024/10/12 策略的修改

    /**
     * 修改 StrategyType 信息
     *
     * @param strategyTypeDTO 包含修改信息的 DTO 对象
     * @return 修改后的 StrategyType 信息
     */
    @PostMapping("/update")
    public ResponseEntity<String> updateStrategyType(@RequestBody StrategyTypeDTO strategyTypeDTO) {
        Optional<StrategyType> strategyTypeOpt = strategyTypeRepository.findById(strategyTypeDTO.getId());
        if (strategyTypeOpt.isPresent()) {
            StrategyType strategyType = strategyTypeOpt.get();
            strategyType.setStrategyCode(strategyTypeDTO.getStrategyCode());
            strategyType.setRegularHolidays(strategyTypeDTO.getRegularHolidays());
            strategyType.setSpecialHolidays(strategyTypeDTO.getSpecialHolidays());
            strategyType.setSpecialWorkdays(strategyTypeDTO.getSpecialWorkdays());
            strategyType.setStartTime(strategyTypeDTO.getStartTime());
            strategyType.setEndTime(strategyTypeDTO.getEndTime());
            strategyTypeRepository.save(strategyType);
            return ResponseEntity.ok("StrategyType updated successfully.");
        } else {
            return ResponseEntity.status(404).body("StrategyType not found.");
        }
    }

}
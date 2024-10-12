package com.pj.service;

import com.pj.enums.ResponseCode;
import com.pj.model.ResponseMessage;
import com.pj.repository.ResponseMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 服务类，用于获取响应消息
 */
@Service
public class ResponseMessageService {

    @Autowired
    private ResponseMessageRepository responseMessageRepository;

    /**
     * 根据提示码获取消息
     *
     * @param code 提示码枚举
     * @return 对应的消息，如果未找到则返回默认消息
     */
    public String getMessage(ResponseCode code) {
        Optional<ResponseMessage> optionalMessage = responseMessageRepository.findByCode(code.name());
        return optionalMessage.map(ResponseMessage::getMessage).orElse("未知错误");
    }
}
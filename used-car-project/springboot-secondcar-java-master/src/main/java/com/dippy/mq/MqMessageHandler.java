package com.dippy.mq;


import com.dippy.config.RabbitConfig;
import com.dippy.dto.LoginUser;
import com.dippy.es.service.SearchService;
import com.dippy.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;

/**
 * mq
 */
@Component
@RabbitListener(queues = RabbitConfig.ES_QUEUE)
@Slf4j
public class MqMessageHandler {

    @Autowired
    public SearchService searchService;

    @Autowired
    public SysUserService sysUserService;


    @RabbitHandler
    public void handler(MQIndexMessage message) {

        log.info("mq 收到一条消息： {}", message.toString());

        try {
            switch (message.getType()) {
                case MQIndexMessage.CREATE_OR_UPDATE:
                    searchService.createOrUpdateCarInfoIndex(message);
                    break;
                case MQIndexMessage.REMOVE:// 删除es中的汽车信息
                    searchService.removeCarInfoIndex(message);
                    break;
                case MQIndexMessage.SYS_USER_INFO_UPDATE:
                    // 更新数据库用户信息
                    sysUserService.updateById((LoginUser) message.getLoginUser());
                    break;

                default:
                    log.error("没找到对应的消息类型，请注意！！ --》 {}", message.toString());
                    break;
            }
        } catch (DataAccessResourceFailureException | ListenerExecutionFailedException e) {
            e.printStackTrace();
        }
    }
}

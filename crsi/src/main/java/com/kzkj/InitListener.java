package com.kzkj;

import com.google.common.eventbus.AsyncEventBus;
import com.kzkj.listener.DeliveryEventListener;
import com.kzkj.listener.ImportInventoryListener;
import com.kzkj.listener.ImportLogisticsListener;
import com.kzkj.listener.ImportOrderListener;
import com.kzkj.pojo.po.Company;
import com.kzkj.service.CompanyService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Component
public class InitListener implements ApplicationRunner {

    @Autowired
    AsyncEventBus asyncEventBus;

    @Autowired
    DeliveryEventListener deliveryEventListener;

    @Autowired
    ImportInventoryListener importInventoryListener;

    @Autowired
    ImportLogisticsListener importLogisticsListener;

    @Autowired
    ImportOrderListener importOrderListener;

    @Autowired
    CompanyService companyService;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        asyncEventBus.register(deliveryEventListener);
        asyncEventBus.register(importInventoryListener);
        asyncEventBus.register(importLogisticsListener);
        asyncEventBus.register(importOrderListener);
        log.info("事件监听器启动成功！");

        //读取分中心申请好传输id的企业，循环生成监听器，并启动
        List<Company> list=companyService.selectList(null);
        //StringBuilder stringBuiilder=new StringBuilder();

        Channel channelE = connectionFactory.createConnection().createChannel(false);
        for(Company company:list)
        {
            if(!StringUtils.isEmpty(company.getDxpIdI()))
            {
                channelE.queueDeclare(company.getDxpIdI(), true, false, false, null);
                channelE.queueDeclare(company.getDxpIdI()+"_HZ", true, false, false, null);
                //simpleMessageListenerContainer.addQueueNames(company.getDxpIdI());
                simpleMessageListenerContainer.addQueueNames(company.getDxpIdE());
                log.info("dxpid:{}",company.getDxpIdI());
            }
        }


        log.info("系统监听启动完成。开始接收企业报文！");

    }
}

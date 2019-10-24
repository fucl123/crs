package com.kzkj;

import com.google.common.eventbus.AsyncEventBus;
import com.kzkj.listener.*;
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
    OrderEventListener orderEventListener;
    @Autowired
    ArrivalEventListener arrivalEventListener;
    @Autowired
    DeliveryEventListener deliveryEventListener;
    @Autowired
    DepartureEventListener departureEventListener;
    @Autowired
    InventoryEventListener inventoryEventListener;
    @Autowired
    InvtCancelEventListener invtCancelEventListener;
    @Autowired
    LogisticsEventListener logisticsEventListener;
    @Autowired
    ReceiptsEventListener receiptsEventListener;
    @Autowired
    SummaryApplyeEventListener summaryApplyeEventListener;
    @Autowired
    SummaryResultEventListener summaryResultEventListener;
    @Autowired
    WaybillEventListener waybillEventListener;
    @Autowired
    CompanyService companyService;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        asyncEventBus.register(orderEventListener);
        asyncEventBus.register(arrivalEventListener);
        asyncEventBus.register(deliveryEventListener);
        asyncEventBus.register(departureEventListener);
        asyncEventBus.register(inventoryEventListener);
        asyncEventBus.register(invtCancelEventListener);
        asyncEventBus.register(logisticsEventListener);
        asyncEventBus.register(receiptsEventListener);
        asyncEventBus.register(summaryApplyeEventListener);
        asyncEventBus.register(summaryResultEventListener);
        asyncEventBus.register(waybillEventListener);
        log.info("事件监听器启动成功！");

        //读取分中心申请好传输id的企业，循环生成监听器，并启动
        List<Company> list=companyService.selectList(null);
        //StringBuilder stringBuiilder=new StringBuilder();

        Channel channelE = connectionFactory.createConnection().createChannel(false);
        for(Company company:list)
        {
            if(!StringUtils.isEmpty(company.getDxpId()))
            {
                //stringBuiilder.append(company.getDxpId()).append(";");
                //stringBuiilder.append(company.getDxpId()).append("_HZ;");
                channelE.queueDeclare(company.getDxpId(), true, false, false, null);
                channelE.queueDeclare(company.getDxpId()+"_HZ", true, false, false, null);
                simpleMessageListenerContainer.addQueueNames(company.getDxpId());
                log.info("dxpid:{}",company.getDxpId());
            }
        }


        log.info("系统监听启动完成。开始接收企业报文！");

    }
}

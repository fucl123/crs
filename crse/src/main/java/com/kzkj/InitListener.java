package com.kzkj;

import com.google.common.eventbus.AsyncEventBus;
import com.kzkj.listener.*;
import com.kzkj.mq.MqSender;
import com.kzkj.pojo.po.Company;
import com.kzkj.pojo.vo.request.customs.Custom;
import com.kzkj.service.CompanyService;
import com.kzkj.utils.XMLUtil;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    public MqSender mqSender;

    @Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        asyncEventBus.register(orderEventListener);
        asyncEventBus.register(arrivalEventListener);
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

        Channel channelE = connectionFactory.createConnection().createChannel(false);
        for(Company company:list)
        {
            //出口
            if(!StringUtils.isEmpty(company.getDxpIdE()))
            {
                channelE.queueDeclare(company.getDxpIdE(), true, false, false, null);
                channelE.queueDeclare(company.getDxpIdE()+"_HZ", true, false, false, null);
                simpleMessageListenerContainer.addQueueNames(company.getDxpIdE());
                log.info("dxpide:{}",company.getDxpIdE());
            }
        }
        /*try{
            simpleMessageListenerContainer.addQueueNames("Q_SY3R010_RecvSW214130Kzkj");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        log.info("系统监听启动完成。开始接收企业报文！");

        String newxml = fileRead("C:\\Users\\32723\\Desktop\\跨境终端报文\\aaa.xml");
        //String xmll =newxml.replace("dxp:", "");
        //Custom customs = (Custom) XMLUtil.convertXmlStrToObject(Custom.class,xmll);
        try {
            log.info("待发送xml:"+newxml);
            mqSender.sendMsg("Q_SY3R010_SendSW214130Kzkj", newxml,"sas");
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    public String fileRead(String path) throws Exception {
        File file = new File(path);//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }

}

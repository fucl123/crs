package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.order.CEB303Message;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener{

    @Subscribe
    public void listener(CEB303Message ceb303Message){

    }

}

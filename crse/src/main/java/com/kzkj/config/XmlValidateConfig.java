package com.kzkj.config;

import com.kzkj.utils.XmlValidate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlValidateConfig {

    @Value("${xmlvalidate.path.crse}")
    public String crsepath;

    @Value("${xmlvalidate.path.crsi}")
    public String crsipath;

    @Value("${xmlvalidate.path.transfer}")
    public String transferpath;

    @Value("${xmlvalidate.path.terminal}")
    public String terminalpath;

    /**
     * 出口业务节点报文校验
     * @return
     */
    @Bean(name = "crseXmlValidate")
    public XmlValidate XmlValidate(){
        XmlValidate xmlValidate = new XmlValidate();
        xmlValidate.setXsdpath(crsepath);
        return xmlValidate;
    }

    /**
     * 进口业务节点报文校验
     * @return
     */
    @Bean(name = "crsiXmlValidate")
    public XmlValidate XmlValidate2(){
        XmlValidate xmlValidate = new XmlValidate();
        xmlValidate.setXsdpath(crsipath);
        return xmlValidate;
    }

    /**
     * 传输节点报文校验
     * @return
     */
    @Bean(name = "transferXmlValidate")
    public XmlValidate XmlValidate3(){
        XmlValidate xmlValidate = new XmlValidate();
        xmlValidate.setXsdpath(transferpath);
        return xmlValidate;
    }

    /**
     * 终端节点报文校验
     * @return
     */
    @Bean(name = "terminalXmlValidate")
    public XmlValidate XmlValidate4(){
        XmlValidate xmlValidate = new XmlValidate();
        xmlValidate.setXsdpath(terminalpath);
        return xmlValidate;
    }
}

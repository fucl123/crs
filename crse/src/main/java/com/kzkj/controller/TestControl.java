package com.kzkj.controller;

import com.kzkj.pojo.po.Company;
import com.kzkj.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/test")
public class TestControl {

    @Autowired
    CompanyService companyService;

    @RequestMapping("/company")
    @ResponseBody
    public String test(@RequestParam Integer id){

        Company company =new Company();
        company.setId(id);
        company.setCompanyCode("companyCode");
        company.setCompanyName("companyName");
        company.setDxpId("dxpid");
        companyService.insert(company);
        return "hello";
    }

    @RequestMapping("/company2")
    public void test2(Company company){


        companyService.insert(company);
    }
}

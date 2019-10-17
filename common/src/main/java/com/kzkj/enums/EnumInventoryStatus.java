package com.kzkj.enums;



public enum EnumInventoryStatus  {

    DZKAYZC("电子口岸已暂存", 1),
    DZKASBZ("电子口岸申报中", 2),
    FSHGCG("发送海关成功", 3),
    FSHGSB("发送海关失败", 4),
    HGTD("海关退单", 100),
    HGRK("海关入库", 120),
    RGSH("人工审核", 300),
    HGSJ("海关审结", 399),
    FX("放行", 800),
    JG("结关", 899)
    ;

    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    EnumInventoryStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}

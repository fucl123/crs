package com.kzkj.enums;

public enum EnumMsgType {

    /**
     * 订单
     */
    Order("CEB303Message"),

    /**
     * 进口订单
     */
    ImportOrder("CEB311Message"),

    /**
     * 运单
     */
    Logistics("CEB505Message"),

    /**
     * 进口运单
     */
    ImportLogistics("CEB511Message"),

    /**
     * 进口运单状态
     */
    ImportLogisticsStatus("CEB513Message"),

    /**
     * 进口清单
     */
    ImportInventory("CEB621Message"),

    /**
     * 退货申请
     */
    InvtRefund("CEB625Message"),

    /**
     * 清单
     */
    Inventory("CEB603Message"),

    /**
     * 清单取消
     */
    InvtCancel("CEB605Message"),

    /**
     * 离境
     */
    Departure("CEB509Message"),

    /**
     * 运抵
     */
    Arrival("CEB507Message"),

    /**
     * 汇总结果单
     */
    SummaryResult("CEB792Message"),

    /**
     * 汇总申请单
     */
    SummaryApply("CEB701Message"),

    /**
     * 清单总分单
     */
    WayBill("CEB607Message"),

    /**
     * 入库明细
     */
    Delivery("CEB711Message"),

    /**
     * 电子税单
     */
    Tax("CEB816Message"),

    /**
     * 电子税单状态
     */
    TaxStatus("CEB818Message"),

    /**
     * 收款单
     */
    Receipts("CEB403Message"),

    /**
     * 支付单
     */
    PayMent("CEB411Message");

    private String name;

    private EnumMsgType(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

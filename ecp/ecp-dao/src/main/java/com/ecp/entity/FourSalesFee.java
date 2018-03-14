package com.ecp.entity;

import javax.persistence.*;

@Table(name = "four_sales_fee")
public class FourSalesFee {
    /**
     * 四项费用表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 交通费
     */
    @Column(name = "transportation_fee")
    private Long transportationFee;

    /**
     * 通讯费
     */
    @Column(name = "communication_fee")
    private Long communicationFee;

    /**
     * 招待费
     */
    @Column(name = "entertainment_fee")
    private Long entertainmentFee;

    /**
     * 差旅费
     */
    @Column(name = "travel_expense_fee")
    private Long travelExpenseFee;

    /**
     * 市场费
     */
    @Column(name = "market_fee")
    private Long marketFee;

    /**
     * 获取四项费用表自增ID
     *
     * @return id - 四项费用表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置四项费用表自增ID
     *
     * @param id 四项费用表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取交通费
     *
     * @return transportation_fee - 交通费
     */
    public Long getTransportationFee() {
        return transportationFee;
    }

    /**
     * 设置交通费
     *
     * @param transportationFee 交通费
     */
    public void setTransportationFee(Long transportationFee) {
        this.transportationFee = transportationFee;
    }

    /**
     * 获取通讯费
     *
     * @return communication_fee - 通讯费
     */
    public Long getCommunicationFee() {
        return communicationFee;
    }

    /**
     * 设置通讯费
     *
     * @param communicationFee 通讯费
     */
    public void setCommunicationFee(Long communicationFee) {
        this.communicationFee = communicationFee;
    }

    /**
     * 获取招待费
     *
     * @return entertainment_fee - 招待费
     */
    public Long getEntertainmentFee() {
        return entertainmentFee;
    }

    /**
     * 设置招待费
     *
     * @param entertainmentFee 招待费
     */
    public void setEntertainmentFee(Long entertainmentFee) {
        this.entertainmentFee = entertainmentFee;
    }

    /**
     * 获取差旅费
     *
     * @return travel_expense_fee - 差旅费
     */
    public Long getTravelExpenseFee() {
        return travelExpenseFee;
    }

    /**
     * 设置差旅费
     *
     * @param travelExpenseFee 差旅费
     */
    public void setTravelExpenseFee(Long travelExpenseFee) {
        this.travelExpenseFee = travelExpenseFee;
    }

    /**
     * 获取市场费
     *
     * @return market_fee - 市场费
     */
    public Long getMarketFee() {
        return marketFee;
    }

    /**
     * 设置市场费
     *
     * @param marketFee 市场费
     */
    public void setMarketFee(Long marketFee) {
        this.marketFee = marketFee;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", transportationFee=").append(transportationFee);
        sb.append(", communicationFee=").append(communicationFee);
        sb.append(", entertainmentFee=").append(entertainmentFee);
        sb.append(", travelExpenseFee=").append(travelExpenseFee);
        sb.append(", marketFee=").append(marketFee);
        sb.append("]");
        return sb.toString();
    }
}
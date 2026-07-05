/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laundryservice.model;

/**
 *
 * @author Cral
 */
public class Service {
    private int serviceId;
    private String serviceName;
    private double fixedPrice;

    public Service(int serviceId, String serviceName, double fixedPrice) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.fixedPrice = fixedPrice;
    }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public double getFixedPrice() { return fixedPrice; }
    public void setFixedPrice(double fixedPrice) { this.fixedPrice = fixedPrice; }
}
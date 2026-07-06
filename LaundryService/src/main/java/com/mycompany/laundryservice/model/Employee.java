/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laundryservice.model;

/**
 *
 * @author Cral
 */
public class Employee {
    private int employeeId;
    private String name;
    private String username;
    private String password; // stores the bcrypt HASH, never the plaintext

    public Employee(int employeeId, String name, String username, String password) {
        this.employeeId = employeeId;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
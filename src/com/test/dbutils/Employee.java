/*
 * EMPLOYEEBean.java
 *
 * Copyright (C) 2018 FUJITSU LIMITED, All Rights Reserved.
 */

package com.test.dbutils;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * EMPLOYEEBean
 */
public class Employee  {

    private static final long serialVersionUID = 1L;

    //EMPLOYEE_ID
    private int employee_id = 0;

    //EMPLOYEE_NAME
    private String employee_name;

    //EMPLOYEE_ADDRESS
    private String employee_address;

    //EMPLOYEE_BIRTH
    private Timestamp employee_birth = null;

    //EMPLOYEE_SALARY
    private BigDecimal employee_salary = new BigDecimal(0);


    /* テーブル名 */
    private String tableName = "EMPLOYEE";

    /* プライマリーキー */
    private String[] primaryKeys = {"employee_id"};

    /**
     * テーブル名を取得します。
     * @return テーブル名
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * プライマリーキーに指定されているカラム名一覧を取得します。
     * @return プライマリーキー一覧
     */
    public String[] getPrimaryKeys() {
        return primaryKeys;
    }

   /**
    * EMPLOYEE_IDを設定する
    */
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

   /**
    * EMPLOYEE_IDを取得する
    */
    public int getEmployee_id() {
        return employee_id;
    }

   /**
    * EMPLOYEE_NAMEを設定する
    */
    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

   /**
    * EMPLOYEE_NAMEを取得する
    */
    public String getEmployee_name() {
        return employee_name;
    }

   /**
    * EMPLOYEE_ADDRESSを設定する
    */
    public void setEmployee_address(String employee_address) {
        this.employee_address = employee_address;
    }

   /**
    * EMPLOYEE_ADDRESSを取得する
    */
    public String getEmployee_address() {
        return employee_address;
    }

   /**
    * EMPLOYEE_BIRTHを設定する
    */
    public void setEmployee_birth(Timestamp employee_birth) {
        this.employee_birth = employee_birth;
    }

   /**
    * EMPLOYEE_BIRTHを取得する
    */
    public Timestamp getEmployee_birth() {
        return employee_birth;
    }

   /**
    * EMPLOYEE_SALARYを設定する
    */
    public void setEmployee_salary(BigDecimal employee_salary) {
        this.employee_salary = employee_salary;
    }

   /**
    * EMPLOYEE_SALARYを取得する
    */
    public BigDecimal getEmployee_salary() {
        return employee_salary;
    }

}


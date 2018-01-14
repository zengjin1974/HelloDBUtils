package com.test.dbutils;

import java.io.Serializable;

public class Employee implements PrimaryKeys, Serializable {

    private static final long serialVersionUID = 1L;

    //EMPLOYEEID
    private int employeeId;

    //EMPLOYEENAME
    private String employeeName;

    //EMPLOYEE_ADDRESS
    private String employee_address;


    /* テーブル名 */
    private String tableName = "EMPLOYEE";

    /* プライマリーキー */
    private String[] primaryKeys = {"employeeid"};

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
    * EMPLOYEEIDを設定する
    */
    public void setEmployeeId(int employeeid) {
        this.employeeId = employeeid;
    }

   /**
    * EMPLOYEEIDを取得する
    */
    public int getEmployeeId() {
        return employeeId;
    }

   /**
    * EMPLOYEENAMEを設定する
    */
    public void setEmployeename(String employeename) {
        this.employeeName = employeename;
    }

   /**
    * EMPLOYEENAMEを取得する
    */
    public String getEmployeename() {
        return employeeName;
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
     * toString()をオーバーライドします。
     * JavaBeansの内容を取得します。
     * @return JavaBeansの内容
     */
    public String toString() {

        // 返却用
        final int buffer = 200;
        StringBuffer sb = new StringBuffer(buffer);

        // MakePrimaryKeysSQLオブジェクト生成
        MakePrimaryKeysSQL pk = new MakePrimaryKeysSQL(this);

        // 全ての項目取得
        ColumnParamBean[] all = pk.getAll();

        // 項目内容を格納
        for (int i = 0; i < all.length; i++) {
            sb.append(all[i].getColumn() + "=" + all[i].getParam() + ",");
        }

        // 返却
        return "[" + sb.toString() + "]";

    }
}

package sample.dbutils;

public class Employee {
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
}

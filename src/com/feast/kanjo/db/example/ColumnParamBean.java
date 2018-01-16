package com.feast.kanjo.db.example;


/**
 * <pre>
 * カラム名とそれに対する値を保持したJavaBeansです。
 *
 * 2004/02/08 ueno 新規作成
 * </pre>
 *
 * @author ueno
 * @since 1.0.0
 * @version 1.0.0
 */
public class ColumnParamBean {

    /* カラム名 */
    private String column;
    /* 値 */
    private Object param;

    /**
     * <pre>
     * 空のColumnParamBeanオブジェクトを生成します。
     * </pre>
     */
    public ColumnParamBean() {
    }

    /**
     * <pre>
     * ColumnParamBeanオブジェクトを生成します。
     * </pre>
     *
     * @param column
     *            カラム名
     * @param param
     *            値
     */
    public ColumnParamBean(String column, Object param) {
        this.column = column;
        this.param = param;
    }

    /**
     * カラム名を取得します。
     *
     * @return カラム名
     */
    public String getColumn() {
        return column;
    }

    /**
     * カラム名に対する値を取得します。
     *
     * @return 値
     */
    public Object getParam() {
        return param;
    }

    /**
     * カラム名を設定します。
     *
     * @param string
     *            カラム名
     */
    public void setColumn(String string) {
        column = string;
    }

    /**
     * カラム名に対する値を設定します。
     *
     * @param object
     *            値
     */
    public void setParam(Object object) {
        param = object;
    }

}
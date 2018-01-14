package com.test.dbutils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * <pre>
 * PrimaryKeysを実装したJavaBeansを受け取り、プライマリーキーとその値から、
 * SQL作成に必要な情報を生成します。
 * 取得出来る情報としては、プライマリーキー、その他項目、全体項目になっています。
 * いずれもカラム名とそれに対する値をセットにしたものを保持します。
 *
 * 2004/02/02 ueno 新規作成
 * </pre>
 *
 * @author ueno
 * @since 1.0.0
 * @version 1.0.0
 */
public class MakePrimaryKeysSQL {

    /** プライマリーキー名とその値を保持。 */
    private ArrayList pkeyArray = new ArrayList(); // ColumnParamBeanオブジェクトを格納

    /** プライマリーキー以外の通常項目のカラム名とその値を保持。 */
    private ArrayList otherArray = new ArrayList(); // ColumnParamBeanオブジェクトを格納

    /** PrimaryKeysインターフェースの実装メソッド名(String格納)一覧。 */
    private static Vector primaryKeysMethods;

    /** アクセッサ修飾子の各種情報. */
    private static final String GETTER = "get"; // Getter系メソッドのサフィックス
    private static final String SETTER = "set"; // Setter系メソッドのサフィックス
    private static final int ACCESSER_LENGTH = 3; // 上記２文字の文字列長

    /**
     * <pre>
     * PrimaryKeysを実装したクラスを使用し、MakePrimaryKeysSQLオブジェクトを生成します。
     * プライマリーキーを判断し、格納されている値を取り出して保持します。
     * </pre>
     *
     * @param pk
     *            PrimaryKeysを実装したJavaBeans
     */
    public MakePrimaryKeysSQL(PrimaryKeys pk) {

        // PrimaryKeysインターフェース情報を取得し、クラス変数にセット
        setPrimaryKeysInfomation();

        // プライマリーキー名のString配列を取得
        String[] keys = toLowerCase(pk.getPrimaryKeys());
        if (keys == null || keys.length == 0) {
            // キーがなければExceptionスロー
            throw new RuntimeException("プライマリーキーが設定されていません。");
        }

        // プライマリーキー名からBeanに格納されている値を取得
        setPkeyArray(keys, pk);

        // プライマリーキー以外の値を取得
        setOtherArray(keys, pk);

    }

    /**
     * <pre>
     * ”プライマリーキー”とその値を保持したColumnParamBeanオブジェクトの配列を取得します。
     * コンストラクタで与えた情報に基づいています。
     * </pre>
     *
     * @return ColumnParamBeanオブジェクトの配列
     */
    public ColumnParamBean[] getPrimaryKeys() {
        return (ColumnParamBean[]) pkeyArray.toArray(new ColumnParamBean[pkeyArray.size()]);
    }

    /**
     * <pre>
     * ”プライマリーキー以外”とその値を保持したColumnParamBeanオブジェクトの配列を取得します。
     * コンストラクタで与えた情報に基づいています。
     * </pre>
     *
     * @return ColumnParamBeanオブジェクトの配列
     */
    public ColumnParamBean[] getNonPrimaryKeys() {
        return (ColumnParamBean[]) otherArray.toArray(new ColumnParamBean[otherArray.size()]);
    }

    /**
     * <pre>
     * 全ての項目を保持したColumnParamBeanオブジェクトの配列を取得します。
     * コンストラクタで与えた情報に基づいています。
     * </pre>
     *
     * @return ColumnParamBeanオブジェクトの配列
     */
    public ColumnParamBean[] getAll() {

        // プライマリーキー取得
        ColumnParamBean[] pks = getPrimaryKeys();
        // その他取得
        ColumnParamBean[] others = getNonPrimaryKeys();

        // マージ
        int k = 0; // all配列のインデックス
        ColumnParamBean[] all = new ColumnParamBean[pks.length + others.length];
        for (int i = 0; i < pks.length; i++, k++) {
            all[k] = pks[i];
        }
        for (int i = 0; i < others.length; i++, k++) {
            all[k] = others[i];
        }

        return all;

    }

    // /**
    // * プライマリーキーのカラム名をString配列で取得します。
    // * このメソッドで取得するカラム名の順番とgetParamsで取得する値オブジェクトの順番は対応されています。
    // * @return プライマリーキー
    // */
    // public String[] getKeys() {
    //
    // // 保存されているキー＋値をキャスト
    // ColumnParamBean[] beans = getPrimaryKeys();
    // // 返却用オブジェクト生成
    // String[] keys = new String[beans.length];
    // // キー部分だけ返却用に格納
    // for (int i = 0; i < keys.length; i++) {
    // keys[i] = beans[i].getColumn();
    // }
    // return keys;
    //
    // }
    //
    // /**
    // * <pre>
    // * プライマリーキーに対する値の配列を取得します。
    // * コンストラクタで与えたJavaBeansに指定したプライマリーキーの配列順に格納されています。
    // * この取得するObject配列の中に格納されているそれぞれの型は、
    // * JavaBeansでのgetメソッドの戻り値の型そのままに格納されています。
    // * </pre>
    // * @return プライマリーキーに対する値の配列
    // */
    // public Object[] getParams() {
    //
    // // 保存されているキー＋値をキャスト
    // ColumnParamBean[] beans = getPrimaryKeys();
    // // 返却用のオブジェクト生成
    // Object[] params = new Object[beans.length];
    // // 値部分だけ返却用に格納
    // for (int i = 0; i < params.length; i++) {
    // params[i] = beans[i].getParam();
    // }
    // return params;
    //
    // }

    /**
     * <pre>
     * PrimaryKeysインターフェースを実装しているJavaBeansの該当する
     * プライマリーキーGetter系メソッドを実行してその値を取得し、
     * プロパティ名と共にクラス変数に格納します。
     * </pre>
     *
     * @param keys
     *            プライマリーキーのプロパティ名の一覧
     * @param pk
     *            実行元のJavaBeans
     */
    private void setPkeyArray(String[] keys, PrimaryKeys pk) {

        // Getter系メソッドを抽出
        Method[] gets = getGetterMethods(pk.getClass().getMethods());

        /*
         * キーの数だけループし、キーに該当するgetメソッドを抽出し、 そのメソッドを実行して、値を取得する。
         */
        for (int i = 0; i < keys.length; i++) {
            // キーに該当するメソッドオブジェクトを取得
            Method method = searchMethod(keys[i], gets);

            // メソッドを代理実行し、ColumnParamBeanオブジェクトを取得
            ColumnParamBean bean = getColumnParamBean(keys[i], method, pk);

            // ColumnParamBeanオブジェクトを保存
            pkeyArray.add(bean);
        }

    }

    /**
     * <pre>
     * PrimaryKeysインターフェースを実装しているJavaBeansの該当する
     * プライマリーキー以外のGetter系メソッドを実行してその値を取得し、
     * プロパティ名と共にクラス変数に格納します。
     * </pre>
     *
     * @param keys
     *            プライマリーキーのプロパティ名の一覧
     * @param pk
     *            実行元のJavaBeans
     */
    private void setOtherArray(String[] keys, PrimaryKeys pk) {

        // Getter系メソッドを抽出
        Method[] gets = getGetterMethods(pk.getClass().getMethods());

        // 取得したGetter系メソッドの中からプライマリーキー以外のメソッドを抽出
        gets = removePrimaryKey(keys, gets);

        // さらに余分なメソッドを省く
        gets = removePrimaryKeysInterfaceMethod(gets);

        // プロパティ名一覧を取得
        String[] prop = getPropertyList(gets);

        /*
         * キー以外のメソッドの数だけループし、メソッドを代理実行して値を取得し、
         * ColumnParamBeanオブジェクトをクラス変数のotherArrayに保存
         */
        for (int i = 0; i < gets.length; i++) {
            // メソッドを代理実行し、ColumnParamBeanオブジェクトを取得
            ColumnParamBean bean = getColumnParamBean(prop[i], gets[i], pk);

            // ColumnParamBeanオブジェクトを保存
            otherArray.add(bean);
        }

    }

    /**
     * <pre>
     * 第２引数に指定されているメソッド一覧から、第１引数に指定されたプロパティを使用している
     * アクセッサメソッドを検索し、そのメソッドオブジェクトを返却します。
     * 最初に見つかったメソッドを返却します。
     * 見つからなかった場合、RuntimeExceptionをスローします。
     * </pre>
     *
     * @param property
     *            検索したいプロパティ名
     * @param methods
     *            検索対象のメソッド一覧
     * @return 一致したメソッドオブジェクト
     */
    private Method searchMethod(String property, Method[] methods) {

        property = property.toLowerCase();

        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName().toLowerCase();
            if (methodName.startsWith(property, ACCESSER_LENGTH)) {
                if (property.length() == methodName.substring(ACCESSER_LENGTH).length()) {
                    return methods[i];
                }
            }
        }

        throw new RuntimeException("指定されたプロパティ名のアクセッサメソッドが存在しません。property=" + property);

    }

    // /**
    // * <pre>
    // * メソッドクラスの一覧を取得します。
    // * </pre>
    // * @param pk PrimaryKeysを継承したJavaBeans
    // * @return メソッド一覧
    // */
    // private Method[] getGetterMethod(PrimaryKeys pk) {
    //
    // // 返却用メソッド一覧
    // ArrayList list = new ArrayList();
    // Method[] getMethods = null;
    //
    // // メソッド一覧取得
    // Method[] methods = pk.getClass().getMethods();
    //
    // // その中からGetterだけ抽出
    //
    // }

    /**
     * <pre>
     * 引数に与えられたメソッド一覧から、PrimaryKeysインターフェースで定義している
     * メソッドを削除し、残りのメソッド一覧を取得します。
     * </pre>
     *
     * @param methods
     *            削除前のメソッド一覧
     * @return 削除後のメソッド一覧
     */
    private Method[] removePrimaryKeysInterfaceMethod(Method[] methods) {

        ArrayList list = new ArrayList();

        // PrimaryKeysインターフェースの定義メソッドとマッチングし、
        // 一致した場合には返却用に含めない
        for (int i = 0; i < methods.length; i++) {
            if (!primaryKeysMethods.contains(methods[i].getName())) {
                list.add(methods[i]);
            }
        }

        return (Method[]) list.toArray(new Method[list.size()]);

    }

    /**
     * <pre>
     * Getter系メソッドの中から、プライマリーキー以外のメソッド一覧を抽出します。
     * </pre>
     *
     * @param keys
     *            プリマリーキー一覧
     * @param methods
     *            Getter系メソッド一覧
     * @return プライマリーキー以外のGetter系メソッドオブジェクト一覧
     */
    private Method[] removePrimaryKey(String[] keys, Method[] methods) {

        // キー検索用のコレクション(文字列を小文字に変換)
        ArrayList seList = new ArrayList(Arrays.asList(toLowerCase(keys)));
        // 返却用のコレクション
        ArrayList reList = new ArrayList();

        for (int i = 0; i < methods.length; i++) {
            // メソッド名取得
            String name = methods[i].getName();
            // getを省いた文字列取得(getが先頭にない場合対象外)
            String prop = null;
            if (name.startsWith(GETTER)) {
                prop = name.substring(ACCESSER_LENGTH).toLowerCase();
            } else {
                continue;
            }
            // 検索用コレクション内サーチし"なければ"返却用に追加
            if (!seList.contains(prop)) {
                reList.add(methods[i]);
            }
        }

        // 返却
        return (Method[]) reList.toArray(new Method[reList.size()]);

    }

    /**
     * <pre>
     * PrimaryKeysインターフェースのメソッド一覧を取得して、
     * Methodオブジェクトの配列を、クラス変数に格納します。
     * </pre>
     */
    private static synchronized void setPrimaryKeysInfomation() {

        // 当メソッドは最初の１回のみ処理する
        if (primaryKeysMethods != null) {
            return;
        } else {
            primaryKeysMethods = new Vector();
        }

        // PrimaryKeysインターフェースの定義メソッドを取得し、クラス変数にセット
        Method[] methods = PrimaryKeys.class.getMethods();
        for (int i = 0; i < methods.length; i++) {
            primaryKeysMethods.add(methods[i].getName());
        }
        // ObjectクラスのgetClassも加える
        primaryKeysMethods.add("getClass");

    }

    /**
     * <pre>
     * 引数のメソッド一覧からメソッド名一覧を抽出し返却します。
     * </pre>
     *
     * @param methods
     *            メソッドオブジェクトの配列
     * @return メソッド名の配列
     */
    private String[] getMethodNames(Method[] methods) {

        String[] names = new String[methods.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = methods[i].getName();
        }
        return names;

    }

    /**
     * <pre>
     * 引数のメソッド一覧からGetter系メソッドのみ抽出し返却します。
     * メソッド名の始まりは「get」で始まるメソッドのみ対象とします。
     * </pre>
     *
     * @param methods
     *            メソッド一覧
     * @return Getter系メソッドのみの一覧
     */
    private Method[] getGetterMethods(Method[] methods) {

        ArrayList list = new ArrayList();

        // メソッド名一覧取得
        String[] names = getMethodNames(methods);

        // getで始まるメソッドを抽出
        for (int i = 0; i < names.length; i++) {
            if (names[i].startsWith(GETTER)) {
                list.add(methods[i]);
            }
        }

        Method[] m = new Method[list.size()];
        return (Method[]) list.toArray(m);

    }

    /**
     * <pre>
     * String配列の中身を全て小文字に変換し返却します。
     * </pre>
     *
     * @param s
     *            小文字に変換したいString配列
     * @return 小文字に変換されたString配列
     */
    private String[] toLowerCase(String[] s) {

        String[] re = new String[s.length];

        for (int i = 0; i < s.length; i++) {
            re[i] = s[i].toLowerCase();
        }

        return re;

    }

    /**
     * <pre>
     * メソッド一覧からメソッド名先頭にあるアクセッサ修飾子("get","set")を
     * 省いた、いわゆるプロパティ名の一覧を取得します。
     * プロパティ名は自動的に小文字変換します。
     * 仮にアクセッサメソッド以外のメソッドがあった場合は破棄されます。
     * </pre>
     *
     * @param methods
     *            アクセッサメソッドの一覧
     * @return プロパティ名の一覧
     */
    private String[] getPropertyList(Method[] methods) {

        // 返却用コレクション
        ArrayList array = new ArrayList();

        /*
         * メソッドの数だけループし、get,setのメソッドを抽出し、 それらのメソッド名から、get,setを取り除いた文字列を小文字に
         * 変換してから、返却用に格納する。 get,setメソッドでなければ無視する。
         */
        for (int i = 0; i < methods.length; i++) {
            String name = methods[i].getName();
            if (name.startsWith(GETTER) || name.startsWith(SETTER)) {
                String prop = name.substring(ACCESSER_LENGTH).toLowerCase();
                array.add(prop);
            }
        }

        // 返却
        return (String[]) array.toArray(new String[array.size()]);

    }

    /**
     * <pre>
     * 引数に渡されたアクセッサメソッドを代理実行し、その値とカラム名を
     * ColumnParamBeanオブジェクトに詰めて取得します。
     * </pre>
     *
     * @param column
     *            カラム名
     * @param method
     *            実行するアクセッサメソッド
     * @param pk
     *            代理実行するもとのオブジェクト
     * @return 実行結果の詰まったColumnParamBeanオブジェクト
     */
    private ColumnParamBean getColumnParamBean(String column, Method method, PrimaryKeys pk) {

        Object value = null;
        try {
            // メソッドオブジェクトを代理実行し値を取得
            value = method.invoke(pk);
        } catch (Exception e) {
            // 代理実行失敗時
            e.printStackTrace();
            throw new RuntimeException("getメソッド実行に失敗しました。Method=" + method.getClass().getName()
                    + "#" + method.getName());
        }
        // 取り出した値オブジェクトをキーと共に保存
        ColumnParamBean bean = new ColumnParamBean(column, value);
        return bean;

    }

}
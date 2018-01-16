package com.feast.kanjo.db.example;


/**
 * <pre>
 * プライマリーキー等を管理するインターフェースです。
 * 実装側で各テーブルに応じたテーブル名／プライマリーキーを返却するようにしてください。
 *
 * 2004/02/02 ueno 新規作成
 * </pre>
 *
 * @author ueno
 * @since 1.0.0
 * @version 1.0.0
 */
public interface PrimaryKeys {

    /**
     * <pre>
     * テーブル名を取得します。
     * 実装側でオーバーライドして、テーブル名を返却してください。
     * </pre>
     *
     * @return テーブル名
     */
    String getTableName();

    /**
     * <pre>
     * プライマリーキーを取得します。
     * 実装側でオーバーライドして、プライマリーキーのカラム名を配列で返却してください。
     * テーブル名は含めないでください。
     * </pre>
     *
     * @return プライマリーキーの配列
     */
    String[] getPrimaryKeys();

}

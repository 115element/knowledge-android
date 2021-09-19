package com.example.knowledge_android.daosupport_annotation.bean.master;


//OrmLite注解方式
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "cashier_plus")
public class CashierPlus {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(uniqueCombo = false, width = 20)
    String custId;

    @DatabaseField(uniqueCombo = false, width = 20)
    String storeId;

    @DatabaseField(uniqueCombo = false, width = 20, canBeNull = false)
    String cashierNo;

    @DatabaseField(format = "yyyy-MM-dd HH:mm:ss", dataType = DataType.DATE_STRING, canBeNull = false)
    Date version;

/*
    @DatabaseField注解可以有以下字段：

    columnName 列名，未指定时为字段名

    dataType DataType类的类型的字段。通常的类型是从Java类的领域，并不需要指定。

    defaultValue 默认值

    width 宽度 默认是0，表示不限

    canBeNull 是否允许为空，默认为true

    id 主键 默认为false

    generatedId 自增长的主键 默认值是false

    generatedIdSequence 字符串名称的序列号 类同generatedId，但您可以指定序列的名称使用。默认为null

    foreign 外键，默认为false,字段不能是一个原始类型。在外键对象的类中，必须要有一个ID字段（ID， generatedId，generatedIdSequence）

    useGetSet 应用get和set方法访问。默认为false

    unknownEnumName 表示该字段是一个Java的枚举类型

    throwIfNull 如果为空值，抛出一个异常 默认为false

    persisted 是否在数据库中存储这个领域 默认为true

    format 指定某一特定领域的信息格式,如指定日期字符串的格式

    unique 唯一约束，默认为false

    uniqueCombo 唯一行，该行内所有字段成为一个唯一约束，如有firstName 和 lastName两个字段，为"张"和"梅"，那么该表内不可再插             入"张"，"梅"，   但你可插入"张"，"全梅"。

    index 是否建立索引 默认为false

    uniqueIndex 唯一索引 默认为false

    indexName 为这一领域的索引添加一个名字

    uniqueIndexName 为这一领域的索引添加一个唯一的名字

    foreignAutoRefresh 当查询到一个外键对象时，是否自动刷新 如 Order表中有Account外键对象，当返回Order的记录时是否也返回Account的记录，           默认为false

    maxForeignAutoRefreshLevel 为了防止无限递归或者无限循环时 需要用到该属性设置自动刷新的最高级别

    allowGeneratedIdInsert 插入一个ID字段是否覆盖它生成的ID的对象 默认为false

    columnDefinition 定义列，默认情况下，数据库类型是用于自动生成所需的SQL来创建列，所以该属性并不常用 [columnDefinition = 'DECIMAL(12,2) NOT NULL']

    foreignAutoCreate 在插入一个有外键对象的对象时，是否自动插入这个外键对象

    version 行版本 当一个对象被更新，以防止数据损坏多个实体时更新在同一时间进行的保护
*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCashierNo() {
        return cashierNo;
    }

    public void setCashierNo(String cashierNo) {
        this.cashierNo = cashierNo;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "CashierPlus{" +
                "id=" + id +
                ", custId='" + custId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", cashierNo='" + cashierNo + '\'' +
                ", version=" + version +
                '}';
    }
}

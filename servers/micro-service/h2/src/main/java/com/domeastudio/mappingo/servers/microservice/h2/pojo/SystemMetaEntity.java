package com.domeastudio.mappingo.servers.microservice.h2.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(schema = "PUBLIC", catalog = "systemdb", name = "system_meta")
public class SystemMetaEntity implements Serializable {
    private String smid;
    private String tableName;
    private String fieldName;
    private FieldTypeEnum fieldType;
    private String referenceTable;
    private String foreignKey;
    private FieldTypeEnum foreignKeyType;
    private String alias;

    @Id
    @GeneratedValue(generator = "autoid")
    @GenericGenerator(name = "autoid", strategy = "uuid")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(sequenceName = "SystemMeta_Sequence", name = "strategy",initialValue=1,allocationSize=1)
    @Column(name = "SMID", unique = true, nullable = false, precision = 10, scale = 0)
    public String getSmid() {
        return smid;
    }

    public void setSmid(String smid) {
        this.smid = smid;
    }

    @Basic
    @Column(name = "TableName")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Basic
    @Column(name = "FieldName")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Basic
    @Column(name = "FieldType")
    public FieldTypeEnum getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldTypeEnum fieldType) {
        this.fieldType = fieldType;
    }

    @Basic
    @Column(name = "ReferenceTable")
    public String getReferenceTable() {
        return referenceTable;
    }

    public void setReferenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
    }

    @Basic
    @Column(name = "ForeignKe")
    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    @Basic
    @Column(name = "ForeignKeyType")
    public FieldTypeEnum getForeignKeyType() {
        return foreignKeyType;
    }

    public void setForeignKeyType(FieldTypeEnum foreignKeyType) {
        this.foreignKeyType = foreignKeyType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        if (alias == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(getTableName());
            stringBuffer.append("_");
            stringBuffer.append(getFieldName());
            stringBuffer.append("_");
            stringBuffer.append(UUID.randomUUID().toString().replace("_", ""));
            this.alias = stringBuffer.toString();
        } else {
            this.alias = alias;
        }
    }
}

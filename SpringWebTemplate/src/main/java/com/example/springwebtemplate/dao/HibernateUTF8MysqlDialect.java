package com.example.springwebtemplate.dao;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class HibernateUTF8MysqlDialect extends MySQL5InnoDBDialect {

    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci";
    }
}

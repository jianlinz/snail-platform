package indi.jl.sp.data.jpa;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sp.data.jpa")
public class SpJpaProperties {

    /**
     * 是否输出sql
     */
    private Boolean showSql = false;

    public Boolean getShowSql() {
        return showSql;
    }

    public void setShowSql(Boolean showSql) {
        this.showSql = showSql;
    }

    /**
     * liquibase配置
     */
    public static class Liquibase {

        /**
         * 是否启动liquibase
         */
        private Boolean enable = true;

        /**
         * 是否跑基线版本
         */
        private Boolean runBaseLine = true;

        /**
         * liquibase baseline changelog path
         */
        private String baseLineChangeLogPath = "classpath:/liquibase/baseline/*/*.xml";

        /**
         * liquibase current changelog path
         */
        private String currentChangeLogPath = "classpath:/liquibase/current/*/*.xml";

        /**
         * Default database schema.
         */
        private String defaultSchema;

        /**
         * Schema to use for Liquibase objects.
         */
        private String liquibaseSchema;

        /**
         * Tablespace to use for Liquibase objects.
         */
        private String liquibaseTablespace;

        /**
         * Name of table to use for tracking change history.
         */
        private String databaseChangeLogTable = "DATABASECHANGELOG";

        /**
         * Name of table to use for tracking concurrent Liquibase usage.
         */
        private String databaseChangeLogLockTable = "DATABASECHANGELOGLOCK";
    }
}

package com.k0s.datasourceagregatorservice.configuration;

public class DataSourceDriverResolver {
    public static String resolveDriver(String databaseType) {
        return switch (databaseType) {
            case "postgres" -> "org.postgresql.Driver";
            case "mysql" -> "com.mysql.cj.jdbc.Driver";
            default -> throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        };
    }
}

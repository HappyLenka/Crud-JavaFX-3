/* package com.emma.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3305/testdb?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "Brownie123";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
 */

package com.emma.repository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;

import org.json.JSONObject;

public class DBConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try {
            InputStream is = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("config.json");

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject config = new JSONObject(content).getJSONObject("db");

            URL = config.getString("url");
            USER = config.getString("user");
            PASSWORD = config.getString("password");

        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar config.json", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
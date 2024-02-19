package com.aoxx.security.connection;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class PostgreSQLConnectionTest {

    private String URL = "jdbc:postgresql://localhost:5432/postgres";
    private String USERNAME = "postgres";
    private String PASSWORD = "0000";


    @Test
    @DisplayName("DB 연결테스트")
    public void ConnectionTest() throws Exception{
        Connection con = DriverManager.getConnection(URL,USERNAME,PASSWORD); //db 연결
        Assertions.assertThat(con).isNotNull();
    }

    @Test
    @DisplayName("DB 연결 및 특정 ID의 이름 검증")
    public void ConnectionNamePrintTest() throws Exception {
        // Given
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement pre = con.createStatement();
             // DB 스키마 이름이 spring , 테이블 명 : members.info
             ResultSet rs = pre.executeQuery("SELECT * FROM spring.members_info WHERE id = 9")) {

            // When
            String name = null;
            if (rs.next()) {
                name = rs.getString("name");
            }

            // Then
            Assertions.assertThat(name).isEqualTo("Illaoi Quirinus Quirrell");
        } catch (SQLException e) {
            // 예외 처리
            Fail.fail("테스트에 예외가 발생했습니다: " + e.getMessage());
        }
    }
}

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;

public class JdbcTest {

    @Test
    @DisplayName("JDBC 연결 및 SELECT 쿼리 테스트")
    void jdbcConnectionTest() {
        String path = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jdbcTest";

        Instant start = null;   // 성능 측정 시작 시각
        Instant end = null;     // 성능 측정 종료 시각
        
        try {
            // ======================측정 시작===================== //
            start = Instant.now();

            // 1. JDBC 드라이버 로딩
            Class.forName(path);

            // 2. Connection 객체 생성
            Connection conn = DriverManager.getConnection(url, "user", "password");

            end = Instant.now();
            System.out.println("Connection 수행 시간: " + Duration.between(start, end).toMillis() + "ms");
            // ======================측정 종료===================== //

            // ======================측정 시작===================== //
            start = Instant.now();

            // 3. Statement 객체 생성
            Statement statement = conn.createStatement();

            end = Instant.now();
            System.out.println("Statement 생성 수행 시간: " + Duration.between(start, end).toMillis() + "ms");
            // ======================측정 종료===================== //

            // ======================측정 시작===================== //
            start = Instant.now();

            // 4. SELECT 쿼리 실행
            String query = "SELECT * FROM user";
            ResultSet result = statement.executeQuery(query);

            end = Instant.now();
            System.out.println("Query 수행 시간: " + Duration.between(start, end).toMillis() + "ms");
            // ======================측정 종료===================== //

            // 5. 쿼리 실행 결과 처리
            while(result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                int age = result.getInt(3);

                System.out.println("id =" + id + ", name = " + name + ", age = " + age);
            }

            // 6. ResultSet, Statement, Connection 객체 close
            result.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
}

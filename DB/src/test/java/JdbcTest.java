import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.*;

public class JdbcTest {

    @Test
    @DisplayName("JDBC 연결 및 SELECT 쿼리 테스트")
    void jdbcConnectionTest() {
        String path = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jdbcTest";

        try {
            // 1. JDBC 드라이버 로딩
            Class.forName(path);
            
            // 2. Connection 객체 생성
            Connection conn = DriverManager.getConnection(url, USERNAME, PASSWORD);

            // 3. Statement 객체 생성
            Statement statement = conn.createStatement();

            // 4. SELECT 쿼리 실행
            String query = "SELECT * FROM user";
            ResultSet result = statement.executeQuery(query);

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

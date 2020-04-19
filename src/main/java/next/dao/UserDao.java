package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//Data Access Object, 주로 데이터베이스에 대한 접근 로직 처리를 담당 하는 객체
public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    public RowMapper<User> rowMapper = new RowMapper() {
        @Override
        public User mapRow(ResultSet rs) throws SQLException {
            return new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email")
            );
        }
    };

    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
            @Override
            public void values(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };
        String sql = "INSERT INTO USERS VALUES (?,?,?,?)";
        jdbcTemplate.execute(sql, pstmtSetter);
    }

    public void update(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
            @Override
            public void values(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
        jdbcTemplate.execute(sql, pstmtSetter);
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
            @Override
            public void values(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
        return jdbcTemplate.queryForObject(sql, pstmtSetter, rowMapper);
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
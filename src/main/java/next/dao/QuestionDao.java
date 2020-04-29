package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuestionDao {
    private RowMapper<Question> rowMapper = new RowMapper<Question>() {
        @Override
        public Question mapRow(ResultSet rs) throws SQLException {
            return new Question(rs.getLong("questionId"),
                    rs.getString("wirter"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getTimestamp("createdData"),
                    rs.getInt("countOfAnswer"));
        }
    };

    public Question findById(Long questionId) {
        JdbcTemplate<Question> jdbcTemplate = new JdbcTemplate<>();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FORM QUESTIONS WHERE questionId = ?";
        PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
            @Override
            public void values(PreparedStatement pstmt) throws SQLException {
                pstmt.setLong(1, questionId);
            }
        };

        return jdbcTemplate.queryForObject(sql, pstmtSetter, rowMapper);
    }

    public List<Question> findAll() {
        JdbcTemplate<Question> jdbcTemplate = new JdbcTemplate<>();
        String sql = "SELECT * FROM QUESTIONS";

        return jdbcTemplate.query(sql, rowMapper);
    }
}

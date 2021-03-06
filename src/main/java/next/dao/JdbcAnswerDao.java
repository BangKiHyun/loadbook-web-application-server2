package next.dao;

import core.jdbc.*;
import next.model.Answer;

import java.sql.*;
import java.util.List;

public class JdbcAnswerDao implements AnswerDao{
    private JdbcTemplate<Answer> jdbcTemplate = JdbcTemplate.getInstance();

    @Override
    public Answer insert(Answer answer) {
        String sql = "INSERT INTO  (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
            @Override
            public void values(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, answer.getWriter());
                pstmt.setString(2, answer.getContents());
                pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
                pstmt.setLong(4, answer.getQuestionId());
            }
        };

        jdbcTemplate.execute(sql, pstmtSetter);
        return findById(answer.getAnswerId());
    }

    @Override
    public Answer findById(long answerId) {
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";
        RowMapper<Answer> rowMapper = rs -> new Answer(rs.getLong("answerId"),
                rs.getString("writer"),
                rs.getString("contents"),
                rs.getTimestamp("createDate"),
                rs.getLong("questionId"));

        return jdbcTemplate.queryForObject(sql, rowMapper, answerId);
    }

    @Override
    public void delete(long answerId) {
        String sql = "DELETE ANSWERS WHERE answerId = ?";
        PreparedStatementSetter pstmtSetter = pstmt -> pstmt.setLong(1, answerId);

        jdbcTemplate.execute(sql, pstmtSetter);
    }

    @Override
    public List<Answer> findAllByQuestionId(long questionId) {
        String sql = "SELECT * FROM ANSWERS WHERE questionId = ?";
        PreparedStatementSetter pstmtSetter = pstmt -> pstmt.setLong(1, questionId);

        RowMapper<Answer> rowMapper = rs -> new Answer(rs.getLong("answerId"),
                rs.getString("writer"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                questionId);

        return jdbcTemplate.query(sql,rowMapper, pstmtSetter);
    }
}

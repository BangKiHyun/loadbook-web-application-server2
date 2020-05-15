package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.Question;

import java.sql.*;
import java.util.List;

public class JdbcQuestionDao implements QuestionDao{
    private JdbcTemplate<Question> jdbcTemplate = JdbcTemplate.getInstance();

    private RowMapper<Question> rowMapper = rs -> new Question(rs.getLong("questionId"),
            rs.getString("writer"),
            rs.getString("title"),
            rs.getString("contents"),
            rs.getTimestamp("createdData"),
            rs.getInt("countOfAnswer"));

    @Override
    public Question insert(Question question) {
        String sql = "INSERT INTO  (writer, title, contents, createdDate) VALUES (?, ?, ?, ?, ?)";
        PreparedStatementSetter pstmtSetter = pstmt -> {
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
        };
        jdbcTemplate.execute(sql, pstmtSetter);
        return findById(question.getQuestionId());
    }

    @Override
    public void update(Question question){
        String sql = "UPDATE SET title = ?, contents = ? WHERE questionId = ?";
        PreparedStatementSetter pstmtSetter = pstmt -> {
            pstmt.setString(1, question.getTitle());
            pstmt.setString(2, question.getContents());
            pstmt.setLong(3, question.getQuestionId());
        };
        jdbcTemplate.execute(sql, pstmtSetter);
    }

    @Override
    public Question findById(Long questionId) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FORM QUESTIONS WHERE questionId = ?";
        PreparedStatementSetter pstmtSetter = pstmt -> pstmt.setLong(1, questionId);

        return jdbcTemplate.queryForObject(sql, pstmtSetter, rowMapper);
    }

    @Override
    public List<Question> findAll() {
        String sql = "SELECT * FROM QUESTIONS";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void delete(long questionId) {
        String sql = "DELETE FROM QUESTION WHERE questionId = ?";
        PreparedStatementSetter pstmtSetter = pstmt -> {
            pstmt.setLong(1, questionId);
        };
        jdbcTemplate.execute(sql, pstmtSetter);
    }
}

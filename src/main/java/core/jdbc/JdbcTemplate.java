package core.jdbc;

import next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate<T> {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);

    public void execute(String sql, PreparedStatementSetter pstmtSetter) throws DataAccessException {
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmtSetter.values(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
        ) {
            List<T> users = new ArrayList<>();
            while (rs.next()) {
                users.add(rowMapper.mapRow(rs));
            }

            return users;

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rowMapper) throws DataAccessException {
        ResultSet rs = null;
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmtSetter.values(pstmt);
            rs = pstmt.executeQuery();
            rs.next();
            return rowMapper.mapRow(rs);
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e.getMessage());
                }
            }
        }
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, long answerId) throws DataAccessException {
        ResultSet rs = null;
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setLong(1, answerId);
            rs = pstmt.executeQuery();
            rs.next();
            return rowMapper.mapRow(rs);
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e.getMessage());
                }
            }
        }
    }
}

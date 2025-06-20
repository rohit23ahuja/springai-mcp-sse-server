package dev.rohitahuja.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FeedCountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long getFeedCount(String date, String feed) {
        String sql = "SELECT feed_count FROM feed_count WHERE feed_date = ? AND feed_name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{Date.valueOf(date), feed}, Long.class);
    }

    /**
     * Returns a map of feed_date to count for the given month and feed.
     * @param month in format "yyyy-MM"
     * @param feed feed name
     * @return Map of LocalDate to count
     */
    public Map<LocalDate, Long> getFeedCountsByMonth(String month, String feed) {
        String sql = "SELECT feed_date, feed_count FROM feed_count " +
                "WHERE TO_CHAR(feed_date, 'YYYY-MM') = ? AND feed_name = ?";
        return jdbcTemplate.query(sql, new Object[]{month, feed}, rs -> {
            Map<LocalDate, Long> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getDate("feed_date").toLocalDate(), rs.getLong("feed_count"));
            }
            return result;
        });
    }
}

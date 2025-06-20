package dev.rohitahuja.service;

import dev.rohitahuja.repository.FeedCountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class FeedCountService {
    private static final Logger _log = LoggerFactory.getLogger(FeedCountService.class);

    @Autowired
    private FeedCountRepository feedCountRepository;

    /**
     * Returns the count for a specific date and feed.
     * @param date date in format "yyyy-MM-dd"
     * @param feed feed name
     * @return count
     */
    @Tool(name="feedcount_by_date_and_feed", description="Get the count for a specific date and feed.")
    public Long getFeedCount(@ToolParam(required = true, description = "date in format yyyy-MM-dd") String date,
                             @ToolParam(required = true, description = "feed name - orders or trades") String feed) {
        _log.info("Getting feed count for date: {} and feed: {}", date, feed);
        Long count = feedCountRepository.getFeedCount(date, feed);
        _log.debug("Retrieved count: {}", count);
        return count;
    }

    /**
     * Returns a map of feed_date to count for the given month and feed.
     * @param month in format "yyyy-MM"
     * @param feed feed name
     * @return Map of LocalDate to count
     */
    @Tool(name="feedcount_by_month_and_feed", description="Get the count for a specific month and feed.")
    public Map<LocalDate, Long> getFeedCountsByMonth(@ToolParam(required = true, description = "month in format yyyy-MM") String month,
                                                     @ToolParam(required = true, description = "feed name - orders or trades") String feed) {
        _log.info("Getting feed counts for month: {} and feed: {}", month, feed);
        Map<LocalDate, Long> counts = feedCountRepository.getFeedCountsByMonth(month, feed);
        _log.debug("Retrieved {} entries", counts.size());
        return counts;
    }
}

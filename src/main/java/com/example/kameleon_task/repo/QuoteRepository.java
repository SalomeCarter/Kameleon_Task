package com.example.kameleon_task.repo;

import com.example.kameleon_task.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findByUserId(Long userId);

    @Query("SELECT q, SUM(CASE WHEN v.type = 'LIKE' THEN 1 WHEN v.type = 'DISLIKE' THEN -1 ELSE 0 END) AS netVotes " +
            "FROM Quote q LEFT JOIN q.votes v " +
            "GROUP BY q " +
            "ORDER BY netVotes DESC")
    List<Quote> findTop10QuotesOrderByVotes();

    @Query("SELECT q, SUM(CASE WHEN v.type = 'LIKE' THEN 1 WHEN v.type = 'DISLIKE' THEN -1 ELSE 0 END) AS netVotes " +
            "FROM Quote q LEFT JOIN q.votes v " +
            "GROUP BY q " +
            "ORDER BY netVotes ASC")
    List<Quote> findFlop10QuotesOrderByVotes();

}

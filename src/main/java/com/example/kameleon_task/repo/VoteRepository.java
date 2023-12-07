package com.example.kameleon_task.repo;

import com.example.kameleon_task.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByQuoteIdOrderByCreationDate(Long quoteId);

}


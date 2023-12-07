package com.example.kameleon_task.service;

import com.example.kameleon_task.entity.Quote;
import com.example.kameleon_task.entity.User;
import com.example.kameleon_task.entity.Vote;
import com.example.kameleon_task.repo.QuoteRepository;
import com.example.kameleon_task.repo.UserRepository;
import com.example.kameleon_task.repo.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;


        public Quote createQuote(String content, Long userId) {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Quote newQuote = Quote.builder()
                        .content(content)
                        .user(user)
                        .creationDate(LocalDateTime.now())
                        .build();

                return quoteRepository.save(newQuote);
            } else {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
        }

    public List<Quote> getQuotesByUserId(Long userId) {
        return quoteRepository.findByUserId(userId);
    }

    public Quote getRandomQuote() {
        long quoteCount = quoteRepository.count();

        if (quoteCount > 0) {
            int randomIndex = (int) (Math.random() * quoteCount);
            List<Quote> allQuotes = quoteRepository.findAll();
            return allQuotes.get(randomIndex);
        } else {
            throw new IllegalStateException("No quotes available");
        }
    }

    public Quote updateQuote(Long quoteId, String newContent, Long userId) {
        Optional<Quote> quoteOptional = quoteRepository.findById(quoteId);

        if (quoteOptional.isPresent()) {
            Quote existingQuote = quoteOptional.get();

            if (!existingQuote.getUser().getId().equals(userId)) {
                throw new IllegalArgumentException("User does not have permission to update this quote");
            }

            existingQuote.setContent(newContent);
            existingQuote.setUpdateDate(LocalDateTime.now());

            return quoteRepository.save(existingQuote);
        } else {
            throw new IllegalArgumentException("Quote not found with ID: " + quoteId);
        }
    }

    public List<Quote> getTop10Quotes() {
        return quoteRepository.findTop10QuotesOrderByVotes();
    }

    public List<Quote> getFlop10Quotes() {
        return quoteRepository.findFlop10QuotesOrderByVotes();
    }

    public void deleteQuote(Long quoteId) {
        if (quoteRepository.existsById(quoteId)) {
            quoteRepository.deleteById(quoteId);
        } else {
            throw new IllegalArgumentException("Quote not found with ID: " + quoteId);
        }
    }

    public List<Vote> getVoteHistory(Long quoteId) {
        return voteRepository.findByQuoteIdOrderByCreationDate(quoteId);
    }



}

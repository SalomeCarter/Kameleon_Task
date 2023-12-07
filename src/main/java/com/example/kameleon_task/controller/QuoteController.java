package com.example.kameleon_task.controller;

import com.example.kameleon_task.dto.QuoteRequestDto;
import com.example.kameleon_task.dto.QuoteUpdateDto;
import com.example.kameleon_task.entity.Quote;
import com.example.kameleon_task.entity.Vote;
import com.example.kameleon_task.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;



    @PostMapping("/create")
    public ResponseEntity<Quote> createQuote(@RequestBody QuoteRequestDto quoteRequestDto) {
        try {
            Quote newQuote = quoteService.createQuote(quoteRequestDto.getContent(), quoteRequestDto.getUserId());
            return ResponseEntity.ok(newQuote);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Quote>> getQuotesByUserId(@PathVariable Long userId) {
        List<Quote> quotes = quoteService.getQuotesByUserId(userId);
        return ResponseEntity.ok(quotes);
    }

    @GetMapping("/random")
    public ResponseEntity<Quote> getRandomQuote() {
        try {
            Quote randomQuote = quoteService.getRandomQuote();
            return ResponseEntity.ok(randomQuote);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{quoteId}")
    public ResponseEntity<Quote> updateQuote(@PathVariable Long quoteId,
                                             @RequestBody QuoteUpdateDto quoteUpdateDto) {
        try {
            Quote updatedQuote = quoteService.updateQuote(quoteId, quoteUpdateDto.getContent(), quoteUpdateDto.getUserId());
            return ResponseEntity.ok(updatedQuote);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Quote>> getTop10Quotes() {
        List<Quote> top10Quotes = quoteService.getTop10Quotes();
        return ResponseEntity.ok(top10Quotes);
    }

    @GetMapping("/flop10")
    public ResponseEntity<List<Quote>> getFlop10Quotes() {
        List<Quote> flop10Quotes = quoteService.getFlop10Quotes();
        return ResponseEntity.ok(flop10Quotes);
    }

    @DeleteMapping("/delete/{quoteId}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long quoteId) {
        try {
            quoteService.deleteQuote(quoteId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{quoteId}/vote-history")
    public ResponseEntity<List<Vote>> getVoteHistory(@PathVariable Long quoteId) {
        List<Vote> voteHistory = quoteService.getVoteHistory(quoteId);
        return ResponseEntity.ok(voteHistory);
    }

}


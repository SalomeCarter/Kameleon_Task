package com.example.kameleon_task.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuoteRequestDto {
    @NotBlank
    private String content;

    @NotNull
    private Long userId;
}

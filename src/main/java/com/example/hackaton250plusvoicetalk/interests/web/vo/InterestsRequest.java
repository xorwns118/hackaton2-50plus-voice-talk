package com.example.hackaton250plusvoicetalk.interests.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class InterestsRequest {
    @JsonProperty("interest_id")
    private Long interestId;

    @NotBlank
    @JsonProperty("interest_name")
    private String interestName;
}

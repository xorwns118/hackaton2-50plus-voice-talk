package com.example.hackaton250plusvoicetalk.interests.web.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
public class UpdateInterestsRequest {
    private List<Long> interestIds;

    @JsonCreator
    public UpdateInterestsRequest(@JsonProperty("interest_ids") List<Long> interestIds) {
        this.interestIds = interestIds;
    }
}

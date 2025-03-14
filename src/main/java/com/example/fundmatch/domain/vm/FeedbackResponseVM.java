package com.example.fundmatch.domain.vm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FeedbackResponseVM {
    private Long id;
    private InvestorResponseVM investor;
    private ProjectResponseVM project;
    private String content;
    private String type;
    private Boolean isPrivate;

}

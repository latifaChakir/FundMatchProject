package com.example.fundmatch.domain.vm;

import lombok.*;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticResponseVM {
    private long users;
    private long sectors;
    private long stages;
    private long investisseurs;
    private long startups;
    private long events;
    Map<String, Long> startupsPerSector;
    Map<String, Long> investorsPerSector;
    Map<String, Long> startupsPerStages;
}

package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.vm.StatisticResponseVM;
import com.example.fundmatch.repository.*;
import com.example.fundmatch.service.interfaces.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;
    private final StageRepository stageRepository;
    private final InvestorRepository investorRepository;
    private final StartupRepository startupRepository;
    private final EventRepository eventRepository;

    @Override
    public StatisticResponseVM getStatistics() {
        long users = userRepository.count();
        long sectors = sectorRepository.count();
        long stages = stageRepository.count();
        long investisseurs = investorRepository.count();
        long startups = startupRepository.count();
        long events = eventRepository.count();

        List<Object[]> results = sectorRepository.countStartupsBySector();
        Map<String, Long> startupsPerSector = results.stream()
                .collect(Collectors.toMap(result -> (String) result[0], result -> (Long) result[1]));

        List<Object[]> results1 = sectorRepository.countStartupsByInvestors();
        Map<String, Long> investorsPerSector = results1.stream()
                .collect(Collectors.toMap(result -> (String) result[0], result -> (Long) result[1]));

        List<Object[]> results3 = stageRepository.countStartupsByStage();
        Map<String, Long> startupsPerStages = results3.stream()
                .collect(Collectors.toMap(result -> (String) result[0], result -> (Long) result[1]));


        return new StatisticResponseVM(users, sectors, stages, investisseurs, startups, events, startupsPerSector, investorsPerSector,startupsPerStages);
    }

}

package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.entities.Investor;
import com.example.fundmatch.domain.entities.Startup;
import com.example.fundmatch.domain.enums.ProjectStatus;
import com.example.fundmatch.domain.vm.AuthResponseVM;
import com.example.fundmatch.domain.vm.StatisticResponseVM;
import com.example.fundmatch.repository.*;
import com.example.fundmatch.service.interfaces.AuthService;
import com.example.fundmatch.service.interfaces.StatisticService;
import com.example.fundmatch.shared.exception.StartupNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final AuthService authService;
    private final ProjectRepository projectRepository;
    private final FeedbackRepository feedbackRepository;
    private final MeetingJoinRepository meetingJoinRepository;

    @Override
    public StatisticResponseVM getStatistics() {
        String currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        AuthResponseVM currentUser = authService.getAuthenticatedUser();

        long users = userRepository.count();
        long sectors = sectorRepository.count();
        long stages = stageRepository.count();
        long investorsCount = investorRepository.count();
        long startupsCount = startupRepository.count();
        long events = eventRepository.count();

        long projectsForCurrentStartup = 0;
        long completedProjects = 0;
        long pendingProjects = 0;
        long totalFeedbackCount = 0;
        long savedProjectsCount = 0;
        long totalMeetings = 0;
        long overdueMeetings = 0;
        long upcomingMeetings = 0;
        Investor investor = investorRepository.findByUserId(currentUser.getId()).orElse(null);

        Startup startup = startupRepository.findByUserId(currentUser.getId()).orElse(null);
        if (startup != null) {
            projectsForCurrentStartup = projectRepository.countProjectsByStartupId(startup.getId());
            completedProjects = projectRepository.countProjectsByStatus(startup.getId(), ProjectStatus.COMPLETED);
            pendingProjects = projectRepository.countProjectsByStatus(startup.getId(), ProjectStatus.PENDING);
            totalFeedbackCount = feedbackRepository.countFeedbacksByStartupId(startup.getId());
              }
        else if (investor != null) {
            savedProjectsCount = investorRepository.countSavedProjects(investor.getId());
            totalMeetings = meetingJoinRepository.countAllByCreatedBy(currentUser.getEmail());
            overdueMeetings = meetingJoinRepository.countOverdueMeetings(currentUser.getEmail(), currentTimestamp);
            upcomingMeetings = meetingJoinRepository.countUpcomingMeetings(currentUser.getEmail(), currentTimestamp);

        } else {
            throw new EntityNotFoundException("No associated Investor for the current user.");
        }

        Map<String, Long> startupsPerSector = sectorRepository.countStartupsBySector().stream()
                .collect(Collectors.toMap(result -> (String) result[0], result -> (Long) result[1]));

        Map<String, Long> investorsPerSector = sectorRepository.countStartupsByInvestors().stream()
                .collect(Collectors.toMap(result -> (String) result[0], result -> (Long) result[1]));

        Map<String, Long> startupsPerStages = stageRepository.countStartupsByStage().stream()
                .collect(Collectors.toMap(result -> (String) result[0], result -> (Long) result[1]));

        return new StatisticResponseVM(users, sectors, stages,
                investorsCount, startupsCount, events, startupsPerSector,
                investorsPerSector, startupsPerStages,
                projectsForCurrentStartup, completedProjects, pendingProjects,
                totalFeedbackCount, savedProjectsCount, totalMeetings,
                overdueMeetings, upcomingMeetings);
    }

}

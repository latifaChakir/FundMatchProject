package com.example.fundmatch;

import com.example.fundmatch.domain.dtos.request.event.CreateEventRequestDto;
import com.example.fundmatch.domain.entities.Event;
import com.example.fundmatch.domain.enums.EventType;
import com.example.fundmatch.domain.mappers.EventMapper;
import com.example.fundmatch.domain.vm.EventResponseVM;
import com.example.fundmatch.repository.EventRepository;
import com.example.fundmatch.service.impl.EventServiceImpl;
import com.example.fundmatch.service.impl.FileStorageService;
import com.example.fundmatch.service.impl.WebSocketNotificationService;
import com.example.fundmatch.shared.exception.EventNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {
    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private WebSocketNotificationService webSocketNotificationService;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEvent_WhenFileProvided_SavesEventWithImagePath() throws IOException {
        CreateEventRequestDto eventRequest = new CreateEventRequestDto();
        eventRequest.setTitle("Test Event");
        eventRequest.setDescription("Event Description");
        eventRequest.setDate(LocalDate.parse("2025-03-17"));
        eventRequest.setLocation("Location");
        eventRequest.setCost(100.0);
        eventRequest.setType(EventType.WORKSHOP);
        eventRequest.setMaxParticipants(100);

        String imagePath = "path/to/image.jpg";
        Event event = new Event();
        EventResponseVM response = new EventResponseVM();

        when(fileStorageService.saveFile(file)).thenReturn(imagePath);
        when(eventMapper.toEntity(eventRequest)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.toDto(event)).thenReturn(response);

        EventResponseVM result = eventService.saveEvent(eventRequest, file);

        assertEquals(response, result);
        assertEquals(imagePath, event.getImagePath());
        assertFalse(event.getIsPublished());
        verify(eventRepository).save(event);
    }

    @Test
    void saveEvent_WhenNoFileProvided_SavesEventWithoutImagePath() throws IOException {
        CreateEventRequestDto eventRequest = new CreateEventRequestDto();
        eventRequest.setTitle("Test Event");
        eventRequest.setDescription("Event Description");
        eventRequest.setDate(LocalDate.parse("2025-03-17"));
        eventRequest.setLocation("Location");
        eventRequest.setCost(100.0);
        eventRequest.setType(EventType.PITCH_EVENT);
        eventRequest.setMaxParticipants(100);

        Event event = new Event();
        EventResponseVM response = new EventResponseVM();

        when(fileStorageService.saveFile(null)).thenReturn(null);
        when(eventMapper.toEntity(eventRequest)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.toDto(event)).thenReturn(response);

        EventResponseVM result = eventService.saveEvent(eventRequest, null);

        assertEquals(response, result);
        assertNull(event.getImagePath());
        assertFalse(event.getIsPublished());
        verify(eventRepository).save(event);
    }

    @Test
    void getEventById_WhenEventExists_ReturnsEvent() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        EventResponseVM response = new EventResponseVM();

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventMapper.toDto(event)).thenReturn(response);

        EventResponseVM result = eventService.getEventById(eventId);

        assertEquals(response, result);
    }

    @Test
    void getEventById_WhenEventDoesNotExist_ThrowsEventNotFoundException() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.getEventById(eventId));
    }

    @Test
    void updateEvent_WhenEventExists_UpdatesEvent() {
        Long eventId = 1L;
        CreateEventRequestDto eventRequest = new CreateEventRequestDto();
        eventRequest.setTitle("Updated Event");
        eventRequest.setDescription("Updated Description");
        eventRequest.setDate(LocalDate.parse("2025-03-17"));
        eventRequest.setLocation("Updated Location");
        eventRequest.setCost(150.0);
        eventRequest.setType(EventType.WORKSHOP);
        eventRequest.setMaxParticipants(200);

        Event existingEvent = new Event();
        existingEvent.setId(eventId);
        existingEvent.setTitle("Old Event");

        Event updatedEvent = new Event();
        updatedEvent.setId(eventId);
        updatedEvent.setTitle("Updated Event");
        EventResponseVM response = new EventResponseVM();

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventMapper.toEntity(eventRequest)).thenReturn(updatedEvent);
        when(eventRepository.save(existingEvent)).thenReturn(updatedEvent);
        when(eventMapper.toDto(updatedEvent)).thenReturn(response);

        EventResponseVM result = eventService.updateEvent(eventRequest, eventId);

        assertEquals(response, result);
        assertEquals("Updated Event", existingEvent.getTitle());
    }

    @Test
    void updateEvent_WhenEventDoesNotExist_ThrowsEventNotFoundException() {
        Long eventId = 1L;
        CreateEventRequestDto eventRequest = new CreateEventRequestDto();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.updateEvent(eventRequest, eventId));
    }

    @Test
    void deleteEvent_WhenEventExists_DeletesEvent() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        eventService.deleteEvent(eventId);

        verify(eventRepository).delete(event);
    }

    @Test
    void deleteEvent_WhenEventDoesNotExist_ThrowsEventNotFoundException() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.deleteEvent(eventId));
    }

    @Test
    void getEvents_ReturnsPublishedEvents() {
        Event event1 = new Event();
        Event event2 = new Event();
        List<Event> events = Arrays.asList(event1, event2);
        EventResponseVM response1 = new EventResponseVM();
        EventResponseVM response2 = new EventResponseVM();

        when(eventRepository.findPublishedEvents()).thenReturn(events);
        when(eventMapper.toDtoList(events)).thenReturn(Arrays.asList(response1, response2));

        List<EventResponseVM> result = eventService.getEvents();

        assertEquals(2, result.size());
        assertEquals(response1, result.get(0));
        assertEquals(response2, result.get(1));
    }

    @Test
    void updateStatus_WhenEventExists_UpdatesStatusAndNotifies() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setIsPublished(false);
        EventResponseVM response = new EventResponseVM();

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventMapper.toDto(event)).thenReturn(response);

        EventResponseVM result = eventService.updateStatus(eventId);

        assertTrue(event.getIsPublished());
        verify(webSocketNotificationService).sendEventPublishedNotification(event.getTitle());
        assertEquals(response, result);
    }

    @Test
    void updateStatus_WhenEventDoesNotExist_ThrowsEventNotFoundException() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.updateStatus(eventId));
    }
}

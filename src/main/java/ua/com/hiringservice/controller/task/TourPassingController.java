package ua.com.hiringservice.controller.task;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.hiringservice.model.dto.KeycloakUserDto;
import ua.com.hiringservice.model.dto.tour.TourPassingDto;
import ua.com.hiringservice.service.task.TourPassingService;

/**
 * Controller for managing tour passing operations.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-31
 */
@RestController
@RequestMapping("/api/v1/tour-passing")
@AllArgsConstructor
public class TourPassingController {
  private final TourPassingService tourPassingService;

  @PostMapping("/start")
  public ResponseEntity<TourPassingDto> startTaskPassing(@RequestParam final UUID tourId) {
    final TourPassingDto tourPassingDto = tourPassingService.startTourPassing(tourId);
    return ResponseEntity.ok().body(tourPassingDto);
  }

  @PutMapping("/update")
  public ResponseEntity<TourPassingDto> updateTaskPassing(@RequestParam final UUID tourId) {
    final TourPassingDto tourPassingDto = tourPassingService.updateTourPassing(tourId);
    return ResponseEntity.ok().body(tourPassingDto);
  }

  // this is for applicant
  @GetMapping
  public ResponseEntity<TourPassingDto> getTourPassingByTourIdForCurrentUser(
      @RequestParam("tourId") UUID tourId) {
    return ResponseEntity.ok(tourPassingService.findTourPassingByTourId(tourId));
  }

  // this is for recruiter
  @GetMapping("/all-users-finished-tour")
  public ResponseEntity<List<KeycloakUserDto>> getAllUsersFinishedTourByTourId(
      @RequestParam("tourId") UUID tourId) {
    return ResponseEntity.ok(tourPassingService.findAllUsersByTourIdFinishedTour(tourId));
  }
}

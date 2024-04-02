package ua.com.hiringservice.controller.tour;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.hiringservice.model.dto.tour.TourDto;
import ua.com.hiringservice.model.dto.tour.TourTaskOrderDto;
import ua.com.hiringservice.model.dto.tour.TourTaskRequestDto;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.tour.Tour;
import ua.com.hiringservice.service.TourService;
import ua.com.hiringservice.util.swagger.TourOpenApi;

/**
 * RESTful controller for managing {@link Tour}-related operations through API endpoints. Handles
 * requests related to the {@link Tour} entity and its associated questions.
 *
 * @author Lev Kiriienko
 */
@RestController
@RequestMapping("/api/v1/tour")
@AllArgsConstructor
public class TourController implements TourOpenApi {

  private final TourService tourService;

  /**
   * Creates a new {@link Tour} based on the provided {@link TourDto}.
   *
   * @param tourDto DTO containing information to create the {@link Tour}.
   * @return ResponseEntity containing the DTO representation of the created {@link Tour}.
   */
  @Override
  @PostMapping
  public ResponseEntity<TourDto> createTour(@Valid @RequestBody TourDto tourDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(tourService.createTour(tourDto));
  }

  @Override
  @GetMapping
  public ResponseEntity<Page<TourDto>> getAllTours(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(tourService.findAllTours(pageable));
  }

  /**
   * Retrieves a specific {@link Tour} by its unique identifier.
   *
   * @param tourId The unique identifier of the {@link Tour}.
   * @return ResponseEntity containing the DTO representation of the {@link Tour}.
   */
  @Override
  @GetMapping("/{tourId}")
  public ResponseEntity<TourDto> getTourById(@PathVariable UUID tourId) {
    return ResponseEntity.status(HttpStatus.OK).body(tourService.getTourById(tourId));
  }

  @Override
  @PutMapping("/{tourId}/add-task")
  public ResponseEntity<TourDto> addTaskToTour(
      @PathVariable UUID tourId, @Valid @RequestBody TourTaskRequestDto requestDto) {
    return ResponseEntity.status(HttpStatus.OK).body(tourService.addTaskToTour(tourId, requestDto));
  }

  @Override
  @PutMapping("/{tourId}/remove-task")
  public ResponseEntity<TourDto> removeTaskFromTour(
      @PathVariable UUID tourId, @RequestParam("tourTaskId") UUID tourTaskId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(tourService.removeTaskFromTour(tourId, tourTaskId));
  }

  /**
   * Changes the {@link Task}s order in the specified {@link Tour}.
   *
   * @param tourId The unique identifier of the {@link Tour} we want to update.
   * @param orderDtos dto of the {@link Tour} with updated {@link Task}s order.
   * @return ResponseEntity containing the DTO representation of the {@link Tour}.
   */
  @Override
  @PatchMapping("/{tourId}/change-task-order")
  public ResponseEntity<TourDto> changeTaskOrder(
      @PathVariable UUID tourId, @Valid @RequestBody List<TourTaskOrderDto> orderDtos) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(tourService.changeTaskOrder(tourId, orderDtos));
  }

  /**
   * Deletes the {@link Tour}.
   *
   * @param tourId The unique identifier of the {@link Tour} we want to delete.
   * @return ResponseEntity containing nothing since we return NO_CONTENT here.
   */
  @Override
  @DeleteMapping("/{tourId}")
  public ResponseEntity<Void> deleteTour(@PathVariable UUID tourId) {
    tourService.deleteTour(tourId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}

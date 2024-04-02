package ua.com.hiringservice.util.swagger;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.hiringservice.model.dto.tour.TourDto;
import ua.com.hiringservice.model.dto.tour.TourTaskOrderDto;
import ua.com.hiringservice.model.dto.tour.TourTaskRequestDto;
import ua.com.hiringservice.model.entity.tour.Tour;

/**
 * OpenAPI specification for {@link Tour} operations.
 *
 * @author Lev Kiriienko.
 */
@Tag(name = "Tour", description = "API for operations with Tour")
public interface TourOpenApi {

  @PostMapping
  ResponseEntity<TourDto> createTour(@RequestBody TourDto dto);

  @GetMapping
  ResponseEntity<Page<TourDto>> getAllTours(@PageableDefault Pageable pageable);

  @GetMapping("/{tourId}")
  ResponseEntity<TourDto> getTourById(@PathVariable UUID tourId);

  @PutMapping("/{tourId}/add-task")
  ResponseEntity<TourDto> addTaskToTour(
      @PathVariable UUID tourId, @RequestBody TourTaskRequestDto requestDto);

  @PutMapping("/{tourId}/remove-task")
  ResponseEntity<TourDto> removeTaskFromTour(@PathVariable UUID tourId, @RequestParam UUID taskId);

  @PatchMapping("/{tourId}/change-task-order")
  ResponseEntity<TourDto> changeTaskOrder(
      @PathVariable UUID tourId, @Valid @RequestBody List<TourTaskOrderDto> orderDto);

  @DeleteMapping("/{tourId}")
  ResponseEntity<Void> deleteTour(@PathVariable UUID tourId);
}

package ua.com.hiringservice.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ua.com.hiringservice.exception.VacancyNotFoundException;
import ua.com.hiringservice.model.dto.vacancy.DepartmentDto;
import ua.com.hiringservice.model.dto.vacancy.SpecializationDto;
import ua.com.hiringservice.model.dto.vacancy.VacancyDescriptionDto;
import ua.com.hiringservice.model.dto.vacancy.VacancyDto;
import ua.com.hiringservice.model.entity.vacancy.Vacancy;
import ua.com.hiringservice.model.entity.vacancy.VacancyDescription;
import ua.com.hiringservice.repository.VacancyRepository;
import ua.com.hiringservice.service.DepartmentService;
import ua.com.hiringservice.service.KeycloakService;
import ua.com.hiringservice.service.SpecializationService;
import ua.com.hiringservice.service.VacancyDescriptionService;
import ua.com.hiringservice.util.mapper.VacancyMapper;

@SuppressWarnings("PMD")
@ExtendWith(MockitoExtension.class)
// todo: rewrite this class with IntegrationTestBase
class VacancyServiceImplTestIT {

  @Mock private VacancyRepository vacancyRepository;
  @Mock private VacancyMapper vacancyMapper;
  @Mock private VacancyDescriptionService vacancyDescriptionService;
  @Mock private SpecializationService specializationService;
  @Mock private DepartmentService departmentService;
  @Mock private KeycloakService keycloakService;
  @InjectMocks private VacancyServiceImpl vacancyService;

  //
  //  @Test
  //  void
  // updateVacancy_shouldReturnVacancyResponseDto_whenValidVacancyIdAndValidVacancyDetailedDto() {
  //    final UUID vacancyId = UUID.randomUUID();
  //    final VacancyDto vacancyDto = new VacancyDto();
  //    final Vacancy existingVacancy = new Vacancy();
  //    final Vacancy mergedVacancy = new Vacancy();
  //    final VacancyDto expectedDto = new VacancyDto();
  //
  //    when(vacancyRepository.findById(vacancyId)).thenReturn(Optional.of(existingVacancy));
  //    doNothing().when(vacancyMapper).mergeWithVacancy(vacancyDto, existingVacancy);
  //    when(vacancyRepository.save(existingVacancy)).thenReturn(mergedVacancy);
  //    when(vacancyMapper.toDto(mergedVacancy)).thenReturn(expectedDto);
  //
  //    final VacancyDto result = vacancyService.update(vacancyId, vacancyDto);
  //
  //    assertNotNull(result);
  //    assertEquals(expectedDto, result);
  //
  //    verify(vacancyRepository).findById(vacancyId);
  //    verify(vacancyMapper).mergeWithVacancy(vacancyDto, existingVacancy);
  //    verify(vacancyRepository).save(existingVacancy);
  //    verify(vacancyMapper).toDto(mergedVacancy);
  //
  //    verifyNoMoreInteractions(vacancyRepository, vacancyMapper);
  //  }
  //
  //  @Test
  //  void
  //
  // updateVacancy_shouldTrowVacancyNotFoundException_whenVacancyNotFoundAndValidVacancyDetailedDto() {
  //    final VacancyDto updatedVacancyDto = new VacancyDto();
  //    final UUID id = UUID.randomUUID();
  //
  //    when(vacancyRepository.findById(id)).thenReturn(Optional.empty());
  //
  //    Assertions.assertThatThrownBy(() -> vacancyService.update(id, updatedVacancyDto))
  //        .isInstanceOf(VacancyNotFoundException.class)
  //        .hasMessageContaining("Vacancy entity with id: %s not found.", id);
  //
  //    verify(vacancyRepository).findById(id);
  //
  //    verifyNoMoreInteractions(vacancyRepository, vacancyMapper);
  //  }
  //
  //  @Test
  //  void
  //
  // updateVacancy_shouldThrowVacancyNotFoundException_whenVacancyIdIsNullAndValidVacancyDetailedDto() {
  //    final VacancyDto updatedVacancyDto = new VacancyDto();
  //    final UUID id = null;
  //
  //    when(vacancyRepository.findById(id)).thenReturn(Optional.empty());
  //
  //    Assertions.assertThatThrownBy(() -> vacancyService.update(id, updatedVacancyDto))
  //        .isInstanceOf(VacancyNotFoundException.class)
  //        .hasMessageContaining("Vacancy entity with id: %s not found.", id);
  //
  //    verify(vacancyRepository).findById(id);
  //
  //    verifyNoMoreInteractions(vacancyRepository, vacancyMapper);
  //  }
  //
  //  @Test
  //  void deleteVacancy_shouldDeleteVacancyById_whenValidVacancyId() {
  //    final UUID vacancyId = UUID.randomUUID();
  //    doNothing().when(vacancyRepository).deleteById(vacancyId);
  //
  //    vacancyService.delete(vacancyId);
  //
  //    verify(vacancyRepository).deleteById(vacancyId);
  //  }
  //
  //  @Test
  //  void
  //
  // getAllVacancies_shouldReturnPageWithOneVacancy_whenOneVacancyHasNoExpirationDateAndOneVacancyHasBeenExpired() {
  //    final int page = 0;
  //    final int size = 10;
  //
  //    Vacancy vacancyWithNoExpirationDate = new Vacancy();
  //    Vacancy expiredVacancy = new Vacancy();
  //    expiredVacancy.setDueDate(LocalDate.of(2020, 12, 14));
  //
  //    final Page<Vacancy> vacancyPage =
  //        new PageImpl<>(List.of(vacancyWithNoExpirationDate, expiredVacancy));
  //
  //    when(vacancyRepository.findAll(any(Pageable.class))).thenReturn(vacancyPage);
  //
  //    final Page<VacancyDto> result = vacancyService.getAll(page, size);
  //
  //    assertEquals(1, result.getTotalElements());
  //  }
  //
  //  @Test
  //  void
  // getAllVacancies_shouldReturnPageWithTwoVacancies_whenTwoVacanciesHasNoExpirationDatePages() {
  //    final int page = 0;
  //    final int size = 10;
  //
  //    Vacancy vacancyWithNoExpirationDate1 = new Vacancy();
  //    Vacancy vacancyWithNoExpirationDate2 = new Vacancy();
  //
  //    final Page<Vacancy> vacancyPage =
  //        new PageImpl<>(List.of(vacancyWithNoExpirationDate1, vacancyWithNoExpirationDate2));
  //
  //    when(vacancyRepository.findAll(any(Pageable.class))).thenReturn(vacancyPage);
  //
  //    final Page<VacancyDto> result = vacancyService.getAll(page, size);
  //
  //    assertEquals(2, result.getTotalElements());
  //  }
  //
  //  @Test
  //  void
  //
  // getAllVacancies_shouldReturnPageWithTwoVacancies_whenOneVacancyHasNoExpirationDateAndOneVacancyHasNotExpiredAndOneVacancyHasBeenExpired() {
  //    final int page = 0;
  //    final int size = 10;
  //
  //    Vacancy vacancyWithNoExpirationDate = new Vacancy();
  //    Vacancy expiredVacancy = new Vacancy();
  //    expiredVacancy.setDueDate(LocalDate.of(2020, 12, 14));
  //    Vacancy notExpiredVacancy = new Vacancy();
  //    notExpiredVacancy.setDueDate(LocalDate.of(3024, 2, 14));
  //
  //    final Page<Vacancy> vacancyPage =
  //        new PageImpl<>(List.of(vacancyWithNoExpirationDate, expiredVacancy, notExpiredVacancy));
  //
  //    when(vacancyRepository.findAll(any(Pageable.class))).thenReturn(vacancyPage);
  //
  //    final Page<VacancyDto> result = vacancyService.getAll(page, size);
  //
  //    assertEquals(2, result.getTotalElements());
  //  }
  //
  //  @Test
  //  void getAllVacancies_shouldReturnZeroPageOfAllVacancies_whenTwoInvalidVacancyPage() {
  //    final int page = 0;
  //    final int size = 10;
  //
  //    Vacancy expiredVacancy1 = new Vacancy();
  //    expiredVacancy1.setDueDate(LocalDate.of(2019, 5, 20));
  //    Vacancy expiredVacancy2 = new Vacancy();
  //    expiredVacancy2.setDueDate(LocalDate.of(2019, 5, 20));
  //
  //    final Page<Vacancy> vacancyPage = new PageImpl<>(List.of(expiredVacancy1, expiredVacancy2));
  //
  //    when(vacancyRepository.findAll(any(Pageable.class))).thenReturn(vacancyPage);
  //
  //    final Page<VacancyDto> result = vacancyService.getAll(page, size);
  //
  //    assertEquals(0, result.getTotalElements());
  //  }
  //
  //  @Test
  //  void
  //
  // getAllVacancies_shouldThrowDataValidationException_whenRepositoryThrowsDataValidationException() {
  //    when(vacancyRepository.findAll(any(Pageable.class)))
  //        .thenThrow(new DataValidationException("Database error"));
  //
  //    final int page = 0;
  //    final int size = 10;
  //
  //    assertThrows(DataValidationException.class, () -> vacancyService.getAll(page, size));
  //
  //    verify(vacancyRepository).findAll(any(Pageable.class));
  //
  //    verify(vacancyMapper, never()).toDto(any());
  //
  //    verifyNoMoreInteractions(vacancyRepository, vacancyMapper);
  //  }
  //
  @Test
  void getVacancyDescriptionById_shouldReturnVacancyDescription_whenValidVacancyId() {
    final UUID id = UUID.randomUUID();

    final Vacancy vacancy = new Vacancy();
    vacancy.setDescriptionId("65dc9bd49abbca0dfa6eae54");
    when(vacancyRepository.findById(id)).thenReturn(Optional.of(vacancy));

    VacancyDescriptionDto vacancyDescriptionDto = new VacancyDescriptionDto();
    when(vacancyDescriptionService.getById(anyString())).thenReturn(vacancyDescriptionDto);

    final VacancyDescriptionDto result = vacancyService.getDescriptionById(id);

    assertNotNull(result);

    verify(vacancyRepository).findById(id);
    verify(vacancyDescriptionService).getById(anyString());
  }

  @Test
  void getVacancyDescriptionById_shouldThrowVacancyNotFoundException_whenVacancyIdNotValid() {
    final UUID id = UUID.randomUUID();

    when(vacancyRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(VacancyNotFoundException.class, () -> vacancyService.getDescriptionById(id));

    verify(vacancyRepository).findById(id);
  }

  @Test
  void createVacancy_shouldCreateVacancy_whenValidVacancyDto() {
    final UUID recruiterId = UUID.randomUUID();
    when(keycloakService.getKeycloakIdOfUserFromSecurityContext()).thenReturn(recruiterId);

    final VacancyDescription vacancyDescription = new VacancyDescription();
    final String descriptionId = "65dc9bd49abbca0dfa6eae54";
    vacancyDescription.setId(descriptionId);
    final MultipartFile file = mock(MultipartFile.class);
    when(vacancyDescriptionService.save(file)).thenReturn(vacancyDescription);

    final DepartmentDto departmentDto = new DepartmentDto();
    final String departmentName = "Sales";
    departmentDto.setName(departmentName);
    when(departmentService.getByName(anyString())).thenReturn(departmentDto);

    final SpecializationDto specializationDto = new SpecializationDto();
    specializationDto.setName("Java");
    when(specializationService.getByName(anyString())).thenReturn(specializationDto);

    final Vacancy vacancy = new Vacancy();
    final VacancyDto vacancyDto = new VacancyDto();
    vacancyDto.setDepartment(departmentDto);
    vacancyDto.setSpecialization(specializationDto);
    when(vacancyMapper.toEntity(vacancyDto)).thenReturn(vacancy);

    final Vacancy savedVacancy = new Vacancy();
    when(vacancyRepository.save(vacancy)).thenReturn(savedVacancy);

    final VacancyDto savedVacancyDto = new VacancyDto();
    when(vacancyMapper.toDto(vacancy)).thenReturn(savedVacancyDto);

    // running method
    final VacancyDto result = vacancyService.create(vacancyDto, file);

    // asserting results
    assertNotNull(result);
    verify(keycloakService).getKeycloakIdOfUserFromSecurityContext();
    verify(vacancyDescriptionService).save(any(MultipartFile.class));
    verify(departmentService).getByName(anyString());
    verify(specializationService).getByName(anyString());
    verify(vacancyMapper).toEntity(vacancyDto);
    verify(vacancyRepository).save(vacancy);
    verify(vacancyMapper).toDto(vacancy);
  }

  @Test
  void createVacancy_shouldThrowNullPointerException_whenVacancyDetailedDtoIsNull() {
    final VacancyDto vacancyDto = null;
    final MultipartFile file = mock(MultipartFile.class);

    when(keycloakService.getKeycloakIdOfUserFromSecurityContext()).thenReturn(UUID.randomUUID());

    assertThrows(NullPointerException.class, () -> vacancyService.create(vacancyDto, file));

    verifyNoInteractions(vacancyRepository);
  }

  @Test
  void createVacancy_shouldCreateVacancy_whenFileIsNull() {
    final UUID recruiterId = UUID.randomUUID();
    when(keycloakService.getKeycloakIdOfUserFromSecurityContext()).thenReturn(recruiterId);

    final MultipartFile file = null;

    final DepartmentDto departmentDto = new DepartmentDto();
    final String departmentName = "Sales";
    departmentDto.setName(departmentName);
    when(departmentService.getByName(anyString())).thenReturn(departmentDto);

    final SpecializationDto specializationDto = new SpecializationDto();
    specializationDto.setName("Java");
    when(specializationService.getByName(anyString())).thenReturn(specializationDto);

    final Vacancy vacancy = new Vacancy();
    final VacancyDto vacancyDto = new VacancyDto();
    vacancyDto.setDepartment(departmentDto);
    vacancyDto.setSpecialization(specializationDto);
    when(vacancyMapper.toEntity(vacancyDto)).thenReturn(vacancy);

    final Vacancy savedVacancy = new Vacancy();
    when(vacancyRepository.save(vacancy)).thenReturn(savedVacancy);

    final VacancyDto savedVacancyDto = new VacancyDto();
    when(vacancyMapper.toDto(vacancy)).thenReturn(savedVacancyDto);

    // running method
    final VacancyDto result = vacancyService.create(vacancyDto, file);

    // asserting results
    assertNotNull(result);
    verify(keycloakService).getKeycloakIdOfUserFromSecurityContext();
    verifyNoInteractions(vacancyDescriptionService);
    verify(departmentService).getByName(anyString());
    verify(specializationService).getByName(anyString());
    verify(vacancyMapper).toEntity(vacancyDto);
    verify(vacancyRepository).save(vacancy);
    verify(vacancyMapper).toDto(vacancy);
  }

  //
  //  @Test
  //  void getVacancyById_shouldReturnVacancyById_whenValidVacancyId() {
  //    final Vacancy vacancy = new Vacancy();
  //    final UUID id = UUID.randomUUID();
  //
  //    when(vacancyRepository.findById(id)).thenReturn(Optional.of(vacancy));
  //    when(vacancyMapper.toDto(any(Vacancy.class))).thenReturn(new VacancyDto());
  //
  //    final VacancyDto result = vacancyService.getById(id);
  //
  //    assertNotNull(result);
  //    verify(vacancyRepository).findById(id);
  //  }
  //
  //  @Test
  //  void getVacancyById_shouldThrowVacancyNotFoundException_whenVacancyNotExist() {
  //    final UUID id = UUID.randomUUID();
  //    when(vacancyRepository.findById(id)).thenReturn(Optional.empty());
  //
  //    assertThrows(VacancyNotFoundException.class, () -> vacancyService.getById(id));
  //
  //    verify(vacancyRepository).findById(id);
  //  }
}

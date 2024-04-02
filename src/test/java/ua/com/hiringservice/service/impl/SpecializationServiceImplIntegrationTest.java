package ua.com.hiringservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ua.com.hiringservice.IntegrationTestBase;
import ua.com.hiringservice.exception.SpecializationIconBySpecializationIdNotFoundException;
import ua.com.hiringservice.exception.SpecializationNameAlreadyExistException;
import ua.com.hiringservice.exception.SpecializationNotFoundException;
import ua.com.hiringservice.model.dto.vacancy.SpecializationCreateDto;
import ua.com.hiringservice.model.dto.vacancy.SpecializationDto;
import ua.com.hiringservice.model.dto.vacancy.SpecializationIconDto;
import ua.com.hiringservice.model.entity.vacancy.Vacancy;
import ua.com.hiringservice.repository.VacancyRepository;
import ua.com.hiringservice.service.SpecializationService;
import ua.com.hiringservice.util.mapper.SpecializationMapper;

/**
 * Integration tests for {@link SpecializationServiceImpl}. These tests verify the behavior of the
 * {@link SpecializationServiceImpl} class by testing its methods. The tests are performed in an
 * integrated environment.
 *
 * @author Artem Dreveckyi
 * @version 1.0
 * @since 2024-01-25
 */
@SuppressWarnings("PMD")
@Sql(scripts = {"classpath:/db/specialization-test-data.sql"})
class SpecializationServiceImplIntegrationTest extends IntegrationTestBase {

  @Autowired private SpecializationService specializationService;
  @Autowired private VacancyRepository vacancyRepository;
  @Autowired private SpecializationMapper specializationMapper;

  private static final String EXISTING_SPECIALIZATION_NAME = "Java";
  private final byte[] icon = "icon".getBytes();

  @Test
  void getAll_shouldReturnListOffSpecializations_whenGetAllSpecializations() {
    final List<SpecializationDto> specializations = specializationService.getAll();
    assertEquals(2, specializations.size(), "The number of retrieved specializations should be 2");
  }

  @Test
  void
      UpdateIconByIdAndGetSpecializationById_shouldReturnExpectedSpecializationWithIcon_whenUpdateIconAndGetSpecializationById()
          throws IOException {
    final Long specializationId = specializationService.getAll().get(0).getId();
    final SpecializationDto updatedSpecializationDto =
        specializationService.updateIconById(specializationId, new MockMultipartFile("icon", icon));

    final SpecializationDto actualSpecializationDto =
        specializationService.getById(specializationId);

    final SpecializationIconDto expectedIconFile = updatedSpecializationDto.getIconFile();
    final String actualSpecializationName = actualSpecializationDto.getName();
    final SpecializationIconDto actualIconFile = actualSpecializationDto.getIconFile();

    assertEquals(EXISTING_SPECIALIZATION_NAME, actualSpecializationName);
    assertEquals(expectedIconFile.getIconFile(), actualIconFile.getIconFile());
  }

  @Test
  void getByName_shouldReturnExpectedSpecialization_whenGetSpecializationByName() {
    final SpecializationDto specializationDtoFromDB =
        specializationService.getByName(EXISTING_SPECIALIZATION_NAME);
    final String actualSpecializationName = specializationDtoFromDB.getName();
    assertEquals(EXISTING_SPECIALIZATION_NAME, actualSpecializationName);
  }

  @Test
  void getByName_shouldThrowNotFoundException_whenGetSpecializationByNonExistingName() {
    assertThrows(
        SpecializationNotFoundException.class,
        () -> specializationService.getByName("NonExistingName"),
        "Should throw SpecializationNotFoundException");
  }

  @Test
  void getById_shouldThrowNotFoundException_whenGetSpecializationByNonExistingId() {
    assertThrows(
        SpecializationNotFoundException.class,
        () -> specializationService.getById(1000L),
        "Should throw SpecializationNotFoundException");
  }

  @Test
  void save_shouldSaveNewSpecialization_whenSaveSpecialization() throws IOException {
    final MockMultipartFile iconFile = new MockMultipartFile("iconFile", "iconFile".getBytes());
    specializationService.save(new SpecializationCreateDto("C#"), iconFile);
    final int actualSize = specializationService.getAll().size();
    assertEquals(3, actualSize, "The number of retrieved specializations should be 3");
  }

  @Test
  void save_shouldThrowNameAlreadyExistException_whenSaveWithExistingName() {
    final MockMultipartFile iconFile = new MockMultipartFile("iconFile", "iconFile".getBytes());
    assertThrows(
        SpecializationNameAlreadyExistException.class,
        () ->
            specializationService.save(
                new SpecializationCreateDto(EXISTING_SPECIALIZATION_NAME), iconFile),
        "Should throw SpecializationNameAlreadyExistException");
  }

  @Test
  void delete_shouldDeleteSpecificSpecialization_whenDeleteSpecialization() {
    final List<SpecializationDto> specializations = specializationService.getAll();
    final Long specializationId = specializations.get(0).getId();
    specializationService.delete(specializationId);
    final int actualSize = specializationService.getAll().size();
    assertEquals(1, actualSize, "The number of retrieved specializations should be 1");
  }

  @Test
  @Transactional
  void
      manyToOneRelationshipVacancySpecialization_shouldReturnSpecialization_whenSetSpecializationToVacancy() {
    final Vacancy vacancy = vacancyRepository.findAll().get(0);
    assertNull(vacancy.getSpecialization(), "There is no specialization before it's set");

    final SpecializationDto specialization = specializationService.getAll().get(0);
    vacancy.setSpecialization(specializationMapper.toEntity(specialization));
    vacancyRepository.save(vacancy);
    final String actualSpecializationName =
        vacancyRepository.findAll().get(0).getSpecialization().getName();
    assertEquals(
        EXISTING_SPECIALIZATION_NAME,
        actualSpecializationName,
        "Should be specialization after it's set");
  }

  @Test
  void getIconById_shouldReturnExpectedIcon_whenGetIconBySpecializationId() throws IOException {
    final Long specializationId = specializationService.getAll().get(0).getId();
    final SpecializationDto updatedSpecializationDto =
        specializationService.updateIconById(specializationId, new MockMultipartFile("icon", icon));

    final SpecializationIconDto specializationIconDto =
        specializationService.getIconById(updatedSpecializationDto.getId());

    final SpecializationIconDto expectedIconFile = updatedSpecializationDto.getIconFile();
    final Binary actualIconFile = specializationIconDto.getIconFile();

    assertEquals(expectedIconFile.getIconFile(), actualIconFile);
  }

  @Test
  void
      getIconById_shouldThrowException_whenGetIconBySpecializationIdAndSpecializationDontHaveIcon() {
    final Long specializationId = specializationService.getAll().get(0).getId();
    assertThrows(
        SpecializationIconBySpecializationIdNotFoundException.class,
        () -> specializationService.getIconById(specializationId),
        "Should throw SpecializationIconBySpecializationIdNotFoundException");
  }
}

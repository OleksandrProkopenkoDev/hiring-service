package ua.com.hiringservice.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.hiringservice.exception.DepartmentNotFoundException;
import ua.com.hiringservice.model.dto.vacancy.DepartmentDto;
import ua.com.hiringservice.model.entity.vacancy.Department;
import ua.com.hiringservice.repository.DepartmentRepository;
import ua.com.hiringservice.util.mapper.DepartmentMapper;

@SuppressWarnings("PMD")
@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTestIT {
  @Mock private DepartmentRepository departmentRepository;
  @Mock private DepartmentMapper departmentMapper;
  @InjectMocks private DepartmentServiceImpl departmentService;

  private final String DEPARTMENT_NAME_1 = "Sales";
  private final String DEPARTMENT_NAME_2 = "Research";
  private final String DEPARTMENT_NAME_NEW = "Univercity";

  //
  @Test
  void getAll_shouldReturnListOfDepartments() {
    final List<Department> departments =
        List.of(new Department(1L, DEPARTMENT_NAME_1), new Department(2L, DEPARTMENT_NAME_2));
    when(departmentRepository.findAll()).thenReturn(departments);
    final List<DepartmentDto> result = departmentService.getAll();
    assertEquals(2, result.size());
    verify(departmentRepository).findAll();
  }

  @Test
  void getByName_shouldReturnDepartment_whenValidName() {
    Department department = new Department(1L, DEPARTMENT_NAME_1);
    when(departmentRepository.findByName(anyString())).thenReturn(Optional.of(department));

    DepartmentDto departmentDto = new DepartmentDto(1L, DEPARTMENT_NAME_1);
    when(departmentMapper.toDto(department)).thenReturn(departmentDto);
    DepartmentDto result = departmentService.getByName(DEPARTMENT_NAME_1);

    assertEquals(DEPARTMENT_NAME_1, result.getName());

    verify(departmentRepository).findByName(DEPARTMENT_NAME_1);
    verify(departmentMapper).toDto(department);
  }

  @Test
  void getByName_shouldThrowException_whenNotValidName() {
    Department department = new Department(1L, DEPARTMENT_NAME_1);
    when(departmentRepository.findByName(anyString()))
        .thenThrow(new DepartmentNotFoundException(DEPARTMENT_NAME_1));

    assertThrows(
        DepartmentNotFoundException.class,
        () -> departmentRepository.findByName(DEPARTMENT_NAME_1));

    verify(departmentRepository).findByName(DEPARTMENT_NAME_1);
    verifyNoInteractions(departmentMapper);
  }

  @Test
  void create_shouldReturnDepartmentDto_whenDepartmentValid() {
    DepartmentDto newDepartmentDto = new DepartmentDto(null, DEPARTMENT_NAME_NEW);
    Department newDepartment = new Department(null, DEPARTMENT_NAME_NEW);
    when(departmentMapper.toEntity(newDepartmentDto)).thenReturn(newDepartment);

    Department savedDepartment = new Department(3L, DEPARTMENT_NAME_NEW);
    when(departmentRepository.save(newDepartment)).thenReturn(savedDepartment);

    DepartmentDto savedDepartmentDto = new DepartmentDto(3L, DEPARTMENT_NAME_NEW);
    when(departmentMapper.toDto(savedDepartment)).thenReturn(savedDepartmentDto);

    DepartmentDto result = departmentService.create(newDepartmentDto);

    assertEquals(DEPARTMENT_NAME_NEW, result.getName());

    verify(departmentMapper).toEntity(newDepartmentDto);
    verify(departmentRepository).save(newDepartment);
    verify(departmentMapper).toDto(savedDepartment);
  }

  @Test
  void create_shouldThrowNullPointerException_whenDepartmentIsNull() {
    DepartmentDto nullDepartmentDto = null;
    Department nullDepartment = null;
    when(departmentMapper.toEntity(nullDepartmentDto)).thenReturn(nullDepartment);

    when(departmentRepository.save(null)).thenThrow(new IllegalArgumentException());

    assertThrows(IllegalArgumentException.class, () -> departmentService.create(nullDepartmentDto));

    verify(departmentMapper).toEntity(null);
    verify(departmentRepository).save(nullDepartment);
  }
}

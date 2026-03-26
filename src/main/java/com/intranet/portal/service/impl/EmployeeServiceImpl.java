package com.intranet.portal.service.impl;

import com.intranet.portal.dto.birthday.BirthdayCelebrantResponse;
import com.intranet.portal.dto.employee.EmployeeCreateRequest;
import com.intranet.portal.dto.employee.EmployeeResponse;
import com.intranet.portal.dto.employee.EmployeeUpdateRequest;
import com.intranet.portal.entity.Department;
import com.intranet.portal.entity.Employee;
import com.intranet.portal.entity.Position;
import com.intranet.portal.exception.BadRequestException;
import com.intranet.portal.exception.NotFoundException;
import com.intranet.portal.mapper.EmployeeMapper;
import com.intranet.portal.repository.DepartmentRepository;
import com.intranet.portal.repository.EmployeeRepository;
import com.intranet.portal.repository.PositionRepository;
import com.intranet.portal.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getEmployees() {
        log.info("Fetching all employees");

        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        log.info("Fetching employee by id: {}", id);

        Employee employee = employeeRepository.findByIdWithDepartmentAndPosition(id)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + id));

        return employeeMapper.toResponse(employee);
    }

    @Override
    public Long addEmployee(EmployeeCreateRequest request) {
        log.info("Creating employee with email: {}", request.email());

        if (request.email() != null && employeeRepository.existsByEmail(request.email())) {
            log.warn("Employee creation failed. Email already exists: {}", request.email());
            throw new BadRequestException("Email already exists: " + request.email());
        }

        if (request.cardCode() != null && employeeRepository.existsByCardCode(request.cardCode())) {
            log.warn("Employee creation failed. Card code already exists: {}", request.cardCode());
            throw new BadRequestException("Card code already exists: " + request.cardCode());
        }

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new NotFoundException("Department not found: " + request.departmentId()));

        Position position = positionRepository.findById(request.positionId())
                .orElseThrow(() -> new NotFoundException("Position not found: " + request.positionId()));

        Employee employee = employeeMapper.toEntity(request);
        employee.setDepartment(department);
        employee.setPosition(position);

        employee.setPassword(passwordEncoder.encode(request.password()));

        if (employee.getIsActive() == null) {
            employee.setIsActive(true);
        }

        Employee saved = employeeRepository.save(employee);

        log.info("Employee created successfully with id: {}", saved.getId());
        return saved.getId();
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeUpdateRequest request) {
        log.info("Updating employee with id: {}", id);

        Employee existing = employeeRepository.findByIdWithDepartmentAndPosition(id)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + id));

        if (request.email() != null && !request.email().equals(existing.getEmail())) {
            if (employeeRepository.existsByEmail(request.email())) {
                log.warn("Employee update failed. Email already exists: {}", request.email());
                throw new BadRequestException("Email already exists: " + request.email());
            }
        }

        if (request.cardCode() != null && !request.cardCode().equals(existing.getCardCode())) {
            if (employeeRepository.existsByCardCode(request.cardCode())) {
                log.warn("Employee update failed. Card code already exists: {}", request.cardCode());
                throw new BadRequestException("Card code already exists: " + request.cardCode());
            }
        }

        employeeMapper.updateEntityFromDto(request, existing);

        if (request.departmentId() != null) {
            log.info("Updating department for employee id: {} to department id: {}", id, request.departmentId());

            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new NotFoundException("Department not found: " + request.departmentId()));
            existing.setDepartment(department);
        }

        if (request.positionId() != null) {
            log.info("Updating position for employee id: {} to position id: {}", id, request.positionId());

            Position position = positionRepository.findById(request.positionId())
                    .orElseThrow(() -> new NotFoundException("Position not found: " + request.positionId()));
            existing.setPosition(position);
        }

        if (request.password() != null && !request.password().isBlank()) {
            log.info("Updating password for employee id: {}", id);
            existing.setPassword(passwordEncoder.encode(request.password()));
        }

        Employee saved = employeeRepository.save(existing);

        log.info("Employee updated successfully with id: {}", saved.getId());
        return employeeMapper.toResponse(saved);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);

        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + id));

        employeeRepository.delete(existing);

        log.info("Employee deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateNetSalary(BigDecimal grossSalary) {
        log.info("Calculating net salary for gross salary: {}", grossSalary);

        if (grossSalary == null) {
            return BigDecimal.ZERO;
        }

        if (grossSalary.compareTo(BigDecimal.ZERO) < 0) {
            log.warn("Net salary calculation failed. Gross salary is negative: {}", grossSalary);
            throw new BadRequestException("Gross salary must be >= 0");
        }

        BigDecimal dsmfTax = BigDecimal.ZERO;
        if (grossSalary.compareTo(new BigDecimal("200")) > 0) {
            dsmfTax = new BigDecimal("6")
                    .add(grossSalary.subtract(new BigDecimal("200")).multiply(new BigDecimal("0.1")));
        }

        BigDecimal unemploymentTax = grossSalary.multiply(new BigDecimal("0.005"));

        BigDecimal healthInsuranceTax =
                (grossSalary.compareTo(new BigDecimal("8000")) <= 0)
                        ? grossSalary.multiply(new BigDecimal("0.02"))
                        : new BigDecimal("8000").multiply(new BigDecimal("0.02"));

        BigDecimal totalTax = dsmfTax.add(unemploymentTax).add(healthInsuranceTax);
        BigDecimal netSalary = grossSalary.subtract(totalTax).setScale(2, RoundingMode.HALF_UP);

        log.info("Net salary calculated successfully. Gross: {}, Net: {}", grossSalary, netSalary);
        return netSalary;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BirthdayCelebrantResponse> getBirthdayCelebrantsThisMonth() {
        log.info("Fetching birthday celebrants for current month");

        int month = LocalDate.now().getMonthValue();
        LocalDate today = LocalDate.now();

        return employeeRepository.findAll().stream()
                .filter(e -> e.getBirthDate() != null && e.getBirthDate().getMonthValue() == month)
                .map(e -> {
                    int age = today.getYear() - e.getBirthDate().getYear();

                    String ageCategory =
                            (age % 10 == 0) ? "QIZIL_YUBILEY" :
                                    (age % 10 == 5) ? "GUMUS_YUBILEY" :
                                            "ADI_YAS";

                    String message =
                            switch (ageCategory) {
                                case "QIZIL_YUBILEY" -> "Qızıl yubiley yaş qrupu";
                                case "GUMUS_YUBILEY" -> "Gümüş yubiley yaş qrupu";
                                default -> "Adi yaş qrupu";
                            };

                    return new BirthdayCelebrantResponse(
                            e.getId(),
                            e.getFirstName() + " " + e.getLastName(),
                            e.getBirthDate(),
                            age,
                            ageCategory,
                            message
                    );
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getCurrentUser(String email) {
        Employee employee = employeeRepository.findByEmailWithDepartmentAndPosition(email)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + email));

        return employeeMapper.toResponse(employee);
    }
}
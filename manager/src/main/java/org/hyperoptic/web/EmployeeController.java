package org.hyperoptic.web;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hyperoptic.infrastructure.exceptions.custom.BadRequestException;
import org.hyperoptic.model.employee.EmployeeDtoBasic;
import org.hyperoptic.model.employee.EmployeeDtoUpdate;
import org.hyperoptic.model.employee.EmployeeResponseCreate;
import org.hyperoptic.service.employee.EmployeeService;
import org.hyperoptic.service.search.EmployeeFilters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.hyperoptic.util.Util.validateEmployeeFilters;

@RestController
@RequestMapping(value = "/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDtoBasic>> findAll(Pageable pageable) {
        List<EmployeeDtoBasic> employees = employeeService.findAll(pageable);
        return ResponseEntity.ok(employees);
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<EmployeeResponseCreate> createEmployee(
            @RequestBody @Valid EmployeeDtoBasic employeeDtoBasic) {

        EmployeeResponseCreate employeeResponse = employeeService.create(employeeDtoBasic);
        return ResponseEntity.ok(employeeResponse);
    }

    @RequestMapping(value = "/update/{personalId}", method = RequestMethod.PUT)
    public ResponseEntity<EmployeeDtoBasic> updateEmployee(
            @RequestBody @Valid EmployeeDtoUpdate employeeDtoUpdate,
            @NotNull @PathVariable String personalId) {

        if(!personalId.matches("^\\d+$")) {
            throw new BadRequestException("personal_id must have only digits");
        }

        EmployeeDtoBasic employeeResponse = employeeService.update(employeeDtoUpdate, personalId);
        return ResponseEntity.ok(employeeResponse);
    }

    @RequestMapping(value = "/delete/{personalId}", method = RequestMethod.DELETE)
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "User deleted successfully")})
    public ResponseEntity<?> deleteEmployee(
            @NotNull @PathVariable String personalId) {

        employeeService.delete(personalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDtoBasic>> searchEmploye(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String team,
            @RequestParam(required = false) String lead,
            @RequestParam(required = false, name = "personal-id") String personalId,
            Pageable pageable) {

        EmployeeFilters employeeFilters = new EmployeeFilters(
                name, team, personalId, lead
        );

        if (personalId != null)
            validateEmployeeFilters(employeeFilters);

        List<EmployeeDtoBasic> employeeDtoBasicList = employeeService.search(employeeFilters, pageable);
        return ResponseEntity.ok(employeeDtoBasicList);
    }



}

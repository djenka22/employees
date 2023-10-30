package org.hyperoptic.util;

import org.hyperoptic.infrastructure.exceptions.custom.BadRequestException;
import org.hyperoptic.service.search.EmployeeFilters;

public class Util {
    public static void validateEmployeeFilters(EmployeeFilters employeeFilters) {
        if (!employeeFilters.getPersonalId().matches("^\\d+$") ||
                employeeFilters.getPersonalId().length() != 6)
            throw new BadRequestException("Employee personal id must have 6 digits");
    }
}

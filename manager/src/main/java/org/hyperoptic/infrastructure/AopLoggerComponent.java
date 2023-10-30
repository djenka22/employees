package org.hyperoptic.infrastructure;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hyperoptic.infrastructure.exceptions.custom.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
public class AopLoggerComponent {

    Logger LOGGER = LoggerFactory.getLogger(AopLoggerComponent.class);
    private static final String POINTCUT = "within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)" +
            " || within(org.hyperoptic.service.client.EmployeeClient )" +
            " || within(org.hyperoptic.service.client.TeamClient )" +
            " || within(org.hyperoptic.service.employee.EmployeeServiceAbstract )" +
            " || within(org.hyperoptic.service.employee.EmployeeServiceImpl )" +
            " || within(org.hyperoptic.service.mapper.employee.EmployeeMapper )" +
            " || within(org.hyperoptic.service.mapper.UtilMapper )" +
            " || within(org.hyperoptic.model.employee.EmployeeDtoBasic )";


    @AfterThrowing(pointcut = POINTCUT, throwing = "e")
    public void logAfterException(JoinPoint jp, Exception e) {
        LOGGER.error("Exception during: {} with ex: {}", constructLogMsg(jp),  e.toString());
    }

    private String constructLogMsg(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs()).map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        StringBuilder sb = new StringBuilder("@");
        sb.append(method.getName());
        sb.append(":");
        sb.append(args);
        return sb.toString();
    }
}

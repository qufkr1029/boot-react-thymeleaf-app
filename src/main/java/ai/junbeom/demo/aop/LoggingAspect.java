package ai.junbeom.demo.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut to execute on all methods in classes in the controller package.
     */
    @Pointcut("execution(* ai.junbeom.demo.controller..*.*(..)) || execution(* ai.junbeom.demo.account.web..*.*(..))")
    public void controllerLayer() {
    }

    // @Before("controllerLayer()")
    // public void logBefore(JoinPoint joinPoint) {
    //     ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    //     if (attributes != null) {
    //         HttpServletRequest request = attributes.getRequest();
    //         logger.info("[REQUEST] IP: {}, URI: {}, METHOD: {}", request.getRemoteAddr(), request.getRequestURI(), request.getMethod());
    //     }
    // }
}

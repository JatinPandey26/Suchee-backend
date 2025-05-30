package com.suchee.app.logging;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "logging")
public class Trace {

    private static Logger logger = LoggerFactory.getLogger(Trace.class);

    @Value("${logging.logflags}")
    private String logFlags;

    private static Set<String> enabledFlags = new HashSet<>();

    public static boolean userCreation;
    public static boolean taskCreation;
    public static boolean validationErrors;
    public static boolean role;
    public static boolean security;
    public static boolean auth;
    public static boolean team;
    public static boolean attachment;
    public static boolean member;
    public static boolean notification;
    public static boolean asyncEvent;



    @PostConstruct
    public void initFlags() {

        try{
            String[] flags = logFlags.split(",");
            enabledFlags = new HashSet<>(Arrays.stream(flags).toList());

            Class<?> traceClass = Trace.class;

            // to enable all logging we can pass "all" flag.
            if(enabledFlags.contains("all")){
                Field[] fields = traceClass.getDeclaredFields();
                for(Field field : fields){
                    if(field.getType() == boolean.class){
                        field.setBoolean(null,true);
                    }
                }
                return;
            }


            for(String enabledFlag : enabledFlags){
                Field field = traceClass.getDeclaredField(enabledFlag);
                if(field.getType() == Boolean.class){
                    field.setBoolean(null,true);
                }
            }
        }
        catch (NoSuchFieldException | IllegalAccessException exception){
            throw new RuntimeException(exception.getMessage());
        }

    }

    public static void debug(Object... contents) {
        log(LogLevel.DEBUG, contents);
    }

    public static void info(Object... contents) {
        log(LogLevel.INFO, contents);
    }

    public static void warn(Object... contents) {
        log(LogLevel.WARN, contents);
    }

    public static void error(Object... contents) {
        log(LogLevel.ERROR, contents);
    }

    public static void log(Object... contents) {
        log(LogLevel.INFO,contents);
    }

    public static void log(LogLevel level, Object... contents) {
        if (contents == null || contents.length == 0) {
            return; // nothing to log
        }

        // Join all contents with space
        String message = joinContents(contents);

        switch (level) {
            case TRACE:
                if (logger.isTraceEnabled()) {
                    logger.trace(message);
                }
                break;
            case DEBUG:
                if (logger.isDebugEnabled()) {
                    logger.debug(message);
                }
                break;
            case INFO:
                if (logger.isInfoEnabled()) {
                    logger.info(message);
                }
                break;
            case WARN:
                if (logger.isWarnEnabled()) {
                    logger.warn(message);
                }
                break;
            case ERROR:
                if (logger.isErrorEnabled()) {
                    logger.error(message);
                }
                break;
        }
    }

    private static String joinContents(Object[] contents) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : contents) {
            if (obj != null) {
                if (sb.length() > 0) sb.append(' ');
                sb.append(obj.toString());
            }
        }
        return sb.toString();
    }

}

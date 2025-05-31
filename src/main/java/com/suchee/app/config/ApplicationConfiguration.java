package com.suchee.app.config;

import com.suchee.app.logging.Trace;
import com.suchee.app.messaging.async.AsyncMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Central configuration class for the Spring application.
 *
 * <p>This class enables JPA repositories, transaction management, and
 * entity scanning for the specified base packages.</p>
 *
 * <ul>
 *   <li>{@code @EnableJpaRepositories} enables detection of Spring Data JPA repositories
 *       in the specified package.</li>
 *   <li>{@code @EnableTransactionManagement} activates annotation-driven transaction management.</li>
 *   <li>{@code @EntityScan} specifies the packages to scan for JPA entity classes.</li>
 * </ul>
 */

@Configuration
@EnableJpaRepositories(basePackages = "com.suchee.app.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "com.suchee.app.entity")
//@EnableConfigurationProperties(Trace.class)
@EnableJpaAuditing
public class ApplicationConfiguration {
    @Value(value = "${spring.kafka.backoff.interval}")
    private Long interval;

    @Value(value = "${spring.kafka.backoff.max-retries}")
    private Long maxRetries;

   @Bean
    public KafkaTemplate<String,AsyncMessage> kafkaTemplate(ProducerFactory<String, AsyncMessage> producerFactory){
       return new KafkaTemplate<>(producerFactory);
   }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(interval, maxRetries);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            Trace.log("Max attempt reached for message" , consumerRecord,exception);
        }, fixedBackOff);
        return errorHandler;
    }


}

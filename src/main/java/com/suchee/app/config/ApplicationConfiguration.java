package com.suchee.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
public class ApplicationConfiguration {
}

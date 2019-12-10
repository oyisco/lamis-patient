package org.fhi360.lamis.modules.patient.config;

import com.foreach.across.modules.hibernate.jpa.repositories.config.EnableAcrossJpaRepositories;
import org.fhi360.lamis.modules.patient.domain.Domain;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableAcrossJpaRepositories(basePackageClasses = Domain.class)
@EnableElasticsearchRepositories(basePackages = "org.fhi360.lamis.modules.patient.domain.repositories.search")
public class DomainConfiguration {
}

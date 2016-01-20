package youyu;

import javax.persistence.EntityManager;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
class SimpleConfiguration {
	@Bean
	public PathQuerier pathQuerier(EntityManager entityManager){
		return new PathQuerier(entityManager);
	}
	
}

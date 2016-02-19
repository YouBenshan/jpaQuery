package youbenshan;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
class SimpleConfiguration {
	@Bean
	public PathQuerier pathQuerier(){
		return new PathQuerier();
	}
	
}

package youbenshan;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = { SimpleConfiguration.class, Jsr310JpaConverters.class })
class SimpleConfiguration {
}

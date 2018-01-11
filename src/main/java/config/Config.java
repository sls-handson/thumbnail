package config;

import org.springframework.cloud.function.context.FunctionScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@FunctionScan
@Configuration
public class Config {

	private final Properties properties;

	public Config(Properties properties) {
		//this.credentialsProvider = credentialsProvider;
		this.properties = properties;
	}

	@Bean
	public AmazonS3 s3() {
		return AmazonS3ClientBuilder
		.standard()
		.withRegion(this.properties.getRegion())
		.build();
	}
}

package dev.rohitahuja;

import dev.rohitahuja.service.FeedCountService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringaiMcpSseServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringaiMcpSseServerApplication.class, args);
	}

	@Bean
	ToolCallbackProvider toolCallbackProvider (FeedCountService feedCountService) {
		return MethodToolCallbackProvider
				.builder()
				.toolObjects(feedCountService)
				.build();
	}
}

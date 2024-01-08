# SpringSecuritywithJWT
 Implementing Spring Security Using Json Web Tokens.


 Multiple Response formats dependencies

 <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml -->
		<dependency>
			<groupId>com.fasterxml.jackson.dataformat</groupId>
			<artifactId>jackson-dataformat-xml</artifactId>
			<version>2.16.1</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-yaml -->
		<dependency>
			<groupId>com.fasterxml.jackson.dataformat</groupId>
			<artifactId>jackson-dataformat-yaml</artifactId>
			<version>2.16.1</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-rest</artifactId>
		</dependency>

		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.16.1</version>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>2.16.1</version>
		</dependency>

  @Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Bean
    public MappingJackson2HttpMessageConverter jsonConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public MappingJackson2XmlHttpMessageConverter xmlConverter() {
        return new MappingJackson2XmlHttpMessageConverter();
    }
    
    @Bean
    public MappingJackson2YamlHttpMessageConverter yamlConverter() {

        YAMLMapper mapper = new YAMLMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return new MappingJackson2YamlHttpMessageConverter(mapper);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jsonConverter());
        converters.add(xmlConverter());
        converters.add(yamlConverter());
    }
}

public class MappingJackson2YamlHttpMessageConverter extends AbstractJackson2HttpMessageConverter {

	MappingJackson2YamlHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.parseMediaType("application/x-yaml"));
    }
}
----------------------------------------------------------------------------------------------------------------------------

JSON Web Token Dependencies

  <!-- JSON Web Token (JWT): library for working with JWT tokens -->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.12.3</version>
		</dependency>

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-impl</artifactId>
			<version>0.12.3</version>
			<scope>runtime</scope>
		</dependency>

		<!-- For Jackson -->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-jackson</artifactId>
			<version>0.12.3</version>
		</dependency>
-------------------------------------------------------------------------------------------------------------------------------

# /api-docs endpoint custom path
#springdoc.api-docs.path=/api-docs
# swagger-ui custom path
springdoc.swagger-ui.path=/swagger-ui.html
#display spring-boot-actuator endpoints
springdoc.show-actuator=true



#springdoc.use-management-port=true
# This property enables the openapi and swagger-ui endpoints to be exposed beneath the actuator base path.
#management.endpoints.web.exposure.include=openapi, swagger-ui
#To expose the swagger-ui, on the management port, you should set
#management.server.port=9090



# Disabling the /v3/api-docs endpoint
#springdoc.api-docs.enabled=false
# Disabling the swagger-ui
#springdoc.swagger-ui.enabled=false


<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.3.0</version>
		</dependency>

  

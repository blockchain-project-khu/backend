package blockchain.project.khu.apiserver.common.config;

import blockchain.project.khu.apiserver.common.annotation.CurrentUser;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")
                ))
                .components(components())
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("accessToken"));
    }

    @Bean
    public OperationCustomizer customizeCurrentUserParameter() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.hasMethodAnnotation(CurrentUser.class)) {
                operation.getParameters().removeIf(
                        param -> param.getName().equals("currentUser")
                );
            }
            return operation;
        };
    }

    private Info apiInfo() {
        return new Info()
                .title("BlockEstate Backend API Docs")
                .description("BlockEstate API 명세서입니다.");
    }

    // JWT 토큰을 위한 보안 컴포넌트 설정
    private Components components() {
        SecurityScheme apiKeyScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)       // API Key 방식
                .in(SecurityScheme.In.HEADER)           // 요청 헤더에 담는다
                .name("access")                         // 헤더 이름을 access 로 지정
                .description("로그인 후 발급받은 JWT access 토큰을 여기에 입력하세요");

        return new Components()
                .addSecuritySchemes("accessToken", apiKeyScheme);
    }
}

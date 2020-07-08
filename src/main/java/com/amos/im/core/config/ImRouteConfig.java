package com.amos.im.core.config;

import com.amos.im.core.test.ServerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 模块名称: im
 * 模块描述: 路由配置
 *
 * @author amos.wang
 * @date 2020/7/8 19:09
 */
@Configuration
public class ImRouteConfig {

    @Bean
    public RouterFunction<ServerResponse> ws(ServerHandler serverHandler) {
///        RouterFunction<ServerResponse> route = route()
//                .GET("/person/{id}", accept(APPLICATION_JSON), handler::getPerson)
//                .GET("/person", accept(APPLICATION_JSON), handler::listPeople)
//                .POST("/person", handler::createPerson)
//                .add(otherRoute)
//                .build();

        return RouterFunctions
                .route(RequestPredicates.GET("/ws").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), serverHandler::ws);
    }

}

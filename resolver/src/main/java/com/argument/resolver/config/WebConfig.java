package com.argument.resolver.config;

import com.argument.resolver.config.domain.resolver.ClientInfoArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by rius0918@gmail.com on 2024. 3. 27.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers (List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ClientInfoArgumentResolver());
    }
}

package com.argument.resolver.config.domain.resolver;

import com.argument.resolver.config.domain.Client.ClientRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;


/**
 * Created by rius0918@gmail.com on 2024. 3. 27.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
@Slf4j
public class ClientInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행!");

        // 파라미터에 어노테이션이 붙어 있는지
        boolean hasClientInfoAnnotation = parameter.hasParameterAnnotation(CliectInfo.class);

        // ClientRequest 객체에 붙어 있는지
        boolean hasClientType = ClientRequest.class.isAssignableFrom(parameter.getParameterType());

        return hasClientInfoAnnotation && hasClientType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실행1 {}", parameter);
        log.info("resolveArgument 실행2 {}", mavContainer);
        log.info("resolveArgument 실행3 {}", webRequest);
        log.info("resolveArgument 실행4 {}", binderFactory);

        ClientRequest clientRequest = new ClientRequest();
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        clientRequest.setIp(request.getRemoteAddr());
        clientRequest.setParams((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));

        return clientRequest;
    }
}

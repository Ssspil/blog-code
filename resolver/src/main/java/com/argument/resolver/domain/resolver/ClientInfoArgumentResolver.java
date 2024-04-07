package com.argument.resolver.domain.resolver;

import com.argument.resolver.domain.Client.ClientRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;


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

        ClientRequest clientRequest = new ClientRequest();
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        clientRequest.setIp(request.getRemoteAddr());
        clientRequest.setUrl(request.getRequestURI());
        clientRequest.setMethod(request.getMethod());
        clientRequest.setParams(request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));

        // ClientRequest(url=/spring-mvc/coasis, params=/spring-mvc/{name}, method=GET, ip=0:0:0:0:0:0:0:1)
        return clientRequest;
    }
}

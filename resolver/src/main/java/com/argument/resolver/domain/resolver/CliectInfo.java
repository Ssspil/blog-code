package com.argument.resolver.domain.resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by rius0918@gmail.com on 2024. 3. 27.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CliectInfo {
    /*
        @Target(ElementType.PARAMETER) : 파라미터에만 사용
        @Retention(RetentionPolicy.RUNTIME) : 리플렉션 등을 활용할 수 있도록 런타임까지 애노테이션 정보가 남아있음
     */
}

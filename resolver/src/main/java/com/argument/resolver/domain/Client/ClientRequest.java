package com.argument.resolver.domain.Client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * Created by rius0918@gmail.com on 2024. 3. 27.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
@Setter
@Getter
@ToString
public class ClientRequest {

    private String url;
    private Object params;
    private String method;
    private String ip;
}

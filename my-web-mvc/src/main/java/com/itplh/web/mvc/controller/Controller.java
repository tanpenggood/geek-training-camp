package com.itplh.web.mvc.controller;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Controller 接口（标记接口）
 *
 * @since 1.0
 */
public interface Controller {

    /**
     * 字符集转换，解决浏览器传输过来的中文乱码
     *
     * @param param
     * @return
     */
    default Optional<String> stringCharsetConvert(String param) {
        return Optional.ofNullable(param)
                .map(name -> new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
    }

}

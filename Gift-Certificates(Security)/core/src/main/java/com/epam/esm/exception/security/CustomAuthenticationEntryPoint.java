package com.epam.esm.exception.security;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.esm.constant.CodeException.UNAUTHORIZED;
import static com.epam.esm.constant.ResponseConstants.CODE;
import static com.epam.esm.constant.ResponseConstants.CONTENT_TYPE;
import static com.epam.esm.constant.ResponseConstants.ENCODING;
import static com.epam.esm.constant.ResponseConstants.MESSAGE;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * Define method which override response message according locale when user unauthorized.
 */
@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageSource resourceBundle;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException {
        String code = UNAUTHORIZED;
        String message = resourceBundle.getMessage(code, new Object[]{}, request.getLocale());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE, code);
        jsonObject.put(MESSAGE, message);

        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        response.getWriter().write(jsonObject.toString());
    }
}

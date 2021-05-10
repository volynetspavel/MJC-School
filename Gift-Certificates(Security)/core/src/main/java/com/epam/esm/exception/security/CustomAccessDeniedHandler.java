package com.epam.esm.exception.security;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.esm.constant.CodeException.FORBIDDEN;
import static com.epam.esm.constant.ResponseConstants.CODE;
import static com.epam.esm.constant.ResponseConstants.CONTENT_TYPE;
import static com.epam.esm.constant.ResponseConstants.ENCODING;
import static com.epam.esm.constant.ResponseConstants.MESSAGE;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

/**
 * Define method which override response message according locale when access denied.
 */
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    private final MessageSource resourceBundle;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException {
        String errorResponse = getErrorResponse(request);
        response.setStatus(SC_FORBIDDEN);
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        response.getWriter().write(errorResponse);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException {
        String errorResponse = getErrorResponse(request);
        response.setStatus(SC_FORBIDDEN);
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        response.getWriter().write(errorResponse);
    }

    private String getErrorResponse(HttpServletRequest request) {
        String code = FORBIDDEN;
        String message = resourceBundle.getMessage(code, new Object[]{}, request.getLocale());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE, code);
        jsonObject.put(MESSAGE, message);

        return jsonObject.toString();
    }
}

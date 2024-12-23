package com.gl.parcauto.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gl.parcauto.dto.response.ErrorDetails;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ContextConfiguration(classes = {AppExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class AppExceptionHandlerTest {
    @Autowired
    private AppExceptionHandler appExceptionHandler;

    /**
     * Method under test:
     * {@link AppExceptionHandler#resourceNotFoundExceptionHandler(ResourceNotFoundException, WebRequest)}
     */
    @Test
    void testResourceNotFoundExceptionHandler() {
        ResourceNotFoundException e = new ResourceNotFoundException("Resource Name", "Field Name", "42");

        ResponseEntity<ErrorDetails> actualResourceNotFoundExceptionHandlerResult = appExceptionHandler
                .resourceNotFoundExceptionHandler(e, new ServletWebRequest(new MockHttpServletRequest()));
        ErrorDetails body = actualResourceNotFoundExceptionHandlerResult.getBody();
        assertEquals("Not Found", body.error());
        assertEquals("Resource Name not found with Field Name : '42'", body.message());
        assertEquals("uri=", body.details());
        assertEquals(404, body.status());
        assertEquals(404, actualResourceNotFoundExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualResourceNotFoundExceptionHandlerResult.hasBody());
        assertTrue(actualResourceNotFoundExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#resourceNotFoundExceptionHandler(ResourceNotFoundException, WebRequest)}
     */
    @Test
    void testResourceNotFoundExceptionHandler2() {
        ResourceNotFoundException e = mock(ResourceNotFoundException.class);
        when(e.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<ErrorDetails> actualResourceNotFoundExceptionHandlerResult = appExceptionHandler
                .resourceNotFoundExceptionHandler(e, new ServletWebRequest(new MockHttpServletRequest()));
        verify(e).getMessage();
        ErrorDetails body = actualResourceNotFoundExceptionHandlerResult.getBody();
        assertEquals("Not Found", body.error());
        assertEquals("Not all who wander are lost", body.message());
        assertEquals("uri=", body.details());
        assertEquals(404, body.status());
        assertEquals(404, actualResourceNotFoundExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualResourceNotFoundExceptionHandlerResult.hasBody());
        assertTrue(actualResourceNotFoundExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#handleConflict(DataIntegrityViolationException, WebRequest)}
     */
    @Test
    void testHandleConflict() {
        DataIntegrityViolationException e = new DataIntegrityViolationException("Msg");
        ResponseEntity<ErrorDetails> actualHandleConflictResult = appExceptionHandler.handleConflict(e,
                new ServletWebRequest(new MockHttpServletRequest()));
        ErrorDetails body = actualHandleConflictResult.getBody();
        assertEquals("Conflict", body.error());
        assertEquals("Msg", body.message());
        assertEquals("uri=", body.details());
        assertEquals(409, body.status());
        assertEquals(409, actualHandleConflictResult.getStatusCodeValue());
        assertTrue(actualHandleConflictResult.hasBody());
        assertTrue(actualHandleConflictResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#handleConflict(DataIntegrityViolationException, WebRequest)}
     */
    @Test
    void testHandleConflict2() {
        DataIntegrityViolationException e = mock(DataIntegrityViolationException.class);
        when(e.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<ErrorDetails> actualHandleConflictResult = appExceptionHandler.handleConflict(e,
                new ServletWebRequest(new MockHttpServletRequest()));
        verify(e).getMessage();
        ErrorDetails body = actualHandleConflictResult.getBody();
        assertEquals("Conflict", body.error());
        assertEquals("Not all who wander are lost", body.message());
        assertEquals("uri=", body.details());
        assertEquals(409, body.status());
        assertEquals(409, actualHandleConflictResult.getStatusCodeValue());
        assertTrue(actualHandleConflictResult.hasBody());
        assertTrue(actualHandleConflictResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#accessDeniedExceptionHandler(AccessDeniedException, WebRequest)}
     */
    @Test
    void testAccessDeniedExceptionHandler() {
        AccessDeniedException e = new AccessDeniedException("Msg");
        ResponseEntity<ErrorDetails> actualAccessDeniedExceptionHandlerResult = appExceptionHandler
                .accessDeniedExceptionHandler(e, new ServletWebRequest(new MockHttpServletRequest()));
        ErrorDetails body = actualAccessDeniedExceptionHandlerResult.getBody();
        assertEquals("Msg", body.message());
        assertEquals("Unauthorized", body.error());
        assertEquals("uri=", body.details());
        assertEquals(401, body.status());
        assertEquals(401, actualAccessDeniedExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualAccessDeniedExceptionHandlerResult.hasBody());
        assertTrue(actualAccessDeniedExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#accessDeniedExceptionHandler(AccessDeniedException, WebRequest)}
     */
    @Test
    void testAccessDeniedExceptionHandler2() {
        AccessDeniedException e = new AccessDeniedException("Msg");
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getRequestURI()).thenReturn("https://example.org/example");
        ResponseEntity<ErrorDetails> actualAccessDeniedExceptionHandlerResult = appExceptionHandler
                .accessDeniedExceptionHandler(e, new ServletWebRequest(request));
        verify(request).getRequestURI();
        ErrorDetails body = actualAccessDeniedExceptionHandlerResult.getBody();
        assertEquals("Msg", body.message());
        assertEquals("Unauthorized", body.error());
        assertEquals("uri=https://example.org/example", body.details());
        assertEquals(401, body.status());
        assertEquals(401, actualAccessDeniedExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualAccessDeniedExceptionHandlerResult.hasBody());
        assertTrue(actualAccessDeniedExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException, WebRequest)}
     */
    @Test
    void testMethodArgumentTypeMismatchExceptionHandler() {
        Class<Object> requiredType = Object.class;
        MethodArgumentTypeMismatchException e = new MethodArgumentTypeMismatchException("Value", requiredType,
                "0123456789ABCDEF", null, new Throwable());

        ResponseEntity<ErrorDetails> actualMethodArgumentTypeMismatchExceptionHandlerResult = appExceptionHandler
                .methodArgumentTypeMismatchExceptionHandler(e, new ServletWebRequest(new MockHttpServletRequest()));
        ErrorDetails body = actualMethodArgumentTypeMismatchExceptionHandlerResult.getBody();
        assertEquals("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Object'; null",
                body.message());
        assertEquals("Unprocessable Entity", body.error());
        assertEquals("uri=", body.details());
        assertEquals(422, body.status());
        assertEquals(422, actualMethodArgumentTypeMismatchExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualMethodArgumentTypeMismatchExceptionHandlerResult.hasBody());
        assertTrue(actualMethodArgumentTypeMismatchExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException, WebRequest)}
     */
    @Test
    void testMethodArgumentTypeMismatchExceptionHandler2() {
        Class<Object> requiredType = Object.class;
        MethodParameter param = mock(MethodParameter.class);
        MethodArgumentTypeMismatchException e = new MethodArgumentTypeMismatchException("Value", requiredType,
                "0123456789ABCDEF", param, new Throwable());

        ResponseEntity<ErrorDetails> actualMethodArgumentTypeMismatchExceptionHandlerResult = appExceptionHandler
                .methodArgumentTypeMismatchExceptionHandler(e, new ServletWebRequest(new MockHttpServletRequest()));
        ErrorDetails body = actualMethodArgumentTypeMismatchExceptionHandlerResult.getBody();
        assertEquals("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Object'; null",
                body.message());
        assertEquals("Unprocessable Entity", body.error());
        assertEquals("uri=", body.details());
        assertEquals(422, body.status());
        assertEquals(422, actualMethodArgumentTypeMismatchExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualMethodArgumentTypeMismatchExceptionHandlerResult.hasBody());
        assertTrue(actualMethodArgumentTypeMismatchExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#blogApiExceptionHandler(AppException, WebRequest)}
     */
    @Test
    void testBlogApiExceptionHandler() {
        AppException e = new AppException(HttpStatus.CONTINUE, "An error occurred");

        ResponseEntity<ErrorDetails> actualBlogApiExceptionHandlerResult = appExceptionHandler.blogApiExceptionHandler(e,
                new ServletWebRequest(new MockHttpServletRequest()));
        ErrorDetails body = actualBlogApiExceptionHandlerResult.getBody();
        assertEquals("An error occurred", body.message());
        assertEquals("Continue", body.error());
        assertEquals("uri=", body.details());
        assertEquals(100, body.status());
        assertEquals(100, actualBlogApiExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualBlogApiExceptionHandlerResult.hasBody());
        assertTrue(actualBlogApiExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#blogApiExceptionHandler(AppException, WebRequest)}
     */
    @Test
    void testBlogApiExceptionHandler2() {
        AppException e = mock(AppException.class);
        when(e.getMessage()).thenReturn("An error occurred");
        when(e.getStatus()).thenReturn(HttpStatus.CONTINUE);
        ResponseEntity<ErrorDetails> actualBlogApiExceptionHandlerResult = appExceptionHandler.blogApiExceptionHandler(e,
                new ServletWebRequest(new MockHttpServletRequest()));
        verify(e).getMessage();
        verify(e).getStatus();
        ErrorDetails body = actualBlogApiExceptionHandlerResult.getBody();
        assertEquals("An error occurred", body.message());
        assertEquals("Continue", body.error());
        assertEquals("uri=", body.details());
        assertEquals(100, body.status());
        assertEquals(100, actualBlogApiExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualBlogApiExceptionHandlerResult.hasBody());
        assertTrue(actualBlogApiExceptionHandlerResult.getHeaders().isEmpty());
    }



    /**
     * Method under test:
     * {@link AppExceptionHandler#authenticationNotFoundExceptionHandler(AuthenticationCredentialsNotFoundException, WebRequest)}
     */
    @Test
    void testAuthenticationNotFoundExceptionHandler() {
        AuthenticationCredentialsNotFoundException e = new AuthenticationCredentialsNotFoundException("Msg");
        ResponseEntity<ErrorDetails> actualAuthenticationNotFoundExceptionHandlerResult = appExceptionHandler
                .authenticationNotFoundExceptionHandler(e, new ServletWebRequest(new MockHttpServletRequest()));
        ErrorDetails body = actualAuthenticationNotFoundExceptionHandlerResult.getBody();
        assertEquals("Msg", body.message());
        assertEquals("Unauthorized", body.error());
        assertEquals("uri=", body.details());
        assertEquals(401, body.status());
        assertEquals(401, actualAuthenticationNotFoundExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualAuthenticationNotFoundExceptionHandlerResult.hasBody());
        assertTrue(actualAuthenticationNotFoundExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#authenticationNotFoundExceptionHandler(AuthenticationCredentialsNotFoundException, WebRequest)}
     */
    @Test
    void testAuthenticationNotFoundExceptionHandler2() {
        AuthenticationCredentialsNotFoundException e = new AuthenticationCredentialsNotFoundException("Msg");
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getRequestURI()).thenReturn("https://example.org/example");
        ResponseEntity<ErrorDetails> actualAuthenticationNotFoundExceptionHandlerResult = appExceptionHandler
                .authenticationNotFoundExceptionHandler(e, new ServletWebRequest(request));
        verify(request).getRequestURI();
        ErrorDetails body = actualAuthenticationNotFoundExceptionHandlerResult.getBody();
        assertEquals("Msg", body.message());
        assertEquals("Unauthorized", body.error());
        assertEquals("uri=https://example.org/example", body.details());
        assertEquals(401, body.status());
        assertEquals(401, actualAuthenticationNotFoundExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualAuthenticationNotFoundExceptionHandlerResult.hasBody());
        assertTrue(actualAuthenticationNotFoundExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleMethodArgumentNotValid() {
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((Executable) null,
                new BindException("Target", "Object Name"));

        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleMethodArgumentNotValidResult = appExceptionHandler
                .handleMethodArgumentNotValid(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        assertEquals(400, actualHandleMethodArgumentNotValidResult.getStatusCodeValue());
        assertTrue(((Map<Object, Object>) actualHandleMethodArgumentNotValidResult.getBody()).isEmpty());
        assertTrue(actualHandleMethodArgumentNotValidResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatusCode, WebRequest)}
     */
    @Test
    void testHandleMethodArgumentNotValid2() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(new ArrayList<>());
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((Executable) null, bindingResult);

        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Object> actualHandleMethodArgumentNotValidResult = appExceptionHandler
                .handleMethodArgumentNotValid(ex, headers, null, new ServletWebRequest(new MockHttpServletRequest()));
        verify(bindingResult).getFieldErrors();
        assertEquals(400, actualHandleMethodArgumentNotValidResult.getStatusCodeValue());
        assertTrue(((Map<Object, Object>) actualHandleMethodArgumentNotValidResult.getBody()).isEmpty());
        assertTrue(actualHandleMethodArgumentNotValidResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#GlobalExceptionHandler(Exception, WebRequest)}
     */
    @Test
    void testGlobalExceptionHandler() {
        Exception e = new Exception("foo");
        ResponseEntity<ErrorDetails> actualGlobalExceptionHandlerResult = appExceptionHandler.GlobalExceptionHandler(e,
                new ServletWebRequest(new MockHttpServletRequest()));
        ErrorDetails body = actualGlobalExceptionHandlerResult.getBody();
        assertEquals("Internal Server Error", body.error());
        assertEquals("foo", body.message());
        assertEquals("uri=", body.details());
        assertEquals(500, body.status());
        assertEquals(500, actualGlobalExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualGlobalExceptionHandlerResult.hasBody());
        assertTrue(actualGlobalExceptionHandlerResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link AppExceptionHandler#GlobalExceptionHandler(Exception, WebRequest)}
     */
    @Test
    void testGlobalExceptionHandler2() {
        Exception e = new Exception("foo");
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getRequestURI()).thenReturn("https://example.org/example");
        ResponseEntity<ErrorDetails> actualGlobalExceptionHandlerResult = appExceptionHandler.GlobalExceptionHandler(e,
                new ServletWebRequest(request));
        verify(request).getRequestURI();
        ErrorDetails body = actualGlobalExceptionHandlerResult.getBody();
        assertEquals("Internal Server Error", body.error());
        assertEquals("foo", body.message());
        assertEquals("uri=https://example.org/example", body.details());
        assertEquals(500, body.status());
        assertEquals(500, actualGlobalExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualGlobalExceptionHandlerResult.hasBody());
        assertTrue(actualGlobalExceptionHandlerResult.getHeaders().isEmpty());
    }
}

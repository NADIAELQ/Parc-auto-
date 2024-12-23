package com.gl.parcauto.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ResourceNotFoundException.class, String.class})
@ExtendWith(SpringExtension.class)
class ResourceNotFoundExceptionTest {
    @Autowired
    private ResourceNotFoundException resourceNotFoundException;

    /**
     * Method under test:
     * {@link ResourceNotFoundException#ResourceNotFoundException(String, String, String)}
     */
    @Test
    void testConstructor() {
        ResourceNotFoundException actualResourceNotFoundException = new ResourceNotFoundException("Resource Name",
                "Field Name", "42");

        assertEquals("42", actualResourceNotFoundException.getFieldValue());
        assertEquals("Field Name", actualResourceNotFoundException.getFieldName());
        assertEquals("Resource Name", actualResourceNotFoundException.getResourceName());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link ResourceNotFoundException#getFieldName()}
     *   <li>{@link ResourceNotFoundException#getFieldValue()}
     *   <li>{@link ResourceNotFoundException#getResourceName()}
     * </ul>
     */
    @Test
    void testGetFieldName() {
        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException("Resource Name", "Field Name",
                "42");
        String actualFieldName = resourceNotFoundException.getFieldName();
        String actualFieldValue = resourceNotFoundException.getFieldValue();
        assertEquals("42", actualFieldValue);
        assertEquals("Field Name", actualFieldName);
        assertEquals("Resource Name", resourceNotFoundException.getResourceName());
    }
}

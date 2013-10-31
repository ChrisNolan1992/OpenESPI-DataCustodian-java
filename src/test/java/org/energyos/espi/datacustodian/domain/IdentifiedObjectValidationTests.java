package org.energyos.espi.datacustodian.domain;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.datacustodian.support.TestUtils.assertAnnotationPresent;

public class IdentifiedObjectValidationTests {
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        IdentifiedObject identifiedObject = new IdentifiedObject();
        identifiedObject.setUUID(UUID.randomUUID());

        Set<ConstraintViolation<IdentifiedObject>> violations = validator.validate(identifiedObject);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        IdentifiedObject IdentifiedObject = new IdentifiedObject();

        Set<ConstraintViolation<IdentifiedObject>> violations = validator.validate(IdentifiedObject);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void uuid() {
        assertAnnotationPresent(IdentifiedObject.class, "uuid", NotNull.class);
    }
}

package com.bank.transfer.service.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.persistence.EntityNotFoundException;

/**
 * возвращает {@link EntityNotFoundException}.
 */
@Slf4j
@ControllerAdvice
@Component
public class EntityNotFoundReturner {

    /**
     * @param id {@link String}
     * @return {@link EntityNotFoundException}
     */
    public EntityNotFoundException getEntityNotFoundException(Long id, String message) {
        return new EntityNotFoundException(message + id);
    }
}

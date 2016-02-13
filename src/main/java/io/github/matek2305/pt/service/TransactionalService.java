package io.github.matek2305.pt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Service
@Transactional
@interface TransactionalService {
}

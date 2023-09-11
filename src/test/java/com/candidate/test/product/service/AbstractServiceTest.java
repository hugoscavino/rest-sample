package com.candidate.test.product.service;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ComponentScan({ "com.candidate.test.product.repository", "com.candidate.test.product.entity", "com.candidate.test.product.service" })
@ActiveProfiles("test")
public abstract class AbstractServiceTest {
}

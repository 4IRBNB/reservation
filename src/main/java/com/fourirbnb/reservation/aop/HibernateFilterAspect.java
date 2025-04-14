package com.fourirbnb.reservation.aop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HibernateFilterAspect {

  @PersistenceContext
  private EntityManager entityManager;

  @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
  public void beforeTransaction() {
    entityManager.unwrap(Session.class)
        .enableFilter("deletedFilter");
  }
}

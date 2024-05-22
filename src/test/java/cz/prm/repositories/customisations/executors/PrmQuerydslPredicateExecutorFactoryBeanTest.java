package cz.prm.repositories.customisations.executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cz.prm.domain.contact.Contact;
import jakarta.persistence.EntityManager;
import org.hibernate.internal.SessionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrmQuerydslPredicateExecutorFactoryBeanTest {

    @Mock
    private EntityManager entityManager;
    @Mock
    private SessionImpl hibernateSession;

    private PrmQuerydslPredicateExecutorFactoryBean factoryBean;

    @BeforeEach
    void setUp() {
        when(entityManager.getDelegate()).thenReturn(hibernateSession);
        factoryBean = new PrmQuerydslPredicateExecutorFactoryBean(Contact.class);
    }

    @Test
    void getRepositoryFragmentsIsReactiveRepository() {
        var factory = factoryBean.createRepositoryFactory(entityManager);
        assertThat(factory).isNotNull().isInstanceOf(PrmQuerydslPredicateExecutorFactory.class);
    }
}
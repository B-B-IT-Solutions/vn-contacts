package cz.prm.repositories.customisations.executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import cz.prm.domain.contact.Contact;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.persistence.metamodel.IdentifiableType;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.internal.SessionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.repository.core.RepositoryMetadata;

@ExtendWith(MockitoExtension.class)
class PrmQuerydslPredicateExecutorFactoryTest {

   @Mock
   private EntityManagerFactory entityManagerFactory;
   @Mock
   private EntityManager entityManager;
   @Mock
   private PersistenceUnitUtil persistenceUnitUtil;
   @Mock
   private SessionImpl hibernateSession;
   @Mock
   private RepositoryMetadata metadata;
   @Mock
   private Metamodel metamodel;
   @Mock
   private IdentifiableType managedType;

   private PrmQuerydslPredicateExecutorFactory factory;

   @BeforeEach
   void setUp() {
      when(entityManager.getDelegate()).thenReturn(hibernateSession);
      factory = new PrmQuerydslPredicateExecutorFactory(entityManager);
   }

   @Test
   void getRepositoryFragmentsIsReactiveRepository() {
      when(metadata.isReactiveRepository()).thenReturn(true);
      assertThrows(InvalidDataAccessApiUsageException.class, () -> factory.getRepositoryFragments(metadata));
   }

   @Test
   void getRepositoryFragments() {
      doReturn(Contact.class).when(metadata).getDomainType();
      when(entityManager.getEntityManagerFactory()).thenReturn(entityManagerFactory);
      when(entityManagerFactory.getPersistenceUnitUtil()).thenReturn(persistenceUnitUtil);
      when(entityManager.getMetamodel()).thenReturn(metamodel);
      when(metamodel.managedType(Contact.class)).thenReturn(managedType);
      var result = factory.getRepositoryFragments(metadata);
      assertThat(result).isNotEmpty();
   }

}
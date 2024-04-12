package cz.prm.repositories.customisations.repositories;

import static cz.prm.utils.ContactUtils.contact;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import org.hibernate.internal.SessionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

@ExtendWith(MockitoExtension.class)
class RefreshAwareRepositoryImplTest {

   @Mock
   private EntityManager entityManager;
   @Mock
   private JpaEntityInformation entityInformation;
   @Mock
   private SessionImpl hibernateSession;

   private RefreshAwareRepositoryImpl repository;

   @BeforeEach
   void setUp() {
      when(entityManager.getDelegate()).thenReturn(hibernateSession);
      repository = new RefreshAwareRepositoryImpl(entityInformation, entityManager);
   }

   @Test
   void refresh() {
      var user = contact();
      repository.refresh(user);
      verify(entityManager).refresh(user);
   }
}
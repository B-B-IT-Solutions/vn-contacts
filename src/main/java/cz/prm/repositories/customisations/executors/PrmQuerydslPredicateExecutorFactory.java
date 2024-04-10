package cz.prm.repositories.customisations.executors;

import static org.springframework.data.querydsl.SimpleEntityPathResolver.INSTANCE;

import jakarta.persistence.EntityManager;
import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryImpl;
import org.springframework.data.envers.repository.support.ReflectionRevisionEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;
import org.springframework.data.repository.core.support.RepositoryFragment;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

public class PrmQuerydslPredicateExecutorFactory extends JpaRepositoryFactory {

   private static final String QUERYDSL_PACKAGE_SUFFIX = ".querydsl";

   private EntityManager entityManager;
   private SimpleEntityPathResolver entityPathResolver;
   private RevisionEntityInformation revisionEntityInformation;

   public PrmQuerydslPredicateExecutorFactory(EntityManager entityManager) {
      super(entityManager);
      this.entityManager = entityManager;
      this.entityPathResolver = new SimpleEntityPathResolver(QUERYDSL_PACKAGE_SUFFIX);
      this.revisionEntityInformation = new ReflectionRevisionEntityInformation(DefaultRevisionEntity.class);
   }

   @Override
   protected RepositoryComposition.RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
      if (metadata.isReactiveRepository()) {
         throw new InvalidDataAccessApiUsageException("Cannot combine Querydsl and reactive repository in a single interface!");
      }
      var entityInformation = getEntityInformation(metadata.getDomainType());
      var querydslFragment = getPrmQuerydslTargetRepository(entityInformation);
      var revisionsFragment = getRevisionsTargetRepository(entityInformation);

      var fragments = RepositoryComposition.RepositoryFragments.empty();
      return fragments.append(RepositoryFragments.just(querydslFragment)).append(RepositoryFragment.implemented(revisionsFragment));
   }

   private Object getPrmQuerydslTargetRepository(JpaEntityInformation<?, Object> entityInformation) {
      return getTargetRepositoryViaReflection(PrmQueryDslJpaPredicateExecutor.class, entityInformation, entityManager, INSTANCE, null);
   }

   private Object getRevisionsTargetRepository(JpaEntityInformation<?, Object> entityInformation) {
      return getTargetRepositoryViaReflection(EnversRevisionRepositoryImpl.class, entityInformation, revisionEntityInformation, entityManager);
   }
}

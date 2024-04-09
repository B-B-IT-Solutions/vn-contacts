package cz.prm.repositories.customisations.executors;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import jakarta.persistence.EntityManager;
import javax.annotation.Nullable;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.EntityPathResolver;

public class PrmQueryDslJpaPredicateExecutor<T> extends QuerydslJpaPredicateExecutor<T> implements PrmQuerydslPredicateExecutor<T> {

   private final EntityPath<T> path;
   private final Querydsl querydsl;

   public PrmQueryDslJpaPredicateExecutor(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, EntityPathResolver resolver,
       @Nullable CrudMethodMetadata metadata) {
      super(entityInformation, entityManager, resolver, metadata);
      this.path = resolver.createPath(entityInformation.getJavaType());
      this.querydsl = new Querydsl(entityManager, new PathBuilder<>(path.getType(), path.getMetadata()));
   }

   @Override
   public JPQLQuery<?> createQuery(Predicate predicate) {
      return super.createQuery(predicate);
   }
}

package cz.prm.repositories.extensions.executors;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.history.RevisionRepository;

public class PrmQuerydslPredicateExecutorFactoryBean<T extends RevisionRepository<S, ID, N>, S, ID, N extends Number & Comparable<N>> extends
    JpaRepositoryFactoryBean<T, S, ID> {

    public PrmQuerydslPredicateExecutorFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new PrmQuerydslPredicateExecutorFactory(entityManager);
    }
}

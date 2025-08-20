package cz.prm.repositories.extensions.repositories;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class RefreshAwareRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements RefreshAwareRepository<T, ID> {

    private final EntityManager entityManager;

    public RefreshAwareRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public void refresh(T t) {
        entityManager.refresh(t);
    }
}

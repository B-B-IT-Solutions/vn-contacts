package cz.prm.repositories.customisations.repositories;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RefreshAwareRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    void refresh(T t);
}

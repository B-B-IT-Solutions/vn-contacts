package cz.prm.services;

import cz.prm.domain.User;
import cz.prm.repositories.user.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   private UserRepository repository;

   public UserService(UserRepository repository) {
      this.repository = repository;
   }

   public List<User> getUsers() {
      return repository.findAll();
   }
}

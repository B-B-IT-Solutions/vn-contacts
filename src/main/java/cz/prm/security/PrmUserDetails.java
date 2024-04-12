package cz.prm.security;

import static com.google.common.collect.Sets.newHashSet;

import org.springframework.security.core.userdetails.User;

public class PrmUserDetails extends User {

   public PrmUserDetails(cz.prm.domain.user.User prmUser) {
      super(prmUser.getEmail(), prmUser.getPassword(), newHashSet());
   }
}

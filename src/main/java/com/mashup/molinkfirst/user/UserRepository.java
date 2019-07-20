package com.mashup.molinkfirst.user;

import com.mashup.molinkfirst.folder.Folder;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByPhoneUuid(String uuid);
}

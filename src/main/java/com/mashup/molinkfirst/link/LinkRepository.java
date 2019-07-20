package com.mashup.molinkfirst.link;

import com.mashup.molinkfirst.folder.Folder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long>{
  List<Link> findAllByFolder(Optional<Folder> folder);
}

package com.derteuffel.archive.repositories;


import com.derteuffel.archive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByCompte_Id(Long id);
}

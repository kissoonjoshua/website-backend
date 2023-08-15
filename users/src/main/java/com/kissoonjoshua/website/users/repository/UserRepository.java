package com.kissoonjoshua.website.users.repository;

import com.kissoonjoshua.website.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}

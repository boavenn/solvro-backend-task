package io.github.boavenn.solvrobackendtask.auth;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long>
{
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
}

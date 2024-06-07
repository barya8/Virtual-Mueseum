package com.company.repo;

        import com.company.model.User;
        import jakarta.transaction.Transactional;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Modifying;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u.password FROM User u WHERE u.username = :username")
    String findPasswordByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
}

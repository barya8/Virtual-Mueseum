package com.company.repo;

        import com.company.model.User;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) FROM User u WHERE u.username = :username AND u.password = :password")
    int findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}

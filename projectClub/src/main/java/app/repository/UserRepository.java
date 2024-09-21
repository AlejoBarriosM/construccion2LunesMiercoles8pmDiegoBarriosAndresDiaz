package app.repository;

import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    boolean existsByUserNameAndPasswordUser(String userName, String passwordUser);
    boolean existsByUserName(String nameUser);

    @Modifying
    @Query("UPDATE User u SET u.roleUser = :role WHERE u.idUser = :userIdGuest")
    void updateRoleUserByIdUser(@Param("userIdGuest") Long userIdGuest, @Param("role")String role);
}

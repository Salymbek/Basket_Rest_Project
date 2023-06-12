package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.AllUserResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("select new peaksoft.dto.response.AllUserResponse" +
            "(u.id, concat(u.firstName,' ', u.lastName), u.email, u.role)from User u")
    List<AllUserResponse> getAllUsers();

    Optional<User> getUserByEmail(String email);

    @Query("select new peaksoft.dto.response.UserResponse(u.id,u.firstName,u.lastName,u.email,u.password,u.createdDate,u.updateDate,u.role)from User u where u.id=:id")
    Optional<UserResponse> findByUserId(Long id);

}
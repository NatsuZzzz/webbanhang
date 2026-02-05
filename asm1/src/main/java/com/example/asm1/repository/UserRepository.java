package com.example.asm1.repository;

import com.example.asm1.Entity.RegisterForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<RegisterForm, Long> {
    // Hàm này giúp tìm User trong DB dựa trên Email (dùng cho chức năng Login sau này)
    RegisterForm findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Users SET password = :newPassword WHERE email = :email", nativeQuery = true)
    void updatePassword(@Param("email") String email, @Param("newPassword") String newPassword);


    @Transactional
@Modifying
@Query(value = "UPDATE Users SET first_name = :fname, surname = :sname, shopping_preference = :pref WHERE email = :email", nativeQuery = true)
void updateProfile(@Param("fname") String fname, @Param("sname") String sname, @Param("pref") String pref, @Param("email") String email);
}
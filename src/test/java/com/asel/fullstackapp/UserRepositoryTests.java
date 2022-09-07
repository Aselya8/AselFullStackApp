package com.asel.fullstackapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;


    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("aselata@gmail.com");
        user.setPassword("asel22");
        user.setFirstName("Asel");
        user.setLastName("Ata");

        User savedUser = userRepo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }

    @Test
    public void testFindUserByEmail()  {
        String email = "asel@gmail.com";

        User user = userRepo.findByEmail(email);

        assertThat(user).isNotNull();

    }

    @Test
    public void testAddRoleNewUser() {
        User user = new User();
        user.setEmail("alisa@gmail.com");
        user.setPassword("alisa2022");
        user.setFirstName("Alisa");
        user.setLastName("Cat");

        Role roleUser = roleRepo.findByName("User");
        user.addRole(roleUser);

        User savedUser = userRepo.save(user);


        assertThat(savedUser.getRoles().size()).isEqualTo(1);

    }

    //added test for existing user
    @Test
    public void testAddRoleToExistingUser() {
        User user = userRepo.findById(1L).get();
        Role roleUser = roleRepo.findByName("User");
        Role roleCustomer = new Role(3L);

        user.addRole(roleUser);
        user.addRole(roleCustomer);

        User savedUser = userRepo.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(2);
    }
}

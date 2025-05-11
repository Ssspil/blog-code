package com.aoxx.user;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



import java.util.Optional;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자를 저장하고 이름으로 조회한다")
    void saveAndFindByName() {
        // given
        User user = new User("홍길동");

        // when
        userRepository.save(user);
        Optional<User> found = userRepository.findByName("홍길동");

        // then
        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found.get().getName()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("없는 이름으로 조회하면 Optional.empty()를 반환한다")
    void findByNameNotExists() {
        // when
        Optional<User> result = userRepository.findByName("없는사람");

        // then
        Assertions.assertThat(result).isEmpty();
    }
}
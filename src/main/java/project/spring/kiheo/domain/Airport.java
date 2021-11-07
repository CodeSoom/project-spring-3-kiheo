package project.spring.kiheo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Entity
@Table(name="airport")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    private String airportId = "";

    @Builder.Default
    private String airportNm = "";

}

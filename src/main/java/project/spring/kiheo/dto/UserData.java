package project.spring.kiheo.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @Mapping("id")
    private Long id;

    @Email
    @Mapping("email")
    private String email;

    @Mapping("name")
    private String name;

    @Mapping("password")
    private String password = "";

    @Mapping("deleted")
    private boolean deleted;

    private String accessToken;
}

package project.spring.kiheo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserAuthentication extends AbstractAuthenticationToken {
    private final String userEmail;
    public UserAuthentication(String userEmail){
        super(authorities());
        this.userEmail = userEmail;
    }

    @Override
    public Object getCredentials(){
        return null;
    }

    @Override
    public Object getPrincipal(){//이 부분은 null이 아니어야 한다.
        return userEmail;
    }

    @Override
    public boolean isAuthenticated(){
        return true;
    }

    public String getUserEmail(){
        return userEmail;
    }

    @Override
    public String toString(){
        return "Authentication(" + userEmail + ")";
    }

    /*
    userId에 따라서 다른 권한부여
    */
    private static List<GrantedAuthority> authorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        
        //admin일 경우에 추가
        //authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }
}

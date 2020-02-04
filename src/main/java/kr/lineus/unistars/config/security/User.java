package kr.lineus.unistars.config.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public interface User<T> {
    Collection<? extends GrantedAuthority> getAuthorities();

    boolean isEnabled();

    boolean isTemporaryPassword();

    boolean isAdmin();

    String getUserId();

    String getFirstName();

    String getMiddleName();

    String getLastName();

    String getFullName();

    String getEmail();

}

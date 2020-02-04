package kr.lineus.unistars.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.lineus.unistars.entity.UserEntity;

public class UserDetailsImpl implements User<String>, UserDetails {
    private static final long serialVersionUID = 1L;
    private final String userId;
    private final String username, password, salt, firstName, middleName, lastName, fullName, email, cellPhone;
    private final boolean expired, temporaryPassword, admin;
    private final List<GrantedAuthority> authorities = new ArrayList<>();
    private List<String> companies = new ArrayList<>();

    protected UserDetailsImpl() {
        this.temporaryPassword = false;
		userId = null;
        username = password = salt = firstName = middleName = lastName = fullName = email = cellPhone = null;
        expired = true;
        admin = true;
    }

    public UserDetailsImpl(UserEntity user) {
    	this.temporaryPassword = false;
    	expired = true;
    	admin = true;
    	
    	
    	//TODO: copy data from userentity to user
    	userId = null;
    	username = password = salt = firstName = middleName = lastName = fullName = email = cellPhone = null;
    }

    //Spring UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(authorities);
    }

    //Spring UserDetails
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the salt used when hashing the password.
     *
     * @return
     */
    public String getSalt() {
        return salt;
    }

    //Spring UserDetails
    @Override
    public String getUsername() {
        return username;
    }


    //Spring UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Spring UserDetails
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //Spring UserDetails
    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }

    //Spring UserDetails
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Indicates if the password is temporary and should be changed
     *
     * @return true if the password is temporary
     */
    public boolean isTemporaryPassword() {
        return temporaryPassword;
    }

    /**
     * Indicates if the user is a global administrator
     *
     * @return true if the user is a global administrator
     */
    @Override
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Provides the internal user id from the dto which is only used as an identifier within the application
     *
     * @return A numeric identifier for the user
     */
    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Users full name formated as: First Middle Last
     *
     * @return The users full name
     */
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getEmail() {
        return email;
    }


    public String getCellPhone() {
        return cellPhone;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDetailsImpl other = (UserDetailsImpl) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserDetailsImpl{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", salt='" + salt + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", expired=" + expired +
                ", temporaryPassword=" + temporaryPassword +
                ", admin=" + admin +
                ", authorities=" + authorities +
                '}';
    }

	/**
	 * @return the companies
	 */
	public List<String> getCompanies() {
		return companies;
	}

	/**
	 * @param companies the companies to set
	 */
	public void setCompanies(List<String> companies) {
		this.companies = companies;
	}
}
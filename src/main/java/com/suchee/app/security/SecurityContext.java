package com.suchee.app.security;

import com.suchee.app.entity.UserAccount;
import jakarta.annotation.PostConstruct;
import org.apache.catalina.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Utility component for accessing the currently authenticated {@link UserAccount} and related security context information.
 *
 * <p>This class provides static helper methods to:
 * <ul>
 *     <li>Resolve a {@link UserAccount} by email via {@link UserAccountDetailService}.</li>
 *     <li>Retrieve the currently authenticated user from Spring Security's {@link SecurityContextHolder}.</li>
 * </ul>
 *
 * <p>It stores a static reference to {@link UserAccountDetailService} initialized after construction to enable
 * static access to user account resolution methods.
 *
 * <p><b>Note:</b> Extending {@link SecurityContextHolder} is unusual and not required as it only contains static methods.
 * The static methods of {@link SecurityContextHolder} can be accessed directly without subclassing.
 * Consider removing the inheritance for clarity.
 *
 * @see UserAccountDetailService
 * @see SecurityContextHolder
 */
@Component
public class SecurityContext extends SecurityContextHolder {

    private static UserAccountDetailService staticUserAccountDetailService;

    private final UserAccountDetailService userAccountDetailService;

    public SecurityContext(UserAccountDetailService userAccountDetailService) {
        this.userAccountDetailService = userAccountDetailService;
    }

    @PostConstruct
    public void init() {
        staticUserAccountDetailService = this.userAccountDetailService;
    }

    /**
     * Resolves a {@link UserAccount} by email using the injected {@link UserAccountDetailService}.
     *
     * @param userAccount email of the user account to resolve
     * @return the resolved {@link UserAccount}
     */
    public static UserAccount resolveUserAccount(String userAccount){
        return staticUserAccountDetailService.loadUserAccountWithEmail(userAccount);
    }

    /**
     * Retrieves the currently authenticated {@link UserAccount} from the security context, if present.
     *
     * @return an {@link Optional} containing the authenticated {@link UserAccount}, or empty if no authenticated user is present
     */
    public static UserAccount getCurrentUserAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null &&
                auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken)) {

            Object principal = auth.getPrincipal();

            if (principal instanceof UserAccountDetails userDetails) {
                return userDetails.getUserAccount();
            }
        }
        return null;
    }

    public static Long getCurrentUserId(){
        UserAccount account = getCurrentUserAccount();
        assert account != null;
        return account.getId();
    }


}

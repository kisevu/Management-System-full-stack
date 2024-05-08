package com.ameda.book.config;/*
*
@author ameda
@project Books
*
*/

import com.ameda.book.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        //given that we updated the context holder we're able to get the current auditor
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated() || authentication instanceof
                AnonymousAuthenticationToken){
            //if authentication object is null which means the user is not authenticated
            // or if we do not know the actual user
            return Optional.empty();
        }
        User principal = (User) authentication.getPrincipal();
        return Optional.ofNullable(principal.getId());
    }
}

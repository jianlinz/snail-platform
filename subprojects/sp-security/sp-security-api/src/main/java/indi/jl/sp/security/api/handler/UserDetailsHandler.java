package indi.jl.sp.security.api.handler;

import indi.jl.sp.security.api.pojo.SecurityUserDetail;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetailsHandler extends UserDetailsService {

    SecurityUserDetail loadUserByUsername(String username);
}

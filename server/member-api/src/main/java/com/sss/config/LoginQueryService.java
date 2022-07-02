package com.sss.config;

import com.sss.domain.Member;

import java.util.List;

public interface LoginQueryService {

    Member getLoginMember(String username);
}

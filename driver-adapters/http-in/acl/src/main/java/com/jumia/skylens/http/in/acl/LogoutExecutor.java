package com.jumia.skylens.http.in.acl;

import com.jumia.skylens.http.in.acl.authentication.AuthToken;

public interface LogoutExecutor {

    void logout(AuthToken authToken);
}

package com.soon83.interfaces.common;

import com.soon83.interfaces.memberdomain.api.RegisterMemberApi;

public class Scenario {
    public static RegisterMemberApi registerMember() {
        return new RegisterMemberApi();
    }
}

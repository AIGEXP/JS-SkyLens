package com.jumia.skylens.commons.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pt.aig.aigx.loggingcontext.enums.CommunicationTypeEnum;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Communications {

    public static final String HTTP_IN = CommunicationTypeEnum.HTTP_IN.getCommunicationTypeValue();
}

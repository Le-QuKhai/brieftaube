package com.haw.se1lab.chatdata.common.api.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NachrichtErstellung {
    private String nachricht;
    private Long chatID;
    private String senderName;
}

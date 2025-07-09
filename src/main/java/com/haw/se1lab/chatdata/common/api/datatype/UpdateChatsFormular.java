package com.haw.se1lab.chatdata.common.api.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChatsFormular {
    private List<Long> chatIds;
    private List<Long> lastNachrichtIds;
}

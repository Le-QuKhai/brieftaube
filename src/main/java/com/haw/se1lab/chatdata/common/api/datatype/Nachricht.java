package com.haw.se1lab.chatdata.common.api.datatype;

import com.haw.se1lab.chatdata.common.api.exception.MessageInvalidException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Date;

@Getter
@Embeddable
public class Nachricht
{
    private String nachricht;
    private Date sendDate;

    private Nachricht(String nachricht, Date sendDate)
    {
        this.nachricht = nachricht;
        this.sendDate = sendDate;
    }

    protected  Nachricht()
    {

    }

    public static Nachricht get(String nachricht, Date sendDate) throws MessageInvalidException
    {
        if(isValid(nachricht))
        {
            return new Nachricht(nachricht, sendDate);
        }
        else
        {
            throw new MessageInvalidException(nachricht);
        }
    }

    private static boolean isValid(String nachricht)
    {
        return nachricht.length() > 0 && nachricht.length() <= 2048;
    }
}

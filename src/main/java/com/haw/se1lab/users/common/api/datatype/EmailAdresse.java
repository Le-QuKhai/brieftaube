package com.haw.se1lab.users.common.api.datatype;

import com.haw.se1lab.users.common.api.exception.EmailInvalidException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class EmailAdresse
{
    private String lokalTeil;
    private String subDomain;
    private String hostname;
    private String topLevelDomain;

    private EmailAdresse(String lokalTeil, String subDomain, String hostname, String topLevelDomain)
    {
        this.lokalTeil = lokalTeil;
        this.subDomain = subDomain;
        this.hostname = hostname;
        this.topLevelDomain = topLevelDomain;
    }

    protected EmailAdresse()
    {

    }

    public static EmailAdresse get(String lokalTeil, String subDomain, String hostname, String topLevelDomain)
            throws EmailInvalidException {
        if(isValid(lokalTeil, subDomain, hostname, topLevelDomain))
        {
            return new EmailAdresse(lokalTeil, subDomain, hostname, topLevelDomain);
        }
        else
        {
            throw new EmailInvalidException("Die Email adresse \"" + lokalTeil + subDomain + hostname + topLevelDomain + "\" ist ungültig");
        }
    }

    public static EmailAdresse get(String lokalTeil, String hostname, String topLevelDomain)
            throws EmailInvalidException {
        return get(lokalTeil, "", hostname, topLevelDomain);
    }

    private static boolean isValid(String lokalTeil, String subDomain, String hostname, String topLevelDomain)
    {
        return !lokalTeil.isEmpty() && subDomain != null && !hostname.isEmpty() && !topLevelDomain.isEmpty();
    }

    @Override
    public String toString()
    {
        if(!subDomain.isEmpty())
            return lokalTeil + "@" + subDomain + "." + hostname + "." + topLevelDomain;
        else
        {
            return lokalTeil + "@" + hostname + "." + topLevelDomain;
        }

    }
}

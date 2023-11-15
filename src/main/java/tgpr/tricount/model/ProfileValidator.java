package tgpr.tricount.model;

import tgpr.framework.Error;
import java.util.regex.Pattern;
public class ProfileValidator {

    public static Error isValidMail(String mail) {
        if(mail == null || mail.isBlank())
            return new Error("Mail required", User.Fields.Mail);
        if (!Pattern.matches("^(.+)@(.+)$", mail))
            return new Error("invalid Mail format", User.Fields.Mail);
        return Error.NOERROR;
    }

    public static Error isValidFullname(String fullname) {
        if(fullname == null || fullname.isBlank())
            return new Error("Fullname required", User.Fields.FullName);
        if (!Pattern.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", fullname))
            return new Error("invalid Fullname", User.Fields.FullName);
        return Error.NOERROR;
    }

    public static Error isValidIban(String iban) {
        if (!Pattern.matches("^BE\\d{14}$", iban) && !iban.isBlank())
            return new Error("Invalid format. \nFormat : BE95000415698547", User.Fields.Iban);
        return Error.NOERROR;
    }
}

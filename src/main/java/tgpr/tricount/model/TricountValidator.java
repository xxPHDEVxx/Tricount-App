package tgpr.tricount.model;

import tgpr.framework.Error;
import tgpr.framework.ErrorList;

import java.util.List;

public abstract class TricountValidator {
    public static Error isValidTitle(String title) {
        if (title == null || title.length() <3)  {
            return new Error("Title must be longer than 3");
        } else  {
            return Error.NOERROR;
        }


    }
    public static Error isValidDescription(String description) {
        if (description != null && description.length() < 3)  {
            return new Error("Description must be longer than 3");
        } else  {
            return Error.NOERROR;
        }
    }


    public static List<Error> validate(Tricount tricount) {
        var errors = new ErrorList();

        // field validations
        errors.add(isValidTitle(tricount.getTitle()));
        errors.add(isValidDescription(tricount.getDescription()));


        return errors;
    }

}

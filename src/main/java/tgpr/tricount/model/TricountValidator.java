package tgpr.tricount.model;

import tgpr.framework.Error;
import tgpr.framework.ErrorList;

import java.util.List;

public abstract interface TricountValidator {
    public static Error isValidTitle(String title) {
        if (title == null || title.length() < 4)  {
            return new Error("must be longer than 3");
        } else  {
            return Error.NOERROR;
        }


    }
    public static Error isValidDescription(String description) {
        if (description != null && description.length() < 4)  {
            return new Error("must be longer than 3");
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

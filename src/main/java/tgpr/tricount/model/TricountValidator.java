package tgpr.tricount.model;

import tgpr.framework.Error;
import tgpr.framework.ErrorList;

import java.util.List;

public abstract class TricountValidator {
    public static Error isValidTitle(String title) {
        if (title == null || title.length() <3)  {
            return new Error("Title must be longer than 3", Tricount.Fields.Title);
        } else  {
            return Error.NOERROR;
        }


    }
    public static Error isValidDescription(String description) {
        if (description != "" && description.length() < 3)  {
            return new Error("Description must be longer than 3", Tricount.Fields.Description);

        } else  {
            return Error.NOERROR;
        }
    }


    public static List<Error> validate(Tricount tricount, int idTricount) {
        var errors = new ErrorList();
        // field validations
        errors.add(isValidTitle(tricount.getTitle()));
        errors.add(isValidDescription(tricount.getDescription()));

        Tricount existed  =Tricount.getByTitleAndUser(tricount.getTitle(), User.getByKey(tricount.getCreatorId()));
       if ( existed != null && existed.getId() != idTricount) {
           errors.add(new Error("already exists", Tricount.Fields.Title));
       }


        return errors;
    }

}

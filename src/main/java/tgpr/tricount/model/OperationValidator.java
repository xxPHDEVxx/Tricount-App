package tgpr.tricount.model;

import tgpr.framework.Error;
import tgpr.framework.ErrorList;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public abstract class OperationValidator {
   public static Error isvalidDate(LocalDate date){
        if(date == null)
            return new Error("date is required", Operation.Fields.OperationDate);
        if(date.isAfter(LocalDate.now()))
            return new Error("date may not be in the future", Operation.Fields.OperationDate);

        return Error.NOERROR;

    }
    public static Error isValidTitle(String title) {
        if(title == null || title.isBlank())
            return new Error("Title required", Operation.Fields.Title);
        if(!Pattern.matches("^.{3,}$", title))
            return new Error("minimum 3 chars", Operation.Fields.Title);
        return Error.NOERROR;
    }
    public static Error isValidAmount(String amount) {
        if(amount == null || amount.isBlank())
            return new Error("amount is required", Operation.Fields.Amount);
        if(!Pattern.matches("-?\\d*(,\\d*)?", amount))
            return new Error("amount must be positive", Operation.Fields.Amount);
        return Error.NOERROR;
    }
    public static Error isValideRepartitions(List<Repartition> repartitions){
       if(repartitions.isEmpty())
           return new Error("you must select at least one", Operation.Fields.Repartition);
       return Error.NOERROR;
    }
    public static List<Error> validate(Operation operation) {
        var errors = new ErrorList();
        errors.add(isValidTitle(operation.getTitle()));
        errors.add(isValidAmount(Double.toString(operation.getAmount())));
        errors.add(isvalidDate(operation.getOperationDate()));
        errors.add(isValideRepartitions(operation.getRepartitions()));
        return errors;
    }

}

import {FormGroup} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";

export function validationHandler(error: any, form: FormGroup) {
  if (error instanceof HttpErrorResponse && (error.status === 400 || error.status === 401)) {
    for (const errorFromServer of error.error.fieldErrors) {
      const formControl = form.get(errorFromServer.field);
      if (formControl) {
        formControl.setErrors({serverError: errorFromServer.message});
      }
    }
  }
}

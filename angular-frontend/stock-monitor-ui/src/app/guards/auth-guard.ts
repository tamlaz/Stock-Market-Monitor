import {inject} from "@angular/core";
import {AuthenticationService} from "../services/authentication.service";
import {Router} from "@angular/router";

export const auth = (router = inject(Router),
                     service = inject(AuthenticationService)) => {
  if (!service.validateTokenInLStorage()) {
    router.navigate(['login']);
  }
  return true;
}

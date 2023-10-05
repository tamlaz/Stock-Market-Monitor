import {ActivatedRouteSnapshot, CanActivate, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthenticationService} from "../services/authentication.service";

@Injectable({providedIn: 'root'})
export class HasRoleGuard implements CanActivate {

  constructor(private auth: AuthenticationService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles = route.data['expectedRoles'];

    const token = localStorage.getItem('token');

    const decodedToken = this.auth.getDecodedToken(token);
    const roles = decodedToken.roles.split(" ");
    for (let role of roles) {
      if (expectedRoles.includes(role)) {
        return true;
      }
    }
    this.router.navigate(['stock-list']);
    return false;
  }

}

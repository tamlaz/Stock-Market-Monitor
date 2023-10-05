export interface DecodedToken {
  header: string;
  exp: number,
  iat: number,
  iss: string,
  roles: string,
  sub: string
}

export class RegistrationRequest {
  private username!: string;
  private email!: string;
  private password!: string;

  public setUsername(p_Username: string) {
    this.username = p_Username;
  }

  public getUsername(): string {
    return this.username;
  }

  public setPassword(p_Password: string) {
    this.password = p_Password;
  }

  public getPassword(): string {
    return this.password;
  }

  public setEmail(p_Email: string) {
    this.email = p_Email;
  }

  public getEmail(): string {
    return this.email;
  }
}

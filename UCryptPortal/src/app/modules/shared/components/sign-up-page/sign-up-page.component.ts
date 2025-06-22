import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { Role } from 'src/app/models/role/role';
import { RegisterRequest } from 'src/app/models/user/register/register-request';
import { AccountService } from 'src/app/services/account.service';
import { AuthService } from 'src/app/services/auth.service';
import { RoleService } from 'src/app/services/role.service';

@Component({
  selector: 'app-sign-up-page',
  templateUrl: './sign-up-page.component.html',
  styleUrls: ['./sign-up-page.component.scss']
})
export class SignUpPageComponent implements OnInit{
  signUpForm!: FormGroup;
  role?: string;
  roles: Role[] = [];
  registerRequest!: RegisterRequest;
  submitted : boolean= false;
  showLogin : boolean= false;

  @Input('hidePopup')hidePopup: boolean;
  @ViewChild('signUpCloseBtn') signUpCloseBtn?: ElementRef;

  constructor(
    private accountService: AccountService,
    protected authService: AuthService,
    private roleService: RoleService,
    private spinnerService: NgxSpinnerService,
    private messageService: MessageService
  ){

  }
  ngOnInit(): void {
    this.getRoles();
    this.createSignUpForm();
  }


  private createSignUpForm() {
    this.signUpForm = new FormGroup({
      name: new FormControl(null, [Validators.required]),
      password: new FormControl<string>('', [Validators.required, Validators.minLength(6), Validators.maxLength(24), Validators.pattern("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,24}$")]),
      confirmPassword: new FormControl<string>('', [Validators.required]),
      email: new FormControl<string>('', [Validators.required, Validators.email]),
      role: new FormControl<Role | null>(null, [Validators.required])
    }, { validators: this.passwordConfirming });

  }


  getRoles() {
    this.roleService.getCurrentUserRoles().subscribe({
      next: (roles) => {
        this.roles = roles;
      },
      error: (error) => {
        this.roles = [];
        if (error?.error?.message) {
          this.messageService.add({severity:'error', summary:'Error Retriving data', detail: error.error.message})
        } else {
          this.messageService.add({severity:'error', detail: 'some thing went wrong!'});

        }
      }
    });
  }



  private passwordConfirming(c: AbstractControl): { mismatch: boolean } | null {
    if (c.get('password')?.value !== c.get('confirmPassword')?.value) {
      return {mismatch: true};
    }
    return null;
  }

  onSignUpSubmit() {
    this.submitted= true;
    if (this.signUpForm.valid) {
      this.registerRequest = {
        name: this.signUpForm.value.name,
        role: this.signUpForm.value.role,
        email: this.signUpForm.value.email,
        password: this.signUpForm.value.password
      };
      this.accountService.register(this.registerRequest).subscribe({
        next: () => {
          this.submitted= false;
          this.signUpForm.reset();
          this.messageService.add({severity:'success', detail: 'Registerd successfully, please wait for admin approval to login!'});
          this.signUpCloseBtn?.nativeElement.click();
        },
        error: (error) => {
          if (error?.error?.message) {
            this.messageService.add({severity:'error', detail: error.error.message});
          } else {
            this.messageService.add({severity:'error', detail: 'some thing went wrong!'});

          }
        }
      })
    } else {
      if( this.signUpForm.value.password !== this.signUpForm.value.confirmPassword)
      this.messageService.add({severity:'error', detail: 'Password Confirmation is not valid'});

    }
  }
}

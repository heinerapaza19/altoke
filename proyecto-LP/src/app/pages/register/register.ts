import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {

  formulario = {
    nombre: '',
    apellido: '',
    telefono: '',
    email: '',
    direccion: '',
    foto: '',
    username: '',
    password: '',
    confirmPassword: ''
  };

  loading = false;
  error = '';
  success = false;

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
    private router: Router
  ) {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/inicio']);
    }
  }

  onSubmit() {

    if (!this.formulario.nombre || !this.formulario.apellido) {
      this.error = 'Por favor completa nombre y apellido';
      return;
    }

    if (!this.formulario.email || !this.formulario.telefono) {
      this.error = 'Por favor completa email y teléfono';
      return;
    }

    if (!this.formulario.username || !this.formulario.password) {
      this.error = 'Por favor completa usuario y contraseña';
      return;
    }

    if (this.formulario.password !== this.formulario.confirmPassword) {
      this.error = 'Las contraseñas no coinciden';
      return;
    }

    if (this.formulario.password.length < 6) {
      this.error = 'La contraseña debe tener al menos 6 caracteres';
      return;
    }

    this.loading = true;
    this.error = '';
    this.success = false;

    const registroData = {
      username: this.formulario.username,
      password: this.formulario.password,
      nombre: this.formulario.nombre,
      apellido: this.formulario.apellido,
      telefono: this.formulario.telefono,
      email: this.formulario.email,
      direccion: this.formulario.direccion,
      foto: this.formulario.foto || 'default.jpg',
      empresa: 'AlToque',
      rol: 'CLIENTE'
    };

    this.apiService.registrar(registroData).subscribe({
      next: () => {
        this.authService.login({
          username: this.formulario.username,
          password: this.formulario.password
        }).subscribe({
          next: () => {
            this.loading = false;
            this.success = true;
            setTimeout(() => {
              this.router.navigate(['/inicio']);
            }, 1000);
          },
          error: () => {
            this.loading = false;
            this.error = 'Cuenta creada pero error al iniciar sesión. Haz login manual.';
          }
        });
      },
      error: (err) => {
        this.loading = false;
        console.error('Error al registrar:', err);
        this.error = err.error?.mensaje || 'Error al crear la cuenta.';
      }
    });
  }
}

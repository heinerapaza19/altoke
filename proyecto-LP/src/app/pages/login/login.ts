import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {

  // ✏ Modelo para enlazar el formulario (username y password)
  credentials = {
    username: '',
    password: ''
  };

  // 🔄 Para mostrar animación de carga
  loading = false;

  // ⚠ Para mostrar mensajes de error
  error = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {

    // 🟢 Si el usuario YA tiene sesión abierta → Redirige automáticamente
    if (this.authService.isAuthenticated()) {

      // Obtenemos el rol guardado en el localStorage
      const rol = this.authService.currentUser()?.rol;

      // Solo redirigimos si el rol existe
      if (rol) this.redirectByRole(rol);
    }
  }

  // 🟡 MÉTODO que se ejecuta cuando hacemos click "Iniciar sesión"
  onSubmit() {

    // ❗ Validación básica del form
    if (!this.credentials.username || !this.credentials.password) {
      this.error = '⚠ Por favor completa todos los campos';
      return;
    }

    this.loading = true;
    this.error = '';

    // 🟢 Llamamos al backend
    this.authService.login(this.credentials).subscribe({

      next: (response) => {
        this.loading = false;

        console.log("🔐 LOGIN OK:", response);

        // 🔁 Redirigir según el rol devuelto por el backend
        this.redirectByRole(response.rol);
      },

      error: (err) => {
        this.loading = false;
        this.error = err.error?.mensaje || '❌ Usuario o contraseña incorrectos';
      },
    });
  }

  // 🟣 Método central para redirigir según el rol del usuario
  private redirectByRole(rol: string | null): void {
    if (rol === 'ADMIN') {
      this.router.navigate(['/admin']);
    } else if (rol === 'REPARTIDOR') {
      this.router.navigate(['/repartidor']);
    } else {
      // Por defecto → Cliente
      this.router.navigate(['/inicio']);
    }
  }
}

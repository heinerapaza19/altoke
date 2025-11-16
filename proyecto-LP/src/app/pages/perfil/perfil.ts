import { Component, OnInit, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { getImageUrl, handleImageError, IMAGE_PLACEHOLDER_AVATAR } from '../../utils/image.utils';

@Component({
  selector: 'app-perfil',
  imports: [CommonModule, FormsModule],
  templateUrl: './perfil.html',
  styleUrl: './perfil.css',
})
export class Perfil implements OnInit {
  usuario: any = {
    idUsuario: null,
    username: '',
    rol: '',
    empresa: '',
    activo: true
  };

  editando = false;
  cargando = true;
  error = '';

  // Exponer funciones para el template
  getImageUrl = getImageUrl;
  handleImageError = handleImageError;
  IMAGE_PLACEHOLDER_AVATAR = IMAGE_PLACEHOLDER_AVATAR;

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
    private router: Router
  ) {
    // Efecto para cargar perfil cuando el usuario cambia
    effect(() => {
      const user = this.authService.currentUser();
      if (user) {
        this.cargarPerfil();
      } else {
        // Si no hay usuario autenticado, redirigir al login
        this.router.navigate(['/login'], { queryParams: { returnUrl: '/perfil' } });
      }
    });
  }

  ngOnInit() {
    // Verificar autenticación
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login'], { queryParams: { returnUrl: '/perfil' } });
      return;
    }
    
    this.cargarPerfil();
  }

  cargarPerfil() {
    const user = this.authService.currentUser();
    if (!user) {
      this.error = 'No hay usuario autenticado';
      this.cargando = false;
      return;
    }

    this.cargando = true;
    this.error = '';

    // Obtener información completa del usuario
    this.apiService.getUsuario(user.idUsuario).subscribe({
      next: (usuario) => {
        this.usuario = usuario;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar perfil:', err);
        // Si no se puede obtener de la API, usar los datos del AuthService
        this.usuario = {
          idUsuario: user.idUsuario,
          username: user.username,
          rol: user.rol,
          empresa: user.empresa,
          activo: user.activo
        };
        this.cargando = false;
        this.error = 'No se pudo cargar información adicional del usuario.';
      }
    });
  }

  guardarPerfil() {
    if (!this.usuario.idUsuario) {
      alert('No hay usuario para actualizar');
      return;
    }

    // Actualizar usuario
    this.apiService.actualizarUsuario(this.usuario.idUsuario, this.usuario).subscribe({
      next: () => {
        alert('Perfil actualizado exitosamente');
        this.editando = false;
        this.cargarPerfil(); // Recargar para asegurar datos actualizados
      },
      error: (err) => {
        console.error('Error al actualizar perfil:', err);
        alert('Error al actualizar el perfil. Por favor intenta nuevamente.');
      }
    });
  }
}

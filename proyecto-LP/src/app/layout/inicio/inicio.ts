import { Component, OnInit, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet, Router } from '@angular/router';
import { CarritoService } from '../../services/carrito.service';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { getImageUrl, handleImageError, IMAGE_PLACEHOLDER_AVATAR } from '../../utils/image.utils';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.html',
  styleUrls: ['./inicio.css'],
  imports: [CommonModule, RouterLink, RouterOutlet]
})
export class Inicio implements OnInit {

  /** ================= 🔹 VARIABLES ================= */
  carritoCount = 0;
  usuario: any = null;
  categorias: any[] = [];
  isAuthenticated = false;
  userRole: string = "CLIENTE";   // 👈 IMPORTANTE

  /** 🔁 Exponer utilidades al HTML */
  getImageUrl = getImageUrl;
  handleImageError = handleImageError;
  IMAGE_PLACEHOLDER_AVATAR = IMAGE_PLACEHOLDER_AVATAR;

  constructor(
    private carritoService: CarritoService,
    private apiService: ApiService,
    private authService: AuthService,
    private router: Router
  ) {

    /** 🎯 CARRITO EN TIEMPO REAL */
    effect(() => {
      this.carritoCount = this.carritoService.getCarritoCount()();
    });

    /** 🎯 DETECTAR LOGIN + ROL */
    effect(() => {
      this.isAuthenticated = this.authService.isAuthenticated();
      const user = this.authService.currentUser();

      if (user) {
        /** 🟢 Usuario logueado */
        this.usuario = {
          nombre: user.username,
          imagen: ''
        };

        /** 🔥 GUARDAR ROL REAL DEL TOKEN */
        this.userRole = user.rol ?? "CLIENTE";

      } else {
        /** 🔴 Usuario invitado */
        this.usuario = null;
        this.userRole = "CLIENTE";   // 👈 Visitante = cliente
      }

      console.log("🟡 Usuario:", this.usuario?.nombre ?? "Visitante");
      console.log("🟡 Rol:", this.userRole);
    });
  }

  /** 🚀 Carga inicial */
  ngOnInit() {
    this.apiService.getCategorias().subscribe({
      next: (categorias) => (this.categorias = categorias),
      error: (err) => console.error('❌ Error al cargar categorías:', err)
    });
  }

  /** 🚪 Cerrar sesión */
  cerrarSesion() {
    this.authService.logout();
  }
}

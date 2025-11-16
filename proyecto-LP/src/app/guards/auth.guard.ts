import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // 1. Verificar que esté autenticado
  if (!authService.isAuthenticated()) {
    router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }

  // 2. Obtener el rol del usuario logueado
  const userRole = authService.currentUser()?.rol;

  // 3. Verificar si la ruta exige un rol
  const requiredRole = route.data?.['role'];

  // Si hay un rol requerido y no coincide -> ❌ Acceso denegado
  if (requiredRole && userRole !== requiredRole) {
    router.navigate(['/inicio']);  // Cambia a `/forbidden` si quieres mostrar una página de error
    return false;
  }

  // Si todo está bien -> permitir acceso
  return true;
};

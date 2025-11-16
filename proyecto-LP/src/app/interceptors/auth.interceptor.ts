import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  // Endpoints públicos que NO requieren token
  const publicEndpoints = [
    '/auth/login',
    '/auth/register',
    '/',
    '/saludo',
    '/categorias',  // GET categorías es público
    '/productos',   // GET productos es público
    '/promociones'  // GET promociones es público
  ];
  
  // Si es registro, no agregar token
  if (req.url.includes('/auth/register')) {
    return next(req);
  }
  
  // Verificar si es un endpoint público (solo GET)
  const isPublicGet = req.method === 'GET' && publicEndpoints.some(endpoint => req.url.includes(endpoint));
  
  // Endpoints que SIEMPRE requieren autenticación (incluso GET)
  const protectedEndpoints = [
    '/clientes',
    '/pedidos',
    '/carrito',
    '/usuarios',
    '/repartidores',
    '/pagos',
    '/auth/logout',
    '/auth/validate'
  ];
  
  const requiresAuth = protectedEndpoints.some(endpoint => req.url.includes(endpoint)) || 
                       (!isPublicGet && req.method !== 'GET');

  // Agregar token solo si es necesario y existe
  if (token && requiresAuth) {
    const clonedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(clonedReq);
  }

  // Si requiere auth pero no hay token, dejar que la API responda con 401
  if (requiresAuth && !token) {
    // No interceptar, dejar que la API responda
    return next(req);
  }

  return next(req);
};


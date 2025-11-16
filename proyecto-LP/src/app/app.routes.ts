import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

/* ========== 📌 LAYOUT PRINCIPAL (CLIENTE) ========== */
import { Inicio } from './layout/inicio/inicio';

/* ========== 🛍️ PÁGINAS CLIENTE ========== */
import { Ventas } from './pages/ventas/ventas';
import { Productos } from './pages/productos/productos';
import { Perfil } from './pages/perfil/perfil';
import { Carrito } from './pages/carrito/carrito';
import { Pedidos } from './pages/pedidos/pedidos';
import { Promociones } from './pages/promociones/promociones';
import { Checkout } from './pages/checkout/checkout';
import { Soporte } from './pages/soporte/soporte';

/* ========== 🔐 LOGIN / REGISTER ========== */
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';

/* ========== 🛠 LAYOUT ADMIN & PANEL REPARTIDOR ========== */
import { AdminLayout } from './layout/admin-layout/admin-layout';
import { RepartidorLayout  } from './layout/repartidor-layout/repartidor-layout';


export const routes: Routes = [

  /** ⏩ LOGIN & REGISTER */
  { path: 'login', component: Login },
  { path: 'register', component: Register },

  /** ⭐ CLIENTE (Layout principal) */
  {
    path: '',
    component: Inicio,
    children: [
      { path: 'inicio', component: Ventas },
      { path: 'productos', component: Productos },
      { path: 'promociones', component: Promociones },
      { path: 'soporte', component: Soporte },
      { path: 'carrito', component: Carrito },
      { path: 'pedidos', component: Pedidos, canActivate: [authGuard] },
      { path: 'perfil', component: Perfil, canActivate: [authGuard] },
      { path: 'checkout', component: Checkout, canActivate: [authGuard] },

      { path: '', redirectTo: 'inicio', pathMatch: 'full' },
    ],
  },

  /** 🛠 PANEL ADMIN (con layout propio y router-outlet interno) */
{
  path: 'admin',
  component: AdminLayout,
  canActivate: [authGuard],
  data: { role: 'ADMIN' },
  children: [
    { path: '', loadComponent: () => import('./pages/admin/dashboard/dashboard').then(m => m.Dashboard) },

    /** PRODUCTOS LISTA */
    { path: 'productos', loadComponent: () =>
        import('./pages/admin/productos/productos').then(m => m.Productos)
    },

    /** PRODUCTOS NUEVO */
    { path: 'productos/nuevo', loadComponent: () =>
        import('./pages/admin/productos/productos-form/productos-form').then(m => m.ProductosForm)
    },

    /** PRODUCTOS EDITAR */
    { path: 'productos/:id', loadComponent: () =>
        import('./pages/admin/productos/productos-form/productos-form').then(m => m.ProductosForm)
    },

    /** RESTO DE MÓDULOS */
    { path: 'categorias', loadComponent: () => import('./pages/admin/categorias/categorias').then(m => m.Categorias) },
    { path: 'repartidores', loadComponent: () => import('./pages/admin/repartidores/repartidores').then(m => m.Repartidores) },
    { path: 'usuarios', loadComponent: () => import('./pages/admin/usuarios/usuarios').then(m => m.Usuarios) },
    { path: 'pedidos', loadComponent: () => import('./pages/admin/pedidos/pedidos').then(m => m.Pedidos) },
    { path: 'promociones', loadComponent: () => import('./pages/admin/promociones/promociones').then(m => m.Promociones) },
  ]
},





  /** 🚚 PANEL REPARTIDOR */
  {
  path: 'repartidor',
  component: RepartidorLayout,
  canActivate: [authGuard],
  data: { role: 'REPARTIDOR' },
  children: [
    { path: '', loadComponent: () => import('./pages/repartidor/pedidos/pedidos').then(m => m.Pedidos) },
    { path: 'historial', loadComponent: () => import('./pages/repartidor/historial/historial').then(m => m.Historial) },
  ]
},


  /** ❌ RUTA NO ENCONTRADA */
  { path: '**', redirectTo: 'inicio' },
];

import { Component, OnInit, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { CarritoService, CarritoItem } from '../../services/carrito.service';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { getImageUrl, handleImageError, IMAGE_PLACEHOLDER_SMALL } from '../../utils/image.utils';

@Component({
  selector: 'app-carrito',
  imports: [CommonModule, RouterLink],
  templateUrl: './carrito.html',
  styleUrl: './carrito.css',
})
export class Carrito implements OnInit {
  items: CarritoItem[] = [];
  itemsUnicos: CarritoItem[] = []; // Productos únicos para el resumen
  total: number = 0;

  // Exponer funciones para el template
  getImageUrl = getImageUrl;
  handleImageError = handleImageError;
  IMAGE_PLACEHOLDER = IMAGE_PLACEHOLDER_SMALL;

  constructor(
    private carritoService: CarritoService,
    private apiService: ApiService,
    private authService: AuthService,
    private router: Router
  ) {
    // Efecto para actualizar items cuando cambia el signal
    effect(() => {
      const itemsRaw = this.carritoService.getCarritoItems()();
      // Agrupar items duplicados por idProducto
      this.items = this.agruparItems(itemsRaw);
      // Crear lista de productos únicos para el resumen
      this.itemsUnicos = this.obtenerItemsUnicos(itemsRaw);
      this.actualizarTotal();
    });

    // Efecto para actualizar total cuando cambia el signal
    effect(() => {
      this.total = this.carritoService.getTotal()();
    });
  }

  // Agrupar items duplicados sumando sus cantidades
  agruparItems(items: CarritoItem[]): CarritoItem[] {
    const itemsAgrupados = new Map<number, CarritoItem>();

    items.forEach(item => {
      if (itemsAgrupados.has(item.idProducto)) {
        const existente = itemsAgrupados.get(item.idProducto)!;
        existente.cantidad += item.cantidad;
        existente.subtotal = existente.precio * existente.cantidad;
      } else {
        itemsAgrupados.set(item.idProducto, { ...item });
      }
    });

    return Array.from(itemsAgrupados.values());
  }

  // Obtener lista de productos únicos para el resumen
  obtenerItemsUnicos(items: CarritoItem[]): CarritoItem[] {
    const itemsUnicos = new Map<number, CarritoItem>();

    items.forEach(item => {
      if (itemsUnicos.has(item.idProducto)) {
        const existente = itemsUnicos.get(item.idProducto)!;
        existente.cantidad += item.cantidad;
        existente.subtotal = existente.precio * existente.cantidad;
      } else {
        itemsUnicos.set(item.idProducto, { ...item });
      }
    });

    return Array.from(itemsUnicos.values());
  }

  ngOnInit() {
    this.cargarCarrito();
  }

  cargarCarrito() {
    // Los signals se actualizan automáticamente, solo necesitamos cargar desde la API
    this.carritoService.cargarCarrito();
  }

  actualizarCantidad(item: CarritoItem, nuevaCantidad: number) {
    if (nuevaCantidad < 1) {
      this.eliminarItem(item.idProducto);
      return;
    }

    const itemActualizado: CarritoItem = {
      ...item,
      cantidad: nuevaCantidad,
      subtotal: item.precio * nuevaCantidad
    };

    // Eliminar y agregar de nuevo con nueva cantidad
    this.carritoService.eliminarDelCarrito(item.idProducto);
    setTimeout(() => {
      this.carritoService.agregarAlCarrito(itemActualizado, nuevaCantidad);
    }, 100);
  }

  eliminarItem(idProducto: number) {
    this.carritoService.eliminarDelCarrito(idProducto);
    setTimeout(() => {
      this.cargarCarrito();
    }, 100);
  }

  actualizarTotal() {
    this.total = this.items.reduce((sum, item) => sum + item.subtotal, 0);
  }

  confirmarPedido() {
    if (this.items.length === 0) {
      alert('El carrito está vacío');
      return;
    }
    
    // Verificar si está autenticado antes de ir a checkout
    const isAuthenticated = this.authService.isAuthenticated();
    if (!isAuthenticated) {
      if (confirm('Para realizar la compra necesitas iniciar sesión. ¿Deseas iniciar sesión ahora?')) {
        this.router.navigate(['/login'], { queryParams: { returnUrl: '/checkout' } });
      }
      return;
    }
    
    this.router.navigate(['/checkout']);
  }

}

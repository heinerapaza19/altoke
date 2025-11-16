import { Injectable, signal } from '@angular/core';
import { ApiService } from './api.service';
import { BehaviorSubject, Observable } from 'rxjs';

export interface CarritoItem {
  idProducto: number;
  nombre: string;
  cantidad: number;
  precio: number;
  subtotal: number;
  imagen?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private carritoItems = signal<CarritoItem[]>([]);
  private totalCarrito = signal<number>(0);
  private carritoCount = signal<number>(0);

  constructor(private apiService: ApiService) {
    this.cargarCarrito();
  }

  getCarritoItems() {
    return this.carritoItems.asReadonly();
  }

  getTotal() {
    return this.totalCarrito.asReadonly();
  }

  getCarritoCount() {
    return this.carritoCount.asReadonly();
  }

  cargarCarrito() {
    this.apiService.getCarrito().subscribe({
      next: (items) => {
        this.carritoItems.set(items);
        this.actualizarContadores();
      },
      error: (err) => {
        console.error('Error al cargar carrito:', err);
        this.carritoItems.set([]);
      }
    });
  }

  agregarAlCarrito(producto: any, cantidad: number = 1) {
    // Si ya es un CarritoItem, usarlo directamente
    if (producto.idProducto && producto.nombre && producto.precio !== undefined) {
      const item: CarritoItem = {
        idProducto: producto.idProducto,
        nombre: producto.nombre,
        cantidad: cantidad,
        precio: producto.precio,
        subtotal: producto.precio * cantidad,
        imagen: producto.imagen
      };

      this.apiService.agregarAlCarrito(item).subscribe({
        next: () => {
          this.cargarCarrito();
        },
        error: (err) => {
          console.error('Error al agregar al carrito:', err);
        }
      });
    } else {
      console.error('Producto inválido:', producto);
    }
  }

  eliminarDelCarrito(idProducto: number) {
    this.apiService.eliminarDelCarrito(idProducto).subscribe({
      next: () => {
        this.cargarCarrito();
      },
      error: (err) => {
        console.error('Error al eliminar del carrito:', err);
      }
    });
  }

  vaciarCarrito() {
    this.apiService.vaciarCarrito().subscribe({
      next: () => {
        this.carritoItems.set([]);
        this.totalCarrito.set(0);
        this.carritoCount.set(0);
      },
      error: (err) => {
        console.error('Error al vaciar carrito:', err);
      }
    });
  }

  private actualizarContadores() {
    const items = this.carritoItems();
    // Contar solo productos únicos, no la cantidad total de items
    const productosUnicos = new Set(items.map(item => item.idProducto));
    const count = productosUnicos.size;
    this.carritoCount.set(count);
    
    this.apiService.getTotalCarrito().subscribe({
      next: (total) => {
        this.totalCarrito.set(total);
      },
      error: (err) => {
        const total = items.reduce((sum, item) => sum + item.subtotal, 0);
        this.totalCarrito.set(total);
      }
    });
  }
}


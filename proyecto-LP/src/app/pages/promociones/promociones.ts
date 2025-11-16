import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { CarritoService } from '../../services/carrito.service';
import { getImageUrl, handleImageError, IMAGE_PLACEHOLDER_PRODUCT } from '../../utils/image.utils';

@Component({
  selector: 'app-promociones',
  imports: [CommonModule],
  templateUrl: './promociones.html',
  styleUrl: './promociones.css',
})
export class Promociones implements OnInit {
  promociones: any[] = [];
  promocionesActivas: any[] = [];
  productos: Map<number, any> = new Map();

  // Exponer funciones para el template
  getImageUrl = getImageUrl;
  handleImageError = handleImageError;
  IMAGE_PLACEHOLDER = IMAGE_PLACEHOLDER_PRODUCT;

  constructor(
    private apiService: ApiService,
    private carritoService: CarritoService
  ) {}

  ngOnInit() {
    this.cargarPromociones();
    this.cargarProductos();
  }

  cargarPromociones() {
    this.apiService.getPromociones().subscribe({
      next: (promociones) => {
        this.promociones = promociones;
        this.filtrarPromocionesActivas();
      },
      error: (err) => {
        console.error('Error al cargar promociones:', err);
      }
    });
  }

  cargarProductos() {
    this.apiService.getProductos().subscribe({
      next: (productos) => {
        productos.forEach(producto => {
          this.productos.set(producto.idProducto, producto);
        });
      },
      error: (err) => {
        console.error('Error al cargar productos:', err);
      }
    });
  }

  filtrarPromocionesActivas() {
    const hoy = new Date();
    this.promocionesActivas = this.promociones.filter(promo => {
      const inicio = new Date(promo.fechaInicio);
      const fin = new Date(promo.fechaFin);
      return hoy >= inicio && hoy <= fin;
    });
  }

  obtenerProducto(idProducto: number): any {
    return this.productos.get(idProducto);
  }

  calcularPrecioConDescuento(precioOriginal: number, descuento: number): number {
    // Asumimos que descuento es un porcentaje
    return precioOriginal * (1 - descuento / 100);
  }

  agregarAlCarrito(promocion: any) {
    const producto = this.obtenerProducto(promocion.idProducto);
    if (producto) {
      const precioConDescuento = this.calcularPrecioConDescuento(producto.precio, promocion.descuento);
      const productoConDescuento = {
        ...producto,
        precio: precioConDescuento
      };
      this.carritoService.agregarAlCarrito(productoConDescuento, 1);
      
      // Feedback visual
      const btn = event?.target as HTMLElement;
      if (btn) {
        const originalText = btn.innerHTML;
        btn.innerHTML = '<i class="fa fa-check me-1"></i> Agregado';
        btn.classList.add('btn-success');
        btn.classList.remove('btn-warning');
        setTimeout(() => {
          btn.innerHTML = originalText;
          btn.classList.remove('btn-success');
          btn.classList.add('btn-warning');
        }, 2000);
      }
    }
  }

  formatearFecha(fecha: string): string {
    const date = new Date(fecha);
    return date.toLocaleDateString('es-PE', { day: '2-digit', month: '2-digit', year: 'numeric' });
  }

}


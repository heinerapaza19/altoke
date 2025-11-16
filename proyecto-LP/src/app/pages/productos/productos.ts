import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { CarritoService } from '../../services/carrito.service';
import { getImageUrl, handleImageError, IMAGE_PLACEHOLDER_PRODUCT } from '../../utils/image.utils';

@Component({
  selector: 'app-productos',
  imports: [CommonModule, FormsModule],
  templateUrl: './productos.html',
  styleUrl: './productos.css',
})
export class Productos implements OnInit {
  productos: any[] = [];
  productosFiltrados: any[] = [];
  categorias: any[] = [];
  categoriaSeleccionada: string = 'Todos';
  busqueda: string = '';
  loading = true;
  error = '';

  // Exponer funciones para el template
  getImageUrl = getImageUrl;
  handleImageError = handleImageError;
  IMAGE_PLACEHOLDER = IMAGE_PLACEHOLDER_PRODUCT;

  constructor(
    private apiService: ApiService,
    private carritoService: CarritoService
  ) {}

  ngOnInit() {
    this.cargarCategorias();
    this.cargarProductos();
  }

  cargarCategorias() {
    this.apiService.getCategorias().subscribe({
      next: (categorias) => {
        this.categorias = categorias;
      },
      error: (err) => {
        console.error('Error al cargar categorías:', err);
      }
    });
  }

  cargarProductos() {
    this.loading = true;
    this.error = '';
    this.apiService.getProductos().subscribe({
      next: (productos) => {
        this.productos = productos;
        this.productosFiltrados = productos;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar productos:', err);
        this.error = 'Error al cargar los productos. Por favor intenta nuevamente.';
        this.productos = [];
        this.productosFiltrados = [];
        this.loading = false;
      }
    });
  }

  filtrarPorCategoria(categoria: string) {
    this.categoriaSeleccionada = categoria;
    this.aplicarFiltros();
  }

  buscar() {
    this.aplicarFiltros();
  }

  aplicarFiltros() {
    let filtrados = [...this.productos];

    // Filtro por categoría
    if (this.categoriaSeleccionada !== 'Todos') {
      filtrados = filtrados.filter(p => 
        p.categoria === this.categoriaSeleccionada
      );
    }

    // Filtro por búsqueda
    if (this.busqueda.trim()) {
      const busquedaLower = this.busqueda.toLowerCase();
      filtrados = filtrados.filter(p =>
        p.nombre.toLowerCase().includes(busquedaLower) ||
        p.descripcion?.toLowerCase().includes(busquedaLower)
      );
    }

    this.productosFiltrados = filtrados;
  }

  agregarAlCarrito(producto: any) {
    this.carritoService.agregarAlCarrito(producto, 1);
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

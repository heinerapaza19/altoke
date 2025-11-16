import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { CarritoService } from '../../services/carrito.service';

@Component({
  selector: 'app-ventas',
  imports: [CommonModule, RouterLink],
  templateUrl: './ventas.html',
  styleUrl: './ventas.css',
})
export class Ventas implements OnInit {
  categorias: any[] = [];

  constructor(
    private apiService: ApiService,
    private carritoService: CarritoService
  ) {}

  ngOnInit() {
    this.cargarCategorias();
  }

  cargarCategorias() {
    this.apiService.getCategorias().subscribe({
      next: (categorias) => {
        this.categorias = categorias;
      },
      error: (err) => {
        console.error('Error al cargar categorías:', err);
        // Categorías por defecto
        this.categorias = [
          { idCategoria: 1, nombre: 'Comida Rápida', descripcion: 'Hamburguesas, papas fritas y más' },
          { idCategoria: 2, nombre: 'Bebidas', descripcion: 'Refrescos y bebidas' },
          { idCategoria: 3, nombre: 'Postres', descripcion: 'Dulces y postres' },
          { idCategoria: 4, nombre: 'Saludable', descripcion: 'Comida saludable' },
          { idCategoria: 5, nombre: 'Platos Fuertes', descripcion: 'Platos principales' }
        ];
      }
    });
  }

  obtenerImagenCategoria(categoria: string): string {
    const imagenes: { [key: string]: string } = {
      'Comida Rápida': 'https://cdn.pixabay.com/photo/2016/03/05/19/02/burger-1239241_1280.jpg',
      'Bebidas': 'https://cdn.pixabay.com/photo/2016/11/29/06/18/coca-cola-1862017_1280.jpg',
      'Postres': 'https://cdn.pixabay.com/photo/2020/02/11/11/07/pizza-4835454_1280.jpg',
      'Saludable': 'https://cdn.pixabay.com/photo/2016/11/29/03/36/salad-1868753_1280.jpg',
      'Platos Fuertes': 'https://cdn.pixabay.com/photo/2017/12/09/08/18/burger-3000549_1280.jpg'
    };
    return imagenes[categoria] || '';
  }
}

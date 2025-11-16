import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-pedidos',
  imports: [CommonModule, RouterLink],
  templateUrl: './pedidos.html',
  styleUrl: './pedidos.css',
})
export class Pedidos implements OnInit {
  pedidos: any[] = [];

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.cargarPedidos();
  }

  cargarPedidos() {
    this.apiService.getPedidos().subscribe({
      next: (pedidos) => {
        this.pedidos = pedidos;
      },
      error: (err) => {
        console.error('Error al cargar pedidos:', err);
        this.pedidos = [];
      }
    });
  }

  formatearFecha(fecha: string): string {
    const date = new Date(fecha);
    return date.toLocaleDateString('es-PE', { 
      day: '2-digit', 
      month: '2-digit', 
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  obtenerColorEstado(estado: string): string {
    const colores: { [key: string]: string } = {
      'PENDIENTE': 'warning',
      'PREPARANDO': 'info',
      'EN_CAMINO': 'primary',
      'ENTREGADO': 'success'
    };
    return colores[estado] || 'secondary';
  }

  obtenerIconoEstado(estado: string): string {
    const iconos: { [key: string]: string } = {
      'PENDIENTE': 'fa-clock',
      'PREPARANDO': 'fa-utensils',
      'EN_CAMINO': 'fa-motorcycle',
      'ENTREGADO': 'fa-check-circle'
    };
    return iconos[estado] || 'fa-question-circle';
  }
}

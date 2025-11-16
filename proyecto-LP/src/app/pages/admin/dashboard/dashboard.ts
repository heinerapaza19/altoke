import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts';
import { ChartOptions } from 'chart.js';
import { DashboardService } from '../../../services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, NgChartsModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
})
export class Dashboard implements OnInit {

  /** ====== TARJETAS ====== */
  cards = [
    { label: 'Productos',     value: 0, icon: 'fa-solid fa-box' },
    { label: 'Pedidos',       value: 0, icon: 'fa-solid fa-file-invoice' },
    { label: 'Repartidores',  value: 0, icon: 'fa-solid fa-biking' },
    { label: 'Usuarios',      value: 0, icon: 'fa-solid fa-users' },
    { label: 'Promociones',   value: 0, icon: 'fa-solid fa-tags' },
  ];

  /** ====== DONUT (PEDIDOS POR ESTADO) ====== */
  pieData = {
    labels: ['Completados', 'Pendientes', 'Cancelados'],
    datasets: [
      {
        data: [0, 0, 0],
        backgroundColor: ['#2ecc71', '#f1c40f', '#e74c3c'],
        borderWidth: 1
      }
    ]
  };

  pieOptions: ChartOptions<'doughnut'> = {
    responsive: true,
    cutout: '60%',
    plugins: {
      legend: {
        position: 'top',
        labels: { color: 'white' }
      }
    }
  };

  /** ====== BARRAS (VENTAS POR MES) ====== */
  barData = { labels: [] as string[], datasets: [{ label: 'Ventas', data: [] }] };
  barOptions: ChartOptions<'bar'> = { responsive: true };

  /** ====== LÍNEA (CATEGORÍAS MÁS VENDIDAS) ====== */
  lineData = { labels: [] as string[], datasets: [{ label: 'Unidades', data: [] }] };
  lineOptions: ChartOptions<'line'> = { responsive: true };

  constructor(private dash: DashboardService) {}

  ngOnInit() {
    this.cargarDashboard();
  }

  cargarDashboard() {
    this.dash.getData().subscribe(data => {

      /** 🟡 TARJETAS SUPERIORES */
      this.cards[0].value = data.productos;
      this.cards[1].value = data.pedidos;
      this.cards[2].value = data.repartidores;
      this.cards[3].value = data.usuarios;
      this.cards[4].value = data.promociones;

      /** 🟢 DONUT */
      this.pieData = {
        ...this.pieData,
        datasets: [{
          ...this.pieData.datasets[0],
          data: [
            data.pedidosCompletados,
            data.pedidosPendientes,
            data.pedidosCancelados
          ]
        }]
      };

      /** 🔵 BARRAS */
      this.barData = {
        labels: data.ventasMeses,
        datasets: [{ label: 'Ventas', data: data.ventasTotales }]
      };

      /** 🔴 LÍNEA */
      this.lineData = {
        labels: data.categorias,
        datasets: [{ label: 'Unidades', data: data.unidadesVendidas }]
      };

    });
  }
}

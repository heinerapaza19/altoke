import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../../services/admin.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './productos.html',
  styleUrls: ['./productos.css'],
})
export class Productos implements OnInit {

  productos: any[] = [];
  loading = true;

  constructor(private admin: AdminService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.admin.getProductos().subscribe(data => {
      this.productos = data;
      this.loading = false;
    });
  }

 eliminar(id: number) {
    if (!confirm('¿Eliminar producto?')) return;

    this.admin.deleteProducto(id).subscribe(() => {
      this.cargar();
    });
  }
}

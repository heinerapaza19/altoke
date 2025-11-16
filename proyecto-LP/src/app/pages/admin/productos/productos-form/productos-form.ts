import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from '../../../../services/admin.service';

@Component({
  selector: 'app-productos-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './productos-form.html',
  styleUrls: ['./productos-form.css']
})
export class ProductosForm implements OnInit {

  id?: number;
  titulo = "Nuevo Producto";

  producto: any = {
    nombre: '',
    descripcion: '',
    precio: 0,
    imagen: '',
    disponible: true
  };

  constructor(
    private route: ActivatedRoute,
    private admin: AdminService,
    private router: Router
  ) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get("id"));

    if (this.id > 0) {
      this.titulo = "Editar Producto";
      this.admin.getProducto(this.id).subscribe(data => {
        this.producto = data;
      });
    }
  }

  guardar() {
    if (this.id) {
      this.admin.updateProducto(this.id, this.producto).subscribe(() => {
        this.router.navigate(['/admin/productos']);
      });
    } else {
      this.admin.saveProducto(this.producto).subscribe(() => {
        this.router.navigate(['/admin/productos']);
      });
    }
  }
}

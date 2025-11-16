import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminService {

  private apiUrl = environment.apiUrl + "/admin";

  constructor(private http: HttpClient) {}

  /* ============================================================
     🟡  PRODUCTOS
  ============================================================ */
  getProductos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/productos`);
  }

  getProducto(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/productos/${id}`);
  }

  saveProducto(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/productos`, data);
  }

  updateProducto(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/productos/${id}`, data);
  }

  deleteProducto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/productos/${id}`);
  }

  /* ============================================================
     🟣  CATEGORÍAS
  ============================================================ */
  getCategorias(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/categorias`);
  }

  /* ============================================================
     🟢  PROMOCIONES
  ============================================================ */
  getPromociones(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/promociones`);
  }

  savePromocion(data: any) {
    return this.http.post(`${this.apiUrl}/promociones`, data);
  }

  deletePromocion(id: number) {
    return this.http.delete(`${this.apiUrl}/promociones/${id}`);
  }

  /* ============================================================
     🔵  PEDIDOS
  ============================================================ */
  getPedidos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/pedidos`);
  }

  getPedido(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/pedidos/${id}`);
  }

  /* ============================================================
     🟤  REPARTIDORES
  ============================================================ */
  getRepartidores(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/repartidores`);
  }

  /* ============================================================
     🔴  USUARIOS
  ============================================================ */
  getUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/usuarios`);
  }

  getUsuario(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/usuarios/${id}`);
  }
}

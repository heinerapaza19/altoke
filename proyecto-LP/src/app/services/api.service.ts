import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // CATEGORÍAS
  getCategorias(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/categorias`);
  }

  // PRODUCTOS
  getProductos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/productos`);
  }

  getProducto(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/productos/${id}`);
  }

  // PROMOCIONES
  getPromociones(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/promociones`);
  }

  getPromocion(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/promociones/${id}`);
  }

  // CARRITO
  getCarrito(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/carrito`);
  }

  agregarAlCarrito(item: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/carrito`, item);
  }

  eliminarDelCarrito(idProducto: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/carrito/${idProducto}`);
  }

  vaciarCarrito(): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/carrito/vaciar`);
  }

  getTotalCarrito(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/carrito/total`);
  }

  // PEDIDOS
  getPedidos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/pedidos`);
  }

  getPedido(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/pedidos/${id}`);
  }

  crearPedido(idCliente: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/pedidos/crear/${idCliente}`, {});
  }

  // CLIENTES
  getClientes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/clientes`);
  }

  getCliente(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/clientes/${id}`);
  }

  crearCliente(cliente: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/clientes`, cliente);
  }

  actualizarCliente(id: number, cliente: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/clientes/${id}`, cliente);
  }

  // USUARIOS
  crearUsuario(usuario: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/usuarios`, usuario);
  }

  getUsuario(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/usuarios/${id}`);
  }

  actualizarUsuario(id: number, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/usuarios/${id}`, usuario);
  }

  // REGISTRO (crear cliente y usuario juntos)
  registrar(registroData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/auth/registro`, registroData);
  }
}

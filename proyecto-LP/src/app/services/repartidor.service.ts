import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn:'root' })
export class RepartidorService {

  private api = "http://localhost:8080";

  constructor(private http:HttpClient){}

  getMisPedidos(){
    return this.http.get<any[]>(`${this.api}/pedidos/mis-pedidos`);
  }

  cambiarEstado(id:number, estado:string){
    return this.http.put(`${this.api}/pedidos/${id}/estado/${estado}`, {});
  }

  getHistorial(){
    return this.http.get<any[]>(`${this.api}/pedidos/mis-entregas`);
  }
}

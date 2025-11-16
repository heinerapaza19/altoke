import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private url = 'http://localhost:8080/api/admin/dashboard';

  constructor(private http: HttpClient) {}

  getData(): Observable<any> {
    return this.http.get<any>(this.url).pipe(
      catchError((error) => {
        console.error("❌ Error al cargar datos del dashboard:", error);
        return throwError(() => error);
      })
    );
  }
}

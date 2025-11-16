import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  type: string;
  idUsuario: number;
  username: string;
  rol: string;
  empresa: string;
  activo: boolean;
  mensaje: string;
}

export interface User {
  idUsuario: number;
  username: string;
  rol: string;
  empresa: string;
  activo: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenKey = 'auth_token';
  private userKey = 'auth_user';
  
  private tokenSignal = signal<string | null>(this.getStoredToken());
  private userSignal = signal<User | null>(this.getStoredUser());

  // Computed signals
  isAuthenticated = computed(() => !!this.tokenSignal());
  currentUser = computed(() => this.userSignal());
  userRole = computed(() => this.userSignal()?.rol || null);

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    // Validar token al iniciar solo si existe
    // No forzar logout si el token no es válido, solo limpiar localmente
    if (this.tokenSignal()) {
      this.validateToken().subscribe({
        error: () => {
          // Solo limpiar localmente, no redirigir
          this.clearAuthLocal();
        }
      });
    }
  }

  private clearAuthLocal(): void {
    this.tokenSignal.set(null);
    this.userSignal.set(null);
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/login`, credentials)
      .pipe(
        tap(response => {
          this.setToken(response.token);
          const user: User = {
            idUsuario: response.idUsuario,
            username: response.username,
            rol: response.rol,
            empresa: response.empresa,
            activo: response.activo
          };
          this.setUser(user);
        })
      );
  }

  logout(): void {
    const token = this.tokenSignal();
    if (token) {
      this.http.post(`${environment.apiUrl}/auth/logout`, {}, {
        headers: { Authorization: `Bearer ${token}` }
      }).subscribe({
        next: () => {
          this.clearAuth();
        },
        error: () => {
          // Incluso si falla el logout en el servidor, limpiamos localmente
          this.clearAuth();
        }
      });
    } else {
      this.clearAuth();
    }
  }

  validateToken(): Observable<any> {
    const token = this.tokenSignal();
    if (!token) {
      return new Observable(observer => {
        observer.error('No token');
        observer.complete();
      });
    }
    return this.http.get(`${environment.apiUrl}/auth/validate`, {
      headers: { Authorization: `Bearer ${token}` }
    });
  }

  getToken(): string | null {
    return this.tokenSignal();
  }

  private setToken(token: string): void {
    this.tokenSignal.set(token);
    localStorage.setItem(this.tokenKey, token);
  }

  private setUser(user: User): void {
    this.userSignal.set(user);
    localStorage.setItem(this.userKey, JSON.stringify(user));
  }

  private getStoredToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  private getStoredUser(): User | null {
    const userStr = localStorage.getItem(this.userKey);
    return userStr ? JSON.parse(userStr) : null;
  }

  private clearAuth(): void {
    this.tokenSignal.set(null);
    this.userSignal.set(null);
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
    this.router.navigate(['/login']);
  }
}


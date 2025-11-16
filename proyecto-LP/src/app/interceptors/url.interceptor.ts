import { HttpInterceptorFn } from '@angular/common/http';
import { environment } from '../../environments/environment';

export const urlInterceptor: HttpInterceptorFn = (req, next) => {

  // Solo agregar base cuando es ruta relativa
  if (!req.url.startsWith('http')) {

    const base = environment.apiUrl.endsWith('/')
      ? environment.apiUrl.slice(0, -1)
      : environment.apiUrl;

    const endpoint = req.url.startsWith('/')
      ? req.url
      : '/' + req.url;

    const modifiedReq = req.clone({
      url: `${base}${endpoint}`
    });

    return next(modifiedReq);
  }

  return next(req);
};

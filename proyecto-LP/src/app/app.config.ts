import {
  ApplicationConfig,
  provideZoneChangeDetection,
  importProvidersFrom,
} from '@angular/core';
import { NgChartsModule } from 'ng2-charts';


import {
  provideHttpClient,
  withInterceptors,
  withInterceptorsFromDi,
} from '@angular/common/http';

import { routes } from './app.routes';

import {
  provideRouter,
  withComponentInputBinding,
  withInMemoryScrolling,
} from '@angular/router';

import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideClientHydration } from '@angular/platform-browser';


// Formularios (Template-driven y Reactive)
import { FormsModule, ReactiveFormsModule } from '@angular/forms';






// Interceptores del proyecto Delivery
import { urlInterceptor } from './interceptors/url.interceptor';// 1. Manipula URL
import { tokenInterceptor } from './interceptors/token.interceptor'; // 2. Agrega token
import { errorInterceptor } from './interceptors/error.interceptor'; // 3. Maneja errores globales

// ---------------------------------------------------
// CONFIGURACIÓN PRINCIPAL DE LA APLICACIÓN DELIVERY
// ---------------------------------------------------
export const appConfig: ApplicationConfig = {
  providers: [
    // 🔥 Optimiza el rendimiento agrupando eventos del DOM
    provideZoneChangeDetection({ eventCoalescing: true }),

    // 🔥 Configuración del router con scroll automático y binding de inputs desde rutas
    provideRouter(
      routes,
      withInMemoryScrolling({
        scrollPositionRestoration: 'enabled', // vuelve al scroll al retroceder
        anchorScrolling: 'enabled',           // soporte para #anchors
      }),
      withComponentInputBinding()              // permite recibir @Input() desde la URL
    ),

    // 🔥 Proveedor HTTP con interceptores en orden correcto
    provideHttpClient(
      withInterceptorsFromDi(),               // permite usar interceptores desde DI
      withInterceptors([
        urlInterceptor,      // 1. Ajusta la URL base del API
        tokenInterceptor,    // 2. Inyecta token JWT
        errorInterceptor     // 3. Manejo centralizado de errores
      ])
    ),

    // 🔥 Habilita SSR Hydration (recomendado a futuro)
    provideClientHydration(),

    // 🔥 Animaciones asincrónicas → más rendimiento
    provideAnimationsAsync(),

    // 🔥 Importación de módulos necesarios para formularios y UI
    importProvidersFrom(
      FormsModule,
      ReactiveFormsModule,
      
    ),
    importProvidersFrom(NgChartsModule)

  ],
};

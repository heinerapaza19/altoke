import { Component, OnInit, effect, AfterViewInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { CarritoService, CarritoItem } from '../../services/carrito.service';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { getImageUrl, handleImageError, IMAGE_PLACEHOLDER_SMALL } from '../../utils/image.utils';

declare var L: any;

@Component({
  selector: 'app-checkout',
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.html',
  styleUrl: './checkout.css',
  animations: [
    trigger('slideInOut', [
      transition(':enter', [
        style({ transform: 'translateX(100%)', opacity: 0 }),
        animate('300ms ease-out', style({ transform: 'translateX(0)', opacity: 1 }))
      ]),
      transition(':leave', [
        animate('300ms ease-in', style({ transform: 'translateX(100%)', opacity: 0 }))
      ])
    ])
  ]
})
export class Checkout implements OnInit, AfterViewInit, OnDestroy {
  items: CarritoItem[] = [];
  total: number = 0;
  map: any; // Público para acceso desde template
  private marker: any;
  private geocoderControl: any;
  
  // Búsqueda de direcciones
  busquedaDireccion = '';
  sugerenciasDirecciones: any[] = [];
  mostrandoSugerencias = false;
  buscandoDireccion = false;
  obteniendoUbicacion = false;
  
  // Estados del formulario
  validandoFormulario = false;
  enviandoPedido = false;
  errores: { [key: string]: string } = {};
  mostrarToast = false;
  toastMensaje = '';
  toastTipo: 'success' | 'error' | 'info' = 'info';
  
  // Progreso del formulario
  pasosCompletados = {
    datosPersonales: false,
    direccion: false,
    confirmacion: false
  };
  
  // Exponer funciones para el template
  getImageUrl = getImageUrl;
  handleImageError = handleImageError;
  IMAGE_PLACEHOLDER = IMAGE_PLACEHOLDER_SMALL;
  
  formulario = {
    nombre: '',
    numero: '',
    descripcionPedido: '',
    direccionEntrega: '',
    ciudad: 'Lima',
    referencia: '',
    latitud: 0,
    longitud: 0
  };

  constructor(
    private carritoService: CarritoService,
    private apiService: ApiService,
    private authService: AuthService,
    private router: Router
  ) {
    // Efecto para actualizar items cuando cambia el signal
    effect(() => {
      this.items = this.carritoService.getCarritoItems()();
    });

    // Efecto para actualizar total cuando cambia el signal
    effect(() => {
      this.total = this.carritoService.getTotal()();
    });

    // Obtener datos del usuario autenticado
    effect(() => {
      const user = this.authService.currentUser();
      if (user) {
        // Cargar datos del cliente si está disponible
        this.cargarDatosCliente();
      }
    });
  }

  ngOnInit() {
    this.cargarCarrito();
  }

  ngAfterViewInit() {
    // Inicializar mapa después de que la vista esté lista
    setTimeout(() => {
      this.inicializarMapa();
    }, 100);
  }

  ngOnDestroy() {
    if (this.map) {
      this.map.remove();
    }
  }

  cargarCarrito() {
    // Los signals se actualizan automáticamente, solo necesitamos cargar desde la API
    this.carritoService.cargarCarrito();
  }

  cargarDatosCliente() {
    // Obtener datos del cliente desde el perfil
    const user = this.authService.currentUser();
    if (user && user.idUsuario) {
      // Aquí podrías cargar los datos del cliente asociado
      // Por ahora usamos valores por defecto
    }
  }

  validarCampo(campo: string, valor: any): boolean {
    delete this.errores[campo];
    
    switch (campo) {
      case 'nombre':
        if (!valor || valor.trim().length < 2) {
          this.errores[campo] = 'El nombre debe tener al menos 2 caracteres';
          return false;
        }
        break;
      case 'numero':
        if (!valor || !/^[0-9]{9,12}$/.test(valor.replace(/\s/g, ''))) {
          this.errores[campo] = 'Ingresa un número de teléfono válido (9-12 dígitos)';
          return false;
        }
        break;
      case 'direccionEntrega':
        if (!valor || valor.trim().length < 5) {
          this.errores[campo] = 'La dirección debe tener al menos 5 caracteres';
          return false;
        }
        break;
      case 'ciudad':
        if (!valor || valor.trim().length < 2) {
          this.errores[campo] = 'La ciudad es requerida';
          return false;
        }
        break;
      case 'latitud':
        if (valor === 0) {
          this.errores['ubicacion'] = 'Debes seleccionar una ubicación en el mapa';
          return false;
        }
        break;
    }
    
    this.actualizarProgreso();
    return true;
  }

  validarFormularioCompleto(): boolean {
    let valido = true;
    
    valido = this.validarCampo('nombre', this.formulario.nombre) && valido;
    valido = this.validarCampo('numero', this.formulario.numero) && valido;
    valido = this.validarCampo('direccionEntrega', this.formulario.direccionEntrega) && valido;
    valido = this.validarCampo('ciudad', this.formulario.ciudad) && valido;
    valido = this.validarCampo('latitud', this.formulario.latitud) && valido;
    
    return valido;
  }

  actualizarProgreso() {
    this.pasosCompletados.datosPersonales = 
      !!this.formulario.nombre && !!this.formulario.numero && 
      !this.errores['nombre'] && !this.errores['numero'];
    
    this.pasosCompletados.direccion = 
      !!this.formulario.direccionEntrega && !!this.formulario.ciudad && 
      this.formulario.latitud !== 0 && this.formulario.longitud !== 0 &&
      !this.errores['direccionEntrega'] && !this.errores['ciudad'] && !this.errores['ubicacion'];
  }

  mostrarNotificacion(mensaje: string, tipo: 'success' | 'error' | 'info' = 'info') {
    this.toastMensaje = mensaje;
    this.toastTipo = tipo;
    this.mostrarToast = true;
    
    setTimeout(() => {
      this.mostrarToast = false;
    }, 4000);
  }

  inicializarMapa() {
    if (typeof L === 'undefined') {
      console.error('Leaflet no está cargado');
      return;
    }

    // Coordenadas por defecto (Lima, Perú)
    const latitud = this.formulario.latitud || -12.0464;
    const longitud = this.formulario.longitud || -77.0428;

    // Crear mapa
    this.map = L.map('map').setView([latitud, longitud], 13);

    // Agregar capa de OpenStreetMap
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors',
      maxZoom: 19
    }).addTo(this.map);

    // Crear marcador inicial
    this.marker = L.marker([latitud, longitud], {
      draggable: true
    }).addTo(this.map);

    // Evento cuando se mueve el marcador
    this.marker.on('dragend', (e: any) => {
      const position = this.marker.getLatLng();
      this.formulario.latitud = position.lat;
      this.formulario.longitud = position.lng;
      this.obtenerDireccionDesdeCoordenadas(position.lat, position.lng);
      this.actualizarProgreso();
      this.mostrarNotificacion('Ubicación actualizada', 'success');
    });

    // Evento cuando se hace clic en el mapa
    this.map.on('click', (e: any) => {
      const lat = e.latlng.lat;
      const lng = e.latlng.lng;
      
      // Animación suave del marcador
      this.marker.setLatLng([lat, lng], { animate: true });
      this.formulario.latitud = lat;
      this.formulario.longitud = lng;
      this.obtenerDireccionDesdeCoordenadas(lat, lng);
      this.actualizarProgreso();
      this.mostrarNotificacion('Ubicación seleccionada', 'success');
    });

    // Obtener ubicación actual del usuario
    this.obtenerUbicacionActual();
  }

  obtenerUbicacionActual() {
    if (!navigator.geolocation) {
      this.mostrarNotificacion('Tu navegador no soporta geolocalización', 'error');
      return;
    }

    this.obteniendoUbicacion = true;
    
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;
        
        this.map.setView([lat, lng], 15);
        this.marker.setLatLng([lat, lng]);
        this.formulario.latitud = lat;
        this.formulario.longitud = lng;
        this.obteniendoUbicacion = false;
        this.obtenerDireccionDesdeCoordenadas(lat, lng);
        this.mostrarNotificacion('Ubicación obtenida exitosamente', 'success');
        this.actualizarProgreso();
      },
      (error) => {
        this.obteniendoUbicacion = false;
        console.log('Error al obtener ubicación:', error);
        this.mostrarNotificacion('No se pudo obtener tu ubicación. Por favor selecciona manualmente en el mapa.', 'error');
      },
      { timeout: 10000, enableHighAccuracy: true }
    );
  }

  obtenerDireccionDesdeCoordenadas(lat: number, lng: number) {
    // Usar Nominatim (OpenStreetMap) para geocodificación inversa
    fetch(`https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`)
      .then(response => response.json())
      .then(data => {
        if (data.address) {
          const address = data.address;
          let direccion = '';
          
          // Construir dirección
          if (address.road) direccion += address.road;
          if (address.house_number) direccion += ' ' + address.house_number;
          if (address.suburb) direccion += ', ' + address.suburb;
          if (address.city || address.town || address.village) {
            this.formulario.ciudad = address.city || address.town || address.village;
          }
          
          if (direccion) {
            this.formulario.direccionEntrega = direccion;
          } else {
            this.formulario.direccionEntrega = `${lat}, ${lng}`;
          }
        }
      })
      .catch(error => {
        console.error('Error al obtener dirección:', error);
        this.formulario.direccionEntrega = `${lat}, ${lng}`;
      });
  }

  buscarDireccion() {
    const direccion = this.busquedaDireccion || this.formulario.direccionEntrega;
    if (!direccion || direccion.trim() === '') {
      return;
    }

    this.buscandoDireccion = true;
    this.mostrandoSugerencias = false;

    // Geocodificación directa (buscar dirección)
    fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(direccion)}&limit=1&addressdetails=1`)
      .then(response => response.json())
      .then(data => {
        this.buscandoDireccion = false;
        if (data && data.length > 0) {
          const resultado = data[0];
          const lat = parseFloat(resultado.lat);
          const lng = parseFloat(resultado.lon);
          
          this.map.setView([lat, lng], 16);
          this.marker.setLatLng([lat, lng]);
          this.formulario.latitud = lat;
          this.formulario.longitud = lng;
          
          // Actualizar dirección con el resultado completo
          if (resultado.display_name) {
            this.formulario.direccionEntrega = resultado.display_name;
            this.busquedaDireccion = resultado.display_name;
          }
          
          // Actualizar ciudad
          if (resultado.address) {
            this.formulario.ciudad = resultado.address.city || 
                                    resultado.address.town || 
                                    resultado.address.village || 
                                    resultado.address.municipality || 
                                    this.formulario.ciudad;
          }
          
          this.actualizarProgreso();
          this.mostrarNotificacion('Dirección encontrada', 'success');
        } else {
          this.mostrarNotificacion('No se encontró la dirección. Intenta con otra búsqueda o selecciona en el mapa.', 'error');
        }
      })
      .catch(error => {
        this.buscandoDireccion = false;
        console.error('Error al buscar dirección:', error);
        this.mostrarNotificacion('Error al buscar la dirección. Por favor intenta nuevamente.', 'error');
      });
  }

  buscarSugerencias(event: any) {
    const query = event.target.value;
    this.busquedaDireccion = query;
    
    if (!query || query.length < 3) {
      this.sugerenciasDirecciones = [];
      this.mostrandoSugerencias = false;
      return;
    }

    // Debounce: esperar 300ms antes de buscar
    clearTimeout((this as any).timeoutBusqueda);
    (this as any).timeoutBusqueda = setTimeout(() => {
      this.obtenerSugerencias(query);
    }, 300);
  }

  obtenerSugerencias(query: string) {
    fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}&limit=5&addressdetails=1&countrycodes=pe`)
      .then(response => response.json())
      .then(data => {
        this.sugerenciasDirecciones = data || [];
        this.mostrandoSugerencias = this.sugerenciasDirecciones.length > 0;
      })
      .catch(error => {
        console.error('Error al obtener sugerencias:', error);
        this.sugerenciasDirecciones = [];
        this.mostrandoSugerencias = false;
      });
  }

  seleccionarSugerencia(sugerencia: any) {
    const lat = parseFloat(sugerencia.lat);
    const lng = parseFloat(sugerencia.lon);
    
    this.busquedaDireccion = sugerencia.display_name;
    this.formulario.direccionEntrega = sugerencia.display_name;
    this.sugerenciasDirecciones = [];
    this.mostrandoSugerencias = false;
    
    // Centrar mapa en la ubicación seleccionada con animación
    this.map.flyTo([lat, lng], 16, {
      animate: true,
      duration: 1.0
    });
    
    setTimeout(() => {
      this.marker.setLatLng([lat, lng], { animate: true });
    }, 300);
    
    this.formulario.latitud = lat;
    this.formulario.longitud = lng;
    
    // Actualizar ciudad
    if (sugerencia.address) {
      this.formulario.ciudad = sugerencia.address.city || 
                              sugerencia.address.town || 
                              sugerencia.address.village || 
                              sugerencia.address.municipality || 
                              this.formulario.ciudad;
    }
    
    this.actualizarProgreso();
    this.mostrarNotificacion('Dirección seleccionada', 'success');
  }

  cerrarSugerencias() {
    setTimeout(() => {
      this.mostrandoSugerencias = false;
    }, 200);
  }

  verUbicacionSeleccionada() {
    if (this.map && this.formulario.latitud !== 0 && this.formulario.longitud !== 0) {
      this.map.setView([this.formulario.latitud, this.formulario.longitud], 15);
    }
  }

  confirmarPedido() {
    // Validar formulario completo
    if (!this.validarFormularioCompleto()) {
      this.mostrarNotificacion('Por favor completa todos los campos requeridos correctamente', 'error');
      return;
    }

    // Obtener ID del cliente desde el usuario autenticado
    const user = this.authService.currentUser();
    if (!user) {
      this.mostrarNotificacion('Debes estar autenticado para realizar un pedido', 'error');
      setTimeout(() => {
        this.router.navigate(['/login'], { queryParams: { returnUrl: '/checkout' } });
      }, 1500);
      return;
    }

    this.enviandoPedido = true;
    this.validandoFormulario = true;

    // El idCliente debería venir de la relación usuario-cliente
    // Por ahora usamos el idUsuario como referencia
    const idCliente = user.idUsuario; // Ajustar según tu lógica de negocio
    
    this.apiService.crearPedido(idCliente).subscribe({
      next: (pedido) => {
        this.enviandoPedido = false;
        this.validandoFormulario = false;
        this.mostrarNotificacion('¡Pedido creado exitosamente!', 'success');
        this.pasosCompletados.confirmacion = true;
        
        setTimeout(() => {
          this.carritoService.vaciarCarrito();
          this.router.navigate(['/pedidos']);
        }, 2000);
      },
      error: (err) => {
        this.enviandoPedido = false;
        this.validandoFormulario = false;
        console.error('Error al crear pedido:', err);
        this.mostrarNotificacion(
          err.error?.mensaje || 'Error al crear el pedido. Por favor intenta nuevamente.', 
          'error'
        );
      }
    });
  }

}


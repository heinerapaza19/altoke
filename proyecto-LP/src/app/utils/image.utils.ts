/**
 * Utilidades para manejo de imágenes
 */

export const IMAGE_PLACEHOLDER = 'https://via.placeholder.com/400x300/2d2d2d/ffc107?text=Imagen+No+Disponible';
export const IMAGE_PLACEHOLDER_SMALL = 'https://via.placeholder.com/200x150/2d2d2d/ffc107?text=Sin+Imagen';
export const IMAGE_PLACEHOLDER_PRODUCT = 'https://via.placeholder.com/300x200/2d2d2d/ffc107?text=Producto+Sin+Imagen';
export const IMAGE_PLACEHOLDER_AVATAR = 'https://via.placeholder.com/150/2d2d2d/ffc107?text=Usuario';

/**
 * Obtiene la URL de la imagen o un placeholder si no existe
 */
export function getImageUrl(imageUrl: string | null | undefined, placeholder: string = IMAGE_PLACEHOLDER): string {
  if (!imageUrl || imageUrl.trim() === '') {
    return placeholder;
  }
  
  // Verificar si la URL es válida
  try {
    new URL(imageUrl);
    return imageUrl;
  } catch {
    return placeholder;
  }
}

/**
 * Maneja errores de carga de imágenes
 */
export function handleImageError(event: Event, placeholder: string = IMAGE_PLACEHOLDER): void {
  const img = event.target as HTMLImageElement;
  if (img.src !== placeholder) {
    img.src = placeholder;
    img.onerror = null; // Prevenir loops infinitos
  }
}


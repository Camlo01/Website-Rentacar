package com.retos.rentacar.modelo.Entity.Client;

/**
 * Permisos por tipo de usuario
 * 
 * CLIENT
 * 
 * - Reservar vehiculos
 * - Hacer comentarios
 * - Eliminar comentario
 * 
 * SUPPORT
 * 
 * - Administrar reservas (cancelar o crear de usuarios tipo client)
 * - Administrar estados de vehiculos (cambiar estatus del carro)
 * 
 * ADMIN
 * 
 * - CRUD vehiculos 
 * 
 * DEVELOPER
 * 
 * - Todos los permisos
 * 
 * 
 */
public enum ClientType {
        CLIENT,
        SUPPORT,
        ADMIN,
        DEVELOPER
}

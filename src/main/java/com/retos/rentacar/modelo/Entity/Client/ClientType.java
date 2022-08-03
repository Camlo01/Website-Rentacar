package com.retos.rentacar.modelo.Entity.Client;

import com.fasterxml.jackson.annotation.JsonFormat;

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

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ClientType {
        CLIENT,
        SUPPORT,
        ADMIN,
        DEVELOPER
}

package edu.pe.upeu.delivery.DTOs;



import lombok.Data;

@Data
public class DashboardResponse {
    private long totalProductos;
    private long totalPedidos;
    private long totalRepartidores;
    private long totalUsuarios;

    private long completados;
    private long pendientes;
    private long cancelados;
}


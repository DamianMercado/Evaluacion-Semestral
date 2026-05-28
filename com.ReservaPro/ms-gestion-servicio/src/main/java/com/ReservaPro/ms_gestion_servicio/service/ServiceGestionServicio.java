package com.ReservaPro.ms_gestion_servicio.service;

public class ServiceGestionServicio {
    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private UsuarioClient usuarioClient; // Cliente Feign

    public Servicio registrarServicio(Servicio nuevoServicio) {
        // Validamos con el ms-usuario si el proveedor_id tiene el rol adecuado
        Boolean esProveedorValido = usuarioClient.esOperadorServicio(nuevoServicio.getProveedorId());

        if (Boolean.FALSE.equals(esProveedorValido)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El ID de proveedor no corresponde a un usuario con rol OperadorServicio");
        }

        // Si es válido, se guarda el servicio
        return servicioRepository.save(nuevoServicio);
    }
}

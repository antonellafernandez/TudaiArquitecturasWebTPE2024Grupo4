package com.example.microservicio_usuario.service;

import com.example.microservicio_usuario.entity.Usuario;
import com.example.microservicio_usuario.feignClients.CuentaAppFeignClient;
import com.example.microservicio_usuario.feignClients.MonopatinFeignClient;
import com.example.microservicio_usuario.feignClients.ViajeFeignClient;
import com.example.microservicio_usuario.models.CuentaApp;
import com.example.microservicio_usuario.models.Monopatin;
import com.example.microservicio_usuario.models.Viaje;
import com.example.microservicio_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CuentaAppFeignClient cuentaAppFeignClient;

    @Autowired
    MonopatinFeignClient monopatinFeignClient;

    @Autowired
    ViajeFeignClient viajeFeignClient;

    // Create
    @Transactional
    public Usuario save(Usuario usuario, String username) {
        usuario.setUsername(username);
        return usuarioRepository.save(usuario);
    }

    // Read
    @Transactional(readOnly = true)
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllHabilitados() {
        return usuarioRepository.findAllHabilitados();
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllDeshabilitados() {
        return usuarioRepository.findAllDeshabilitados();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Update
    @Transactional
    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Delete
    @Transactional
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    // Habilitar Parada
    @Transactional
    public void habilitar(Long id) {
        usuarioRepository.habilitar(id);
    }

    // Habilitar Parada
    @Transactional
    public void deshabilitar(Long id) {
        usuarioRepository.deshabilitar(id);
    }

    // Read CuentaApps
    public List<CuentaApp> getCuentaAppsById(Long id) {
        List<CuentaApp> salida = new ArrayList<CuentaApp>();
        List<Long> idCuentaApps = usuarioRepository.getIdCuentaApps(id);

        for (Long idCuenta : idCuentaApps) {
            salida.add(cuentaAppFeignClient.getCuentaById(idCuenta));
        }

        return salida;
    }

    @Transactional
    public void activarMonopatin(Long idCuenta, Long idParada, Long monopatinId) {
        CuentaApp cuenta = cuentaAppFeignClient.getCuentaById(idCuenta);
        if (cuenta.getMontoCreditos() > 0) {
            Viaje viaje = viajeFeignClient.iniciarViaje(monopatinId, LocalDateTime.now());
            Long idViaje = viaje.getId();
            viajeFeignClient.asociarCuenta(idViaje, idCuenta);
            monopatinFeignClient.reservarMonopatin(idParada, monopatinId);
        } else {
            throw new IllegalStateException("No hay suficiente saldo en la cuenta.");
        }
    }

    @Transactional
    public void pausarMonopatin(Long idMonopatin) {
        viajeFeignClient.registrarPausa(idMonopatin, LocalDateTime.now());
    }

    @Transactional
    public void finalizarViaje(Long idCuenta, Long monopatinId) {
        Monopatin monopatin = monopatinFeignClient.getMonopatinById(monopatinId);
        Long idViaje = viajeFeignClient.getViaje(monopatin.getIdViajeActivo());

        cuentaAppFeignClient.cobrarViaje(idCuenta, idViaje);

        monopatinFeignClient.finalizarRecorrido(monopatinId);
    }

    // Read Monopatin
    @Transactional(readOnly = true)
    public Monopatin getMonopatinById(Long id) {
        return monopatinFeignClient.getMonopatinById(id);
    }
}

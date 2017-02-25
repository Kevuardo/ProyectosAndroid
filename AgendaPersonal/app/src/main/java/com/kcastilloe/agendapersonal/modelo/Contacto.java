package com.kcastilloe.agendapersonal.modelo;

/**
 * Clase modelo dedicada a los contactos que se almacenarán en la BD y que el usuario utilizará en la agenda.
 *
 * @author Kevin Castillo Escudero
 */
public class Contacto {

    private int id = 0;
    private String nombre = null;
    private String telefono = null;
    private String direccion = null;
    private String email = null;
    private byte[] foto;

    /**
     * Constructor sin id (para la inserción en la BD).
     *
     * @param nombre    El nombre del contacto.
     * @param telefono  El telefono del contacto.
     * @param direccion La direccion del contacto.
     * @param email     El email del contacto.
     * @param foto      La foto del contacto.
     */
    public Contacto(String nombre, String telefono, String direccion, String email, byte[] foto) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.foto = foto;
    }

    /**
     * Constructor con id (para la recuperación de datos en la BD y posterior muestra en el ListView).
     *
     * @param id        El id del contacto.
     * @param nombre    El nombre del contacto.
     * @param telefono  El telefono del contacto.
     * @param direccion La direccion del contacto.
     * @param email     El email del contacto.
     * @param foto      La foto del contacto.
     */
    public Contacto(int id, String nombre, String telefono, String direccion, String email, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.foto = foto;
    }

    /* Getters y Setters. */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}

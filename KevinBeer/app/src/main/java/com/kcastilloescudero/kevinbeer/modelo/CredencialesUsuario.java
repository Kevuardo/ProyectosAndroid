package com.kcastilloescudero.kevinbeer.modelo;
import com.kcastilloescudero.kevinbeer.WelcomeActivity;

/**
 * Created by Kevin Castillo on 16/12/2016.
 */

public class CredencialesUsuario {

    WelcomeActivity wa = new WelcomeActivity();

    private String nick = null;
    private String pass = null;
    private String nombre = null;

    /* Se evalúa la validez de las credenciales dentro del constructor para asegurar que se crea correctamente. */
    public CredencialesUsuario(String nick, String pass, String nombre) throws Exception {
        int longitudMinima = 3;
        String todoCorrecto = "Credenciales corectas. ¡Bienvenido!";

        /* Evalúa las longitudes, y si no cumple los estándares lanza una excepción que provoca una interrupción en la construcción del nuevo objeto. */
        if (nick.length() < longitudMinima) {
            throw new Exception ("El nick de usuario debe tener una longitud mayor a 3 caracteres.");
        } else if (pass.length() < longitudMinima) {
            throw new Exception ("La contraseña debe tener una longitud mayor a 3 caracteres.");
        } else if (nombre.length() < longitudMinima) {
            throw new Exception ("El nombre de usuario debe tener una longitud mayor a 3 caracteres.");
        }

        this.nick = nick;
        this.pass = pass;
        this.nombre = nombre;
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.LoginController;
 //Contraseña aplicacion
    //prbl esqm ftmc dzxo


// la otra xnffbbkqwflvkeig
    // proyectopsp2024@gmail.com
/**
 *
 * @author Usuario
 */
public class Main {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);
        loginController.showLoginView();
    }
}


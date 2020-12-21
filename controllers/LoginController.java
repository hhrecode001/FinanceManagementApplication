/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import services.LoginService;

/**
 *
 * @author hoang
 */
public class LoginController {
    
    private LoginService loginService = new LoginService();
    
    public boolean tryLogin(String user, String password){
        return loginService.tryLogin(user,password);
    }
    
}

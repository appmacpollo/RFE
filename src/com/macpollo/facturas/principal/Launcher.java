/* 
 * Copyright (c) 2020, Avidesa MacPollo S.A.
 * Todos los Derechos Reservados.
 * 
 * Este es un software propietario y su contenido es confidencial de Avidesa MacPollo S.A y sus afiliados.
 * 
 * Toda la informacion contenida en el presente es y sigue siendo propiedad de Avidesa MacPollo S.A y sus
 * afiliados. Los conceptos intelectuales y tecnicos contenidos en este documento son propiedad de
 * Avidesa MacPollo S.A y sus afiliados y estan protegidos por secretos comerciales o leyes de derechos 
 * de autor. La difusion de esta informacion o reproduccion de este material esta estrictamente prohibida 
 * a menos que se obtenga el permiso previo escrito de Avidesa MacPollo S.A o sus afiliados.
 */

package com.macpollo.facturas.principal;

import com.macpollo.facturas.componentes.principal.Principal;
import javax.swing.UIManager;

/**
 * Clase principal que inicializa el proyecto.
 * 
 * @author Joan Romero
 */
public class Launcher
{
    /**
     * Método principal desde donde se ejecuta el proyecto.
     * 
     * @param args Argumentos enviados desde la linea de comandos.
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            System.err.println("Error con el look " + ex);
        }
        
        new Principal().setVisible(true);
    }
}

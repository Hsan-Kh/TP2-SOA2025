package service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * Implémentation du service web Calculatrice
 * Cette classe expose les opérations mathématiques via SOAP
 */

@WebService(
        name = "ICalculatrice",
        serviceName = "CalculatriceService",
        portName = "CalculatricePort",
        targetNamespace = "http://service.calculatrice.com/",
        endpointInterface = "service.ICalculatrice"
)
public class Calculatrice implements ICalculatrice {


    @Override
    @WebMethod(operationName = "somme")
    public double somme(@WebParam(name = "a") double a,
                        @WebParam(name = "b") double b) {
        System.out.println("Appel de somme(" + a + ", " + b + ")");
        return a + b;
    }


    @Override
    @WebMethod(operationName = "multiplication")
    public double multiplication(@WebParam(name = "a") double a,
                                 @WebParam(name = "b") double b) {
        System.out.println("Appel de multiplication(" + a + ", " + b + ")");
        return a * b;
    }


    @Override
    @WebMethod(operationName = "soustraction")
    public double soustraction(@WebParam(name = "a") double a,
                               @WebParam(name = "b") double b) {
        System.out.println("Appel de soustraction(" + a + ", " + b + ")");
        return a - b;
    }


    @Override
    @WebMethod(operationName = "division")
    public double division(@WebParam(name = "a") double a,
                           @WebParam(name = "b") double b) {
        System.out.println("Appel de division(" + a + ", " + b + ")");

        if (b == 0) {
            throw new ArithmeticException("Division par zéro impossible");
        }

        return a / b;
    }
}
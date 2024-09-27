package co.edu.escuelaing.jpaDemo.exception;

public class BadRequestException extends Exception{
    public BadRequestException(){
        super("Verifique los campos ingresados e intente nuevamente");
    }
}

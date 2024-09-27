package co.edu.escuelaing.jpaDemo.exception;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(Long id){
        super("La entidad con el id: "+ id + "No se encontro");
    }
}

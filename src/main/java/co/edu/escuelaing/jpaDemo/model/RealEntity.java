package co.edu.escuelaing.jpaDemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RealEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String address;

    private int price;

    private int size;

    private String description;

    public RealEntity(){}

    public RealEntity(String address, int price, int size, String description){
        this.address = address;
        this.price = price;
        this.size = size;
        this.description = description;
    }

    public String getAddress(){
        return address;
    }

    public Long getId(){
        return id;
    }

    public int getPrice(){
        return price;
    }

    public int getSize(){
        return size;
    }

    public String getDescription(){
        return description;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

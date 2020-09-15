package ru.live.kamaz_cs;

import javax.persistence.*;

@Entity
@Table(name = "Menu")
@NamedQueries({
        @NamedQuery(name = "MenuOfFood.findByPrice", query = "select c from MenuOfFood c")
})

public class MenuOfFood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String nameOfFood;
    @Column(name = "price", nullable = false)
    private double priceOfFood;
    @Column(name = "weight_in_gramm", nullable = false)
    private double weightOfFood;
    @Column(name = "discount")
    private String discountOfFood;

    public MenuOfFood() {
    }

    public MenuOfFood(String nameOfFood, double priceOfFood, double weightOfFood, String discountOfFood) {
        this.nameOfFood = nameOfFood;
        this.priceOfFood = priceOfFood;
        this.weightOfFood = weightOfFood;
        this.discountOfFood = discountOfFood;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameOfFood() {
        return nameOfFood;
    }

    public void setNameOfFood(String nameOfFood) {
        this.nameOfFood = nameOfFood;
    }

    public double getPriceOfFood() {
        return priceOfFood;
    }

    public void setPriceOfFood(double priceOfFood) {
        this.priceOfFood = priceOfFood;
    }

    public double getWeightOfFood() {
        return weightOfFood;
    }

    public void setWeightOfFood(double weightOfFood) {
        this.weightOfFood = weightOfFood;
    }

    public String getDiscountOfFood() {
        return discountOfFood;
    }

    public void setDiscountOfFood(String discountOfFood) {
        this.discountOfFood = discountOfFood;
    }

    @Override
    public String toString() {
        return "MenuOfFood{" +
                "id=" + id +
                ", nameOfFood='" + nameOfFood + '\'' +
                ", priceOfFood=" + priceOfFood +
                ", weightOfFood=" + weightOfFood +
                ", discountOfFood='" + discountOfFood + '\'' +
                '}';
    }

}

package ru.live.kamaz_cs;

import org.apache.commons.math3.util.Precision;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ChoiceByParameter {

    private double aPrice = 50;
    private double bPrice = 100;
    private double cPrice = 150;
    private double dPrice = 200;
    private double toWeight = 1000;
    private double fromWeight = 0;

    EntityManagerFactory emf;
    EntityManager em;

    public void menu() {
        Scanner sc = new Scanner(System.in);
        try {
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add new food in menu");
                    System.out.println("2: view menu");
                    System.out.println("3: fill a random menu");
                    System.out.println("4: choice by price");
                    System.out.println("5: choice by discount");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addFood(sc);
                            break;
                        case "2":
                            viewMenu();
                            break;
                        case "3":
                            fillRandomMenu(sc);
                            break;
                        case "4":
                            choiceByPrice(sc);
                            break;
                        case "5":
                            choiceByDiscount(sc);
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    public void addFood(Scanner sc) {
        System.out.println("Enter food name: ");
        String name = sc.nextLine();
        System.out.println("Enter food price: ");
        String sPrice = sc.nextLine();
        double price = Double.valueOf(sPrice);
        System.out.println("Enter food weight: ");
        String sWeight = sc.nextLine();
        double weight = Double.valueOf(sWeight);
        System.out.println("Enter food discount: ");
        String discount = sc.nextLine();

        em.getTransaction().begin();
        try {
            MenuOfFood mf = new MenuOfFood(name, price, weight, discount);
            em.persist(mf);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    private void choiceByPrice(Scanner sc) {
        System.out.println("1: Select from 1 to 50: ");
        System.out.println("2: Select from 50 to 100: ");
        System.out.println("3: Select from 100 to 150: ");
        System.out.println("4: Select from 150 to 200: ");
        String sChoice = sc.nextLine();
        int choice = Integer.parseInt(sChoice);

        Query query = em.createNamedQuery("MenuOfFood.findByPrice", MenuOfFood.class);
        List<MenuOfFood> mf = query.getResultList();
        for (MenuOfFood m : mf) {
            if (choice == 1) {
                if (m.getPriceOfFood() <= aPrice) {
                    fromWeight += m.getWeightOfFood();
                    if (fromWeight <= toWeight) System.out.println(m);
                }
            } else if (choice == 2) {
                if (m.getPriceOfFood() <= bPrice && m.getPriceOfFood() >= aPrice) {
                    fromWeight += m.getWeightOfFood();
                    if (fromWeight <= toWeight) System.out.println(m);
                }
            } else if (choice == 3) {
                if (m.getPriceOfFood() <= cPrice && m.getPriceOfFood() >= bPrice) {
                    fromWeight += m.getWeightOfFood();
                    if (fromWeight <= toWeight) System.out.println(m);
                }
            } else if (choice == 4) {
                if (m.getPriceOfFood() <= dPrice && m.getPriceOfFood() >= cPrice) {
                    fromWeight += m.getWeightOfFood();
                    if (fromWeight <= toWeight) System.out.println(m);
                }
            } else if (choice > 4) continue;
        }
        fromWeight = 0;
    }

    private void choiceByDiscount(Scanner sc) {
        System.out.println("1: Select with discount: ");
        System.out.println("2: Select without discount: ");
        String sChoice = sc.nextLine();
        int choice = Integer.parseInt(sChoice);

        Query query = em.createNamedQuery("MenuOfFood.findByPrice", MenuOfFood.class);
        List<MenuOfFood> mf = query.getResultList();
        for (MenuOfFood m : mf) {
            if (choice == 1) {
                if (m.getDiscountOfFood().equals("yes")) {
                    fromWeight += m.getWeightOfFood();
                    if (fromWeight <= toWeight) System.out.println(m);
                }
            } else if (choice == 2) {
                if (m.getDiscountOfFood().equals("no")) {
                    fromWeight += m.getWeightOfFood();
                    if (fromWeight <= toWeight) System.out.println(m);
                }
            } else if (choice > 2) continue;
        }
        fromWeight = 0;
    }

    private void viewMenu() {
        Query query = em.createNamedQuery("MenuOfFood.findByPrice", MenuOfFood.class);
        List<MenuOfFood> list = query.getResultList();

        for (MenuOfFood c : list)
            System.out.println(c);
    }

    private void fillRandomMenu(Scanner sc) {
        System.out.println("Enter count of menu");
        String a = sc.nextLine();
        int b = Integer.parseInt(a);

        em.getTransaction().begin();
        try {
            for (int i = 1; i <= b; i += 1) {
                MenuOfFood mf = new MenuOfFood(randomName(), Precision.round(randomPrice(), 2),
                        Precision.round(randomWeight(), 2), randomDiscount());
                em.persist(mf);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    private final String[] NAMES_OF_FOOD = {"Устрица", "Рак", "Жареный барашек", "Подстреленый лось на костре", "Индюк в перце",
            "Два индюка в перце", "Картошка печенная", "Жареная картошка", "Печень бизона", "Печень классного руководителя",
            "Печень декана", "Пюрешка в соусе", "Пюрешка без соуса", "Беляш", "Каклета", "Мясо в соусе Кровавый перегар", "Мюсли",
            "Дуля с маком"};

    private final Random RND = new Random();

    private String randomName() {
        return NAMES_OF_FOOD[RND.nextInt(NAMES_OF_FOOD.length)];
    }

    private double randomPrice() {
        return Math.random() * 200;
    }

    private double randomWeight() {
        return Math.random() * 100;
    }

    private String randomDiscount() {
        String[] disc = {"yes", "no"};
        return disc[RND.nextInt(disc.length)];
    }

}

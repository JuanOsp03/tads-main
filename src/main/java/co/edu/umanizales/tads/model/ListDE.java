package co.edu.umanizales.tads.model;

import lombok.Data;

import java.util.LinkedList;

@Data
public class ListDE {
    private NodeDE headDE;
    private int size;

    LinkedList<Pet> pets;
}

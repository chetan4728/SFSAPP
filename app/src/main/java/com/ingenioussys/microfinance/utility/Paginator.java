package com.ingenioussys.microfinance.utility;

import com.ingenioussys.microfinance.model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Paginator {

    List<Item> data;
    public static final int ITEMS_PER_PAGE = 5;
    public Paginator(List<Item> data) {
        this.data = data;
    }

    public int getTotalPages() {
        int remainingItems=data.size() % ITEMS_PER_PAGE;
        if(remainingItems>0)
        {
            return data.size() / ITEMS_PER_PAGE;
        }
        return (data.size() / ITEMS_PER_PAGE)-1;

    }

    public List<Item> getCurrentGalaxys(int currentPage) {
        int startItem = currentPage * ITEMS_PER_PAGE;
        int lastItem = startItem + ITEMS_PER_PAGE;

        List<Item> currentGalaxys = new ArrayList<>();

        //LOOP THRU LIST OF GALAXIES AND FILL CURRENTGALAXIES LIST
        try {
            for (int i = 0; i < data.size(); i++) {

                //ADD CURRENT PAGE'S DATA
                if (i >= startItem && i < lastItem) {
                    currentGalaxys.add(data.get(i));
                }
            }
            return currentGalaxys;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

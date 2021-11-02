package com.example.project531.Helper;

import android.content.Context;
import android.widget.Toast;

import androidx.arch.core.internal.SafeIterableMap;

import com.example.project531.Domain.FoodDomain;
import com.example.project531.Interface.ChangeNumberItemsListener;
import com.example.project531.responsedata.responseProduct;

import java.util.ArrayList;
public class ManagementCart {

    public ManagementCart(){};
    private Context context;
    private TinyDB tinyDB;
    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    public void insertFood(responseProduct item) {
        ArrayList<responseProduct> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getProductName().equals(item.getProductName())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if (existAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
            Toast.makeText(context, "Al ready Added to your Cart", Toast.LENGTH_SHORT).show();
        } else {
            listFood.add(item);
            Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
        }
      //  listFood.add(item);
        tinyDB.putListObject("CardList", listFood);


    }


    public ArrayList<responseProduct> getListCart() {
        return tinyDB.getListObject("CardList");
    }
    public void minusNumberFood(ArrayList<responseProduct> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listfood.get(position).getNumberInCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemsListener.changed();
    }

    public void plusNumberFood(ArrayList<responseProduct> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CardList", listfood);
        changeNumberItemsListener.changed();
    }

    /*public int getTotalFee() {
        ArrayList<responseProduct> listfood2 = getListCart();
        int fee = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            fee = (int) (fee + (listfood2.get(i).getFee() * listfood2.get(i).getNumberInCart()));
        }
        return fee;
    }
*/

    public int getTotalFee() {
        ArrayList<responseProduct> listfood2 = getListCart();
        int fee = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            fee = fee + (listfood2.get(i).getSalePrice() * listfood2.get(i).getNumberInCart());
        }
        return fee;
    }
}

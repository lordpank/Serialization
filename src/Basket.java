import java.io.*;
import java.text.DecimalFormat;

public class Basket implements Serializable {
    DecimalFormat dF = new DecimalFormat("0.00");
    private final String[] products;
    private final double[] price;
    private final int[] basket;

    public Basket(String[] products, double[] price) {
        Basket basket1 = loadFromBinFile(new File("basket.bin"));
        if (basket1 != null) {
            this.products = basket1.getProducts();
            this.price = basket1.getPrice();
            this.basket = basket1.getBasket();
        } else {
            this.products = products;
            this.price = price;
            this.basket = new int[products.length];
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket result = null;
        if (file.exists() & file.length() != 0) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                result = (Basket) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                result = null;
            }
        }
        return result;
    }

    public void printCart() {
        System.out.println("Корзина");
        double sum = 0;
        for (int i = 0; i < basket.length; i++) {
            if (basket[i] != 0) {
                System.out.println(products[i] + " " + basket[i] + " шт " + dF.format(price[i]) + " руб/шт "
                        + dF.format(basket[i] * price[i]) + " руб");
                sum += basket[i] * price[i];
            }
        }
        System.out.println("Итог: " + dF.format(sum));
    }

    public void addToCart(int productNum, int amount) {
        basket[productNum] += amount;
    }

    public void saveBin(File file) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getProducts() {
        return products;
    }

    public double[] getPrice() {
        return price;
    }

    public int[] getBasket() {
        return basket;
    }
}
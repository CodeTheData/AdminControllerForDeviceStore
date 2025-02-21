package models;

public class Device {

    private String brand;
    private String nameDevice;
    private String typeDevice;
    private int ram;
    private int ssd;
    private int price;
    private int quantity;

    public Device(String typeDevice, String brand, String nameDevice, int ram, int ssd, int price, int quantity) {
        this.typeDevice = typeDevice;
        this.brand = brand;
        this.nameDevice = nameDevice;
        this.ram = ram;
        this.ssd = ssd;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTypeDevice() {
        return typeDevice;
    }

    public String getBrand() {
        return brand;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public int getRam() {
        return ram;
    }

    public int getSsd() {
        return ssd;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Device{" +
                "brand='" + brand + '\'' +
                ", nameDevice='" + nameDevice + '\'' +
                ", typeDevice='" + typeDevice + '\'' +
                ", ram=" + ram +
                ", ssd=" + ssd +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

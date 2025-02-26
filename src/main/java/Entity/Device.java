package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "devices")

public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type_device")
    private String typeDevice;

    @Column(name = "brand")
    private String brand;

    @Column(name = "name_device")
    private String nameDevice;

    @Column(name = "ram")
    private int ram;

    @Column(name = "ssd")
    private int ssd;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    public Device() {
    }

    public Device(String typeDevice, String brand, String nameDevice, int ram, int ssd, int price, int quantity) {
        this.typeDevice = typeDevice;
        this.brand = brand;
        this.nameDevice = nameDevice;
        this.ram = ram;
        this.ssd = ssd;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", typeDevice='" + typeDevice + '\'' +
                ", brand='" + brand + '\'' +
                ", nameDevice='" + nameDevice + '\'' +
                ", ram=" + ram +
                ", ssd=" + ssd +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

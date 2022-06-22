package persistence.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "number_of_rooms")
    private int numberOfRooms;

    @Column(name = "extra_bed")
    private boolean extraBed;

    @Column(name = "rooms_available")
    private int roomsAvailable;

    @Column(name = "price")
    private double price;

    @ManyToMany(mappedBy = "roomSet")
    private Set<Hotel> hotelSet;

    public Room(String type, int numberOfRooms, boolean extraBed, int roomsAvailable) {
        this.type = type;
        this.numberOfRooms = numberOfRooms;
        this.extraBed = extraBed;
        this.roomsAvailable = roomsAvailable;
    }

    public Room() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public boolean isExtraBed() {
        return extraBed;
    }

    public void setExtraBed(boolean extraBed) {
        this.extraBed = extraBed;
    }

    public int getRoomsAvailable() {
        return roomsAvailable;
    }

    public void setRoomsAvailable(int roomsAvailable) {
        this.roomsAvailable = roomsAvailable;
    }

    public Set<Hotel> getHotelSet() {
        return hotelSet;
    }

    public void setHotelSet(Set<Hotel> hotelSet) {
        this.hotelSet = hotelSet;
    }

    @Override
    public String toString() {
        return "Room: " + type + ", number of rooms:" + numberOfRooms + ", extra bed: " +
                extraBed + ", price: " + price + ",number of rooms available : " + roomsAvailable;
    }
}

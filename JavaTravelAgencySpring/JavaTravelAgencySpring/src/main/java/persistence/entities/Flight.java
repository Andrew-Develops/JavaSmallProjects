package persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Date;

@NamedQueries({
        @NamedQuery(name = "flightByNumber", query = "select flight from Flight flight where flightNumber= :flightNumber"),
        @NamedQuery(name = "countFlightNumber", query = "select count(flightNumber) from Flight where flightNumber= :flightNumber"),
        @NamedQuery(name = "deleteFlight", query = "delete from Flight where flightNumber= :flightNumber"),
        @NamedQuery(name = "updateSeatsAvailable", query = "update Flight set seatsAvailable = seatsAvailable - :numberOfPeople where flightNumber= :flightNumber"),
})
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "departure_time")
    private Timestamp departureTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departure_airport")
    private Airport departureAirport;

    @Column(name = "arriving_date")
    private Date arrivingDate;

    @Column(name = "arriving_time")
    private Timestamp arrivingTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arriving_airport")
    private Airport arrivingAirport;

    @Column(name = "price")
    private double price;

    @Column(name = "seats_available")
    private int seatsAvailable;


    public Flight(String flightNumber, Date departureDate, Timestamp departureTime, Date arrivingDate, Timestamp arrivingTime, double price, int seatsAvailable,
                  Airport departureAirport, Airport arrivingAirport) {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivingDate = arrivingDate;
        this.arrivingTime = arrivingTime;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
        this.departureAirport = departureAirport;
        this.arrivingAirport = arrivingAirport;
    }

    public Flight() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Date getArrivingDate() {
        return arrivingDate;
    }

    public void setArrivingDate(Date arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    public Timestamp getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(Timestamp arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public Airport getArrivingAirport() {
        return arrivingAirport;
    }

    public void setArrivingAirport(Airport arrivingAirport) {
        this.arrivingAirport = arrivingAirport;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    @Override
    public String toString() {
        return "Flight: " + flightNumber + "departure date: " + departureDate + ",time: " + departureTime +
                ",departing from airport: " + departureAirport + " ,arriving date: " + arrivingDate + ",time: "
                + arrivingTime + " arriving in airport: " + arrivingAirport + ", price:" + price +
                ",number of seats available:" + seatsAvailable;
    }
}

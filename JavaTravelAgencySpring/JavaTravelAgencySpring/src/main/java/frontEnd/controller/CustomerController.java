package frontEnd.controller;

import business.dto.CustomerDTO;
import business.dto.PurchasedTripDTO;
import business.dto.RoomDTO;
import business.dto.TripDTO;
import business.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;
    @Autowired
    TripService tripService;
    @Autowired
    RoomService roomService;
    @Autowired
    PurchasedTripService purchasedTripService;

    //inseram un customer
    @PostMapping(path = "/insertCustomer")
    public ResponseEntity insertCustomer(@RequestBody CustomerDTO customerDTO) {
        //luam parola din accountDTO si o trecem prin metoda cryptPassword pentru a ajunge o parolă criptata in baza de date
        customerDTO.getAccountDTO().setPassword(accountService.cryptPassword(customerDTO.getAccountDTO().getPassword()));
        //verificam daca exista deja un cont cu acest userName si parola
        if (accountService.countUserName(customerDTO.getAccountDTO().getUserName()) != 0) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("This user_name already registered.");
        } else if (customerService.countEmail(customerDTO.getEmail()) != 0) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("This email already registered.");
        } else {
            customerService.insertCustomerDTO(customerDTO);
            return ResponseEntity.ok("Customer added.");
        }
    }

    //schimba coloana din baza de date logged_in pentru in functie de userName, password de la 0 la 1
    @PutMapping(path = "/logIn")
    public ResponseEntity logIn(@RequestParam String userName, String password) {
        String cryptPassword = accountService.cryptPassword(password);
        //verificam daca contul exista de la inceput pentru a nu trece prin tot procesul pana in baza de date
        if (customerService.findCustomerAccount(userName, cryptPassword) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong input for 'UserName' or 'password'!");
        }
        //parola criptata impreuna cu userName o introducem in metoda checkRegistration sa vedem daca acel cont exista in baza de date
        if (cryptPassword.equals(accountService.checkRegistration(userName, cryptPassword))) {
            //acum schimbam statutul userului din notLoggedIn in LoggedIn (de la 0 la 1)
            accountService.updateUserLogIn(true, userName);
            return ResponseEntity.ok("User " + userName + "logged in successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong input for 'UserName' or 'password'!");
        }
    }

    //schimba coloana din baza de date logged_in pentru in functie de userName, password de la 1 la 0
    @PutMapping(path = "/logOut")
    public ResponseEntity logOut(@RequestParam String userName) {
        int result = accountService.updateUserLogIn(false, userName);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userName + " can't find an account with this user name.");
        } else {
            return ResponseEntity.ok(userName + " logged out successfully.");
        }
    }

    //afisam un customer dupa email
    @GetMapping(path = "/getCustomerByEmail")
    public ResponseEntity getCustomerByEmail(@RequestParam String email) {
        CustomerDTO customerDTO = customerService.findCustomerByEmail(email);
        if (customerDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There's no account with this email.");
        } else {
            return ResponseEntity.ok(customerDTO);
        }
    }

    //afisam un customer dupa userName
    @GetMapping(path = "/getCustomerByUserName")
    public ResponseEntity getCustomerByUserName(@RequestParam String userName) {
        CustomerDTO customerDTO = customerService.findCustomerByUserName(userName);
        if (customerDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There's no account with this user name.");
        } else {
            return ResponseEntity.ok(customerDTO);
        }
    }

    //afisam un customer dupa userName si password
    @GetMapping(path = "/findCustomerAccount")
    public ResponseEntity findCustomerAccount(@RequestParam String userName, String password) {
        String cryptPassword = accountService.cryptPassword(password);
        CustomerDTO customerDTO = customerService.findCustomerAccount(userName, cryptPassword);
        if (customerDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There's no account with this user name and password.");
        } else {
            return ResponseEntity.ok(customerDTO);
        }
    }

    //stergem un customer dupa id
    @DeleteMapping(path = "/deleteCustomer")
    public ResponseEntity deleteCustomer(@RequestParam int id) {
        int result = customerService.deleteCustomer(id);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There's no account with this id.");
        } else {
            return ResponseEntity.ok("Customer deleted.");
        }
    }

    @PostMapping(path = "/purchasedTrip")
    public ResponseEntity purchaseTrip(@RequestBody String userName, String tripToPurchase, int numberOfAdults, int numberOfChildren, int singleRooms, int doubleRooms) {
        TripDTO tripDTO = tripService.findTripByName(tripToPurchase);
        if (tripDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No trip with the name: " + tripToPurchase + " was found in database");
        }
        CustomerDTO customerDTO = customerService.findCustomerByUserName(userName);
        if (customerDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with the user name: " + userName + " was found in database");
        }
        if (!customerDTO.getAccountDTO().isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Log in before you make a purchase!");
        }
        if (!tripService.checkAvailability(tripDTO)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("There are no trips available at this moment");
        }
        tripDTO.setNumberOfAdults(numberOfAdults);
        tripDTO.setNumberOfChildren(numberOfChildren);

        //pentru a calcula pretul avem nevoie sa stim cate camere eu fost inchiriate
        Set<RoomDTO> roomDTOSet = new HashSet<>();
        RoomDTO singleRoomDTO = roomService.findRoomByType("single");
        roomDTOSet.add(singleRoomDTO);
        RoomDTO doubleRoomDTO = roomService.findRoomByType("double");
        roomDTOSet.add(doubleRoomDTO);

        PurchasedTripDTO purchasedTripDTO = new PurchasedTripDTO();
        purchasedTripDTO.setCustomerDTO(customerDTO);
        purchasedTripDTO.setTripDTO(tripDTO);
        //setam numarul de camere cumparate
        purchasedTripDTO.getTripDTO().getStayingHotelDTO().setRoomDTOSet(roomDTOSet);
        //calculam pretul total in functie de numarul de camere
        purchasedTripDTO.setTotalPrice((singleRoomDTO.getPrice() * singleRooms) + (doubleRoomDTO.getPrice() * doubleRooms));
        purchasedTripService.insertPurchasedTrip(purchasedTripDTO);
        return ResponseEntity.ok("Trip purchased");
    }
}



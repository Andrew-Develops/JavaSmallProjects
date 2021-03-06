package business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.AccountDAO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AccountService {

    @Autowired
    AccountDAO accountDAO;

    public int deleteAccountByUserName(String userName) {
        return accountDAO.deleteAccount(userName);
    }

    //setam statusul unui user logged in true sau false in functie de maparea pe care o sa o cream in controller
    public int updateUserLogIn(boolean loggedIn, String userName) {
        return accountDAO.updateUserLogIn(loggedIn, userName);
    }

    //updatam username-ul unui user
    public int changeUserName(String newUsername, String userName) {
        return accountDAO.changeUserName(newUsername, userName);
    }

    //numaram cati useri au acelasi userName in baza de date
    public long countUserName(String userName) {
        return accountDAO.countUserName(userName);
    }

    //verificam daca mai exista un user nu acelasi nume si parola in baza de date
    public String checkRegistration(String userName, String password) {
        return accountDAO.checkRegistration(userName, password);
    }

    //metoda care cripteaza parola inainte sa ajunga in baza de date
    public String cryptPassword(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, messageDigest.digest(password.getBytes()));
        String cryptedPassword = bigInteger.toString();

        return cryptedPassword;
    }

}

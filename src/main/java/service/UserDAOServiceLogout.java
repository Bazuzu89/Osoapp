package service;

import DAO.UserDAO;
import exceptions.EmailConflictException;

public class UserDAOServiceLogout extends UserDAOService {
    public UserDAOServiceLogout() {

    }

    @Override
    void validate(String email) throws EmailConflictException {

    }
}

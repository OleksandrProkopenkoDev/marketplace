package ua.tc.marketplace.service;

import ua.tc.marketplace.model.auth.AuthRequest;
import ua.tc.marketplace.model.auth.AuthResponse;

/**
 * Service interface defining operations for managing advertisements. Includes methods for
 * retrieving, creating, updating, and deleting advertisements.
 */
public interface AuthService {

    AuthResponse authentificate(AuthRequest requestBody) ;

}
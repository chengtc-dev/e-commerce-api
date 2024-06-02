package dev.chengtc.ecommerceapi.exception.member;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String name) {
        super("Role with name " + name + " not found");
    }

}

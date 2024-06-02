package dev.chengtc.ecommerceapi.exception.member;

public class MemberExistsException extends RuntimeException {

    public MemberExistsException(String email) {
        super("Member with email " + email + " already exists");
    }
}

package com.springboot.vulnerableapp.cwe200;

public class BankAccount {
    private final String userName;
    private final String accountNumber;

    public BankAccount(String userName, String accountNumber) {
        this.userName = userName;
        this.accountNumber = accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

}

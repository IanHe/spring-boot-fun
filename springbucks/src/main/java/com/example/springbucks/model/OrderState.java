package com.example.springbucks.model;

public enum OrderState {
// state need to be inorder, init -> paid -> brewing -> brewed -> taken
    INIT, PAID, BREWING, BREWED, TAKEN, CANCELLED
}

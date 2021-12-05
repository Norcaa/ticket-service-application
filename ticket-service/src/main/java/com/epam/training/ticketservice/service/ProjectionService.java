package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Projection;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ProjectionService {

    Projection projection() throws ParseException;

    //Projection createProjection(String movieTitle, String roomName, String startDate) throws ParseException;
    String createProjection(String movieTitle, String roomName, String startDate) throws ParseException;

    List<Projection> getProjections();

    void deleteProjection(Projection projectionToDelete);

    Date formatToDate(String date) throws ParseException;

    Date addToDate(String date, Integer hour, Integer minute) throws ParseException;

    String canCreate(String movieTitle, String roomName, String startDate) throws ParseException;

    //void subscribe(Observer observer);
}

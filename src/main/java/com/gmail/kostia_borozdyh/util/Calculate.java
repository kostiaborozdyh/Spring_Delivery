package com.gmail.kostia_borozdyh.util;


import com.gmail.kostia_borozdyh.dto.PointDTO;
import com.gmail.kostia_borozdyh.entity.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Calculate {
    private Calculate() {

    }

    public static Integer avgPrice(Integer distance) {
        if (distance == 0) return 100;
        return distance * 15 / 10;
    }

    public static Integer deliveryPrice(Integer distance, Integer volume, Integer weight) {
        return Math.max(avgPrice(distance) * weight * volume / 10, 100);
    }

    public static Integer volume(Integer height, Integer length, Integer width) {
        return height * length * width;
    }

    public static LocalDate arrivalTime(Integer distance) {
        distance = distance / 400 + 1;
        return LocalDate.now().plusDays(distance);
    }

    public static LocalDate newArrivalTime(Date dateOfCreate, Date dateOfArrival) {
        return LocalDate.now().plusDays(diffDays(convertToLocalDate(dateOfCreate), convertToLocalDate(dateOfArrival)));
    }

    public static int diffDays(LocalDate dateOfSending, LocalDate dateOfArrival) {
        int length1 = dateOfSending.getDayOfYear();
        int length2 = dateOfArrival.getDayOfYear();
        if (dateOfArrival.getYear() != dateOfSending.getYear()) {
            return length2 - length1 + 365;
        } else {
            return length2 - length1;
        }
    }

    public static Set<String> cityFromSet(List<Order> orderList) {
        Set<String> stringSet = new HashSet<>();
        for (Order order :
                orderList) {
            stringSet.add(order.getCityFrom());
        }
        return stringSet;
    }

    public static Set<String> cityToSet(List<Order> orderList) {
        Set<String> stringSet = new HashSet<>();
        for (Order order :
                orderList) {
            stringSet.add(order.getCityTo());
        }
        return stringSet;
    }

    public static <T> List<Integer> getPaginationList(List<T> inputList) {
        return getPaginationList(inputList.size());
    }

    public static List<Integer> getPaginationList(Integer count) {
        List<Integer> list = new ArrayList<>();
        int a = 0;
        if (count % 5 != 0) a = 1;
        int length = count / 5 + a;
        for (int i = 0; i < length; i++) {
            list.add(i + 1);
        }
        if (count <= 5) return null;
        else return list;
    }

    public static <T> List<T> getFiveElements(List<T> inputList, int index) {
        List<T> list = new ArrayList<>();
        int lastIndex = index * 5;
        if (inputList.size() < index * 5) {
            lastIndex = inputList.size() % ((index - 1) * 5) + ((index - 1) * 5);
        }
        for (int i = (index - 1) * 5; i < lastIndex; i++) {
            list.add(inputList.get(i));
        }
        return list;
    }

    public static <T> int pageId(int id, List<T> list) {
        if (id == 0) {
            id = 1;
        } else if (id == list.size() + 1) {
            id = list.size();
        }
        return id;
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static PointDTO getPointAtTheMoment(Order order) {
        PointDTO cityFromPoint = GoogleMaps.getCityCoordinates(order.getCityFrom());
        PointDTO cityToPoint = GoogleMaps.getCityCoordinates(order.getCityTo());
        int days = diffDays(convertToLocalDate(order.getDateOfSending()), convertToLocalDate(order.getDateOfArrival()));
        int time = LocalDateTime.now().getHour();
        if (convertToLocalDate(order.getDateOfSending()).equals(LocalDate.now()) && time < 22) {
            return cityFromPoint;
        }
        if ((convertToLocalDate(order.getDateOfArrival()).equals(LocalDate.now()) && time > 15) || (convertToLocalDate(order.getDateOfArrival()).isBefore(LocalDate.now()))) {
            return cityToPoint;
        }
        int diffDays = diffDays(convertToLocalDate(order.getDateOfSending()), LocalDate.now());
        if (diffDays == 0) time = time - 22;
        else time = time + (diffDays - 1) * 24 + 2;
        return currentPoint(cityFromPoint, cityToPoint, days, time);
    }

    public static PointDTO currentPoint(PointDTO cityFromPoint, PointDTO cityToPoint, int days, int time) {
        double latCityFrom = Double.parseDouble(cityFromPoint.getLatitude());
        double lngCityFrom = Double.parseDouble(cityFromPoint.getLongitude());
        double latCityTo = Double.parseDouble(cityToPoint.getLatitude());
        double lngCityTo = Double.parseDouble(cityToPoint.getLongitude());
        double diffLat = (latCityFrom - latCityTo) / (days * 24 - 7);
        double diffLng = (lngCityFrom - lngCityTo) / (days * 24 - 7);
        String lat = String.valueOf(latCityFrom - diffLat * time);
        String lng = String.valueOf(lngCityFrom - diffLng * time);
        return new PointDTO(lat, lng);
    }

}

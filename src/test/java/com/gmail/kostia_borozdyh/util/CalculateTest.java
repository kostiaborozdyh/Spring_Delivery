package com.gmail.kostia_borozdyh.util;

import com.gmail.kostia_borozdyh.dto.InfoTableDTO;
import com.gmail.kostia_borozdyh.dto.PointDTO;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalculateTest {
    @Test
    public void avgPriceTest(){
        assertEquals(Integer.valueOf(100), Calculate.avgPrice(0));
        assertEquals(Integer.valueOf(450),Calculate.avgPrice(300));
        assertEquals(Integer.valueOf(817),Calculate.avgPrice(545));
    }

    @Test
    public void deliveryPriceTest(){
        assertEquals(Integer.valueOf(330), Calculate.deliveryPrice(0,3 ,11));
        assertEquals(Integer.valueOf(2042), Calculate.deliveryPrice(545,5,5));
        assertEquals(Integer.valueOf(180), Calculate.deliveryPrice(400,1,3));
    }

    @Test
    public void volumeTest() {
        assertEquals(Integer.valueOf(6), Calculate.volume(1,2 ,3));
        assertEquals(Integer.valueOf(50), Calculate.volume(10,1 ,5));
        assertEquals(Integer.valueOf(1), Calculate.volume(1,1 ,1));
    }
    @Test
    public void arrivalTimeTest() {
        assertEquals(LocalDate.now().plusDays(2), Calculate.arrivalTime(500));
        assertEquals(LocalDate.now().plusDays(1), Calculate.arrivalTime(245));
        assertEquals(LocalDate.now().plusDays(4), Calculate.arrivalTime(1300));
    }
    @Test
    public void getPaginationListTest() throws FileNotFoundException, ParseException {
        List<Integer> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            list.add(i);
            if(i%2==0) {
                list1.add("i" + i);
            }
        }

        List<InfoTableDTO> infoTableList = JsonParser.parseGoogleApiDistance(fileReader());
        assertEquals(5, Calculate.getPaginationList(list).size());
        assertEquals(5, Calculate.getPaginationList(infoTableList).size());
        assertEquals(3, Calculate.getPaginationList(list1).size());
    }
    @Test
    public void getFiveElementsTest() throws FileNotFoundException, ParseException {
        List<Integer> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            list.add(i);
            if(i%2==0) {
                list1.add("i" + i);
            }
        }
        Integer prediction = 5;
        List<Integer> listTest = Calculate.getFiveElements(list,2);
        List<String> list1Test = Calculate.getFiveElements(list1,3);
        for (Integer numb :
                listTest) {
            assertEquals(prediction,numb);
            prediction++;
        }
        prediction=20;
        for (String str :
                list1Test) {
            assertEquals("i"+prediction,str);
            prediction=prediction+2;
        }
        List<InfoTableDTO> infoTableList =  Calculate.getFiveElements(JsonParser.parseGoogleApiDistance(fileReader()),4);
        assertEquals(5,infoTableList.size());
    }
    @Test
    public void currentPointTest() throws FileNotFoundException, ParseException {
        PointDTO pointA = new PointDTO("50.4501", "30.5234");
        PointDTO pointB = new PointDTO("49.444433", "32.059767");
        PointDTO pointC = new PointDTO("47.910483", "33.391783");
        PointDTO firstTestPoint = Calculate.currentPoint(pointA,pointB,4,40);
        PointDTO secondTestPoint = Calculate.currentPoint(pointA,pointC,6,21);
        PointDTO thirdTestPoint = Calculate.currentPoint(pointB,pointC,5,30);
        assertTrue(firstTestPoint.getLatitude().contains("49.9981"));
        assertTrue(firstTestPoint.getLongitude().contains("31.2139"));
        assertTrue(secondTestPoint.getLatitude().contains("50.0608"));
        assertTrue(secondTestPoint.getLongitude().contains("30.9630"));
        assertTrue(thirdTestPoint.getLatitude().contains("49.0371"));
        assertTrue(thirdTestPoint.getLongitude().contains("32.4133"));
    }

    private String fileReader() throws FileNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("distanceJSON.txt").getFile());
        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return builder.toString();
    }
}

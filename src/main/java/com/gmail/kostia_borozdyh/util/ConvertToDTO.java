package com.gmail.kostia_borozdyh.util;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.dto.ReviewDTO;
import com.gmail.kostia_borozdyh.entity.Order;
import com.gmail.kostia_borozdyh.entity.Review;

import java.util.ArrayList;
import java.util.List;

public class ConvertToDTO {

    public static List<ReviewDTO> fromListReview(List<Review> list) {
        List<ReviewDTO> dtoList = new ArrayList<>();
        for (Review review :
                list) {
            dtoList.add(review.toReviewDTO());
        }
        return dtoList;
    }

    public static List<OrderDTO> fromListOrder(List<Order> list) {
        List<OrderDTO> dtoList = new ArrayList<>();
        for (Order order :
                list) {
            dtoList.add(OrderDTO.fromOrder(order));
        }
        return dtoList;
    }
}

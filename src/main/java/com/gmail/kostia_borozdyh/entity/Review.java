package com.gmail.kostia_borozdyh.entity;

import com.gmail.kostia_borozdyh.dto.ReviewDTO;
import com.gmail.kostia_borozdyh.util.CutDate;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "response")
    private String response;

    @Column(name = "date")
    private Date date;

    public ReviewDTO toReviewDTO() {
        return new ReviewDTO(user.getFirstName(), response, CutDate.cut(date));
    }
}

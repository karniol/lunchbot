package com.ttu.lunchbot.spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "opening_times")
public class OpeningTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "food_service_id", nullable = false)
    @JsonProperty("food_service")
    private FoodService foodService;

    /**
     * Day of the week, starts with Monday (1) and ends with Sunday (7)
     */
    @Column(name = "week_day", nullable = false)
    @JsonProperty("week_day")
    private byte weekDay;

    @Column(name = "open", nullable = false)
    @JsonProperty("open_today")
    private boolean open;

    @Column(name = "open_time")
    @JsonProperty("open_time")
    @JsonFormat(pattern = "hh:mm")
    private LocalTime openTime;

    @Column(name = "close_time")
    @JsonProperty("close_time")
    @JsonFormat(pattern = "hh:mm")
    private LocalTime closeTime;

}

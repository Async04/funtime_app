package com.example.funtime_app.entity;

import com.example.funtime_app.entity.enums.OTPStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "codes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String oneTimePassword;
    private LocalDateTime fromTime=LocalDateTime.now();
    private LocalDateTime expireTime=fromTime.plusMinutes(2);

    @Enumerated(value = EnumType.STRING)
    private OTPStatus status = OTPStatus.ALIVE;



}
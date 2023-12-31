package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "chat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int chatId;
    @ManyToOne
    @JoinColumn(name = "from_id")
    private User fromId;
    @ManyToOne
    @JoinColumn(name = "to_id")
    private User toId;

    @Temporal(TemporalType.TIMESTAMP)
    public Date dateTime;
    @Lob
    public String message;

    public Boolean seen;
}
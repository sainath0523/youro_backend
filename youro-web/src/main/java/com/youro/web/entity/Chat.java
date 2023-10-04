package com.youro.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "chat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int chatId;
    @ManyToOne
    @JoinColumn(name = "from_id")
    private LoginTable fromId;
    @ManyToOne
    @JoinColumn(name = "to_id")
    private LoginTable toId;

    @Temporal(TemporalType.TIMESTAMP)
    public Date dateTime;
    @Lob
    public String message;
}

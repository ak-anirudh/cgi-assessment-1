package org.cgi.assesment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@ToString
@NoArgsConstructor
@Table(name = "logs")
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String logLevel;

    @Lob
    @Column(length = 20971520)
    private String description;

    private String timeStamp;

    private String thread;

}

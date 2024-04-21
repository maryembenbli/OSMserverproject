package com.ftp.osmserverproj.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "email")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
   //add it 21/04
    public String getMsgBody() {
        return msgBody != null ? msgBody : ""; // Return empty string if msgBody is null
    }

}

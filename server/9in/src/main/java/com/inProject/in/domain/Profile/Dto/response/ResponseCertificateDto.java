package com.inProject.in.domain.Profile.Dto.response;

import com.inProject.in.domain.Profile.entity.Certificate;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponseCertificateDto {
    private Long certificate_id;
    private String certificate_name;  //자격증 이름
    private LocalDate acquisition_date; //딴 날짜
    private String expire;
    private String explanation;
    private Long user_id;

    public ResponseCertificateDto(Certificate certificate){
        this.certificate_id = certificate.getId();
        this.certificate_name = certificate.getCertificate_name();
        this.acquisition_date = certificate.getAcquisition_date();
        this.expire = certificate.getExpire();
        this.explanation = certificate.getExplanation();
        this.user_id = certificate.getUser().getId();
    }
}

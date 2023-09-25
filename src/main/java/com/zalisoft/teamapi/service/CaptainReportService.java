package com.zalisoft.teamapi.service;

import com.zalisoft.teamapi.dto.CaptainReportDto;
import com.zalisoft.teamapi.model.CaptainReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CaptainReportService {

    CaptainReport  save(CaptainReportDto captainReportDto, long projectId);

    CaptainReport findById(long id);

    void delete(long id);

    void update(long id , CaptainReportDto captainReportDto);

    Page<CaptainReport>  search(Pageable pageable , String search);
}

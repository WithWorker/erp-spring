package com.erp.backend.mapper;

import com.erp.backend.dto.CalendarDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {
    List<CalendarDto> getAllCalendars();
}

package com.gmail.kostia_borozdyh.service;

import com.gmail.kostia_borozdyh.dto.InfoTableDTO;

import java.util.List;

public interface IInfoTableService {
    List<Integer> getPageNumberList(List<InfoTableDTO> infoTable);

    List<InfoTableDTO> getInfoTable(String cityFrom, String cityTo);

    List<InfoTableDTO> getShortInfoTable(List<InfoTableDTO> infoTable, List<Integer> list);

    InfoTableDTO getInfoTableById(List<InfoTableDTO> infoTable, Integer id);
}
